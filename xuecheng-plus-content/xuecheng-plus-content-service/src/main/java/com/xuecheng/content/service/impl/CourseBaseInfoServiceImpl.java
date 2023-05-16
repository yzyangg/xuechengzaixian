package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.mapper.CourseMarketMapper;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.EditCourseDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.model.po.CourseCategory;
import com.xuecheng.content.model.po.CourseMarket;
import com.xuecheng.content.service.CourseBaseInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * The type Course base info service.
 *
 * @author Mr.M
 * @version 1.0
 * @description TODO
 * @date 2022 /10/8 9:46
 */
@Slf4j
@Service
public class CourseBaseInfoServiceImpl implements CourseBaseInfoService {

    /**
     * The Course base mapper.
     */
    @Autowired
    CourseBaseMapper courseBaseMapper;

    /**
     * The Course market mapper.
     */
    @Autowired
    CourseMarketMapper courseMarketMapper;

    /**
     * The Course category mapper.
     */
    @Autowired
    CourseCategoryMapper courseCategoryMapper;


    @Override
    public PageResult<CourseBase> queryCourseBaseList(PageParams params, QueryCourseParamsDto queryCourseParamsDto) {

        LambdaQueryWrapper<CourseBase> queryWrapper = new LambdaQueryWrapper<>();

        //拼接查询条件
        //根据课程名称模糊查询  name like '%名称%'
        queryWrapper.like(StringUtils.isNotEmpty(queryCourseParamsDto.getCourseName()), CourseBase::getName, queryCourseParamsDto.getCourseName());

        //根据课程审核状态
        queryWrapper.eq(StringUtils.isNotEmpty(queryCourseParamsDto.getAuditStatus()), CourseBase::getAuditStatus, queryCourseParamsDto.getAuditStatus());

        //根据课程发布状态
        queryWrapper.eq(StringUtils.isNotEmpty(queryCourseParamsDto.getPublishStatus()), CourseBase::getAuditStatus, queryCourseParamsDto.getPublishStatus());


        //分页参数
        Page<CourseBase> page = new Page<>(params.getPageNo(), params.getPageSize());


        //分页查询E page 分页参数, @Param("ew") Wrapper<T> queryWrapper 查询条件
        Page<CourseBase> pageResult = courseBaseMapper.selectPage(page, queryWrapper);

        //数据
        List<CourseBase> items = pageResult.getRecords();
        //总记录数
        long total = pageResult.getTotal();


        //准备返回数据 List<T> items, long counts, long page, long pageSize
        PageResult<CourseBase> courseBasePageResult = new PageResult<>(items, total, params.getPageNo(), params.getPageSize());

        return courseBasePageResult;
    }

    @Transactional
    @Override
    public CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto dto) {


        //对参数进行合法性的校验
        {
            if (StringUtils.isBlank(dto.getName())) {
                XueChengPlusException.cast("课程名称为空");
            }
            if (StringUtils.isBlank(dto.getMt())) {
                XueChengPlusException.cast("课程大分类为空");
            }
            if (StringUtils.isBlank(dto.getSt())) {
                XueChengPlusException.cast("课程分类为空");
            }
            if (StringUtils.isBlank(dto.getGrade())) {
                XueChengPlusException.cast("课程等级为空");
            }
            if (StringUtils.isBlank(dto.getTeachmode())) {
                XueChengPlusException.cast("授课模式为空");
            }
            if (StringUtils.isBlank(dto.getUsers())) {
                XueChengPlusException.cast("适用人群为空");
            }
            if (StringUtils.isBlank(dto.getCharge())) {
                XueChengPlusException.cast("收费规则为空");
            }
        }


        //向CourseBase表写
        CourseBase courseBase = new CourseBase();
        //拷贝
        BeanUtils.copyProperties(dto, courseBase);

        //传输数据以外的其他参数
        //设置机构id
        courseBase.setCompanyId(companyId);
        //创建时间
        courseBase.setCreateDate(LocalDateTime.now());
        //审核状态默认为未提交
        courseBase.setAuditStatus("202002");
        //发布状态默认为未发布
        courseBase.setStatus("203001");

        //课程基本表插入一条记录
        int insert = courseBaseMapper.insert(courseBase);

        if (insert <= 0) {
            XueChengPlusException.cast("课程基本信息保存失败");
        }

        //向CourseMarket表写入一条记录
        //获取课程id
        Long courseId = courseBase.getId();
        CourseMarket courseMarket = new CourseMarket();
        //将dto中和courseMarket属性名一样的属性值拷贝到courseMarket
        BeanUtils.copyProperties(dto, courseMarket);
        courseMarket.setId(courseId);

        //保存到数据库
        saveOrupdateCourseMarket(courseMarket);

        //组装要返回的结果(多添加一些必要的参数)
        return getCourseBaseInfo(courseId);
    }

    /**
     * @param courseMarket
     * @return int
     * @description 保存课程营销信息，有则更新，没有就插入
     * @author
     */
    private int saveOrupdateCourseMarket(CourseMarket courseMarket) {
        //详细参数校验
        String charge = courseMarket.getCharge();
        if (StringUtils.isBlank(charge)) {
            throw new RuntimeException("收费规则为空");
        }
        if (charge.equals("201001")) {
            //收费规则为收费时，必须设置价格
            if (courseMarket.getPrice() == null || courseMarket.getPrice() <= 0) {
                XueChengPlusException.cast("收费规则为收费时，必须设置价格");
            }
        }

        CourseMarket selectById = courseMarketMapper.selectById(courseMarket.getId());

        if (selectById == null) {
            //新增
            return courseMarketMapper.insert(courseMarket);
        } else {
            //修改（传过来的和数据库中的数据不一样，要增加一些数据）
            BeanUtils.copyProperties(courseMarket, selectById);
            return courseMarketMapper.updateById(selectById);
        }

    }

    /**
     * 根据课程id查询课程的基本和营销信息
     *
     * @param courseId 课程id
     * @return 课程的信息
     */
    @Override
    public CourseBaseInfoDto getCourseBaseInfo(Long courseId) {

        //基本信息
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        Optional.ofNullable(courseBase).orElseThrow(() -> new RuntimeException("课程不存在"));

        //营销信息
        CourseMarket courseMarket = courseMarketMapper.selectById(courseId);

        CourseBaseInfoDto courseBaseInfoDto = new CourseBaseInfoDto();
        BeanUtils.copyProperties(courseBase, courseBaseInfoDto);
        Optional.ofNullable(courseMarket).ifPresent(courseMarket1 -> BeanUtils.copyProperties(courseMarket1, courseBaseInfoDto));

        //根据课程分类的id查询分类的名称
        String mt = courseBase.getMt();
        String st = courseBase.getSt();

        CourseCategory mtCategory = courseCategoryMapper.selectById(mt);
        CourseCategory stCategory = courseCategoryMapper.selectById(st);

        //设置分类名称
        Optional.ofNullable(mtCategory)
                .map(CourseCategory::getName)
                .ifPresent(courseBaseInfoDto::setMtName);

        Optional.ofNullable(stCategory)
                .map(CourseCategory::getName)
                .ifPresent(courseBaseInfoDto::setStName);


        return courseBaseInfoDto;

    }

    @Transactional
    @Override
    public CourseBaseInfoDto updateCourseBase(Long companyId, EditCourseDto editCourseDto) {
        //合法性校验
        CheckParams(companyId, editCourseDto);
        CourseBase courseBase = courseBaseMapper.selectById(editCourseDto.getId());
        BeanUtils.copyProperties(editCourseDto, courseBase);


        //更新基本信息
        int i = courseBaseMapper.updateById(courseBase);
        Optional.of(i).filter(integer -> integer > 0).orElseThrow(() -> new XueChengPlusException("更新课程基本信息失败"));

        CourseMarket courseMarket = courseMarketMapper.selectById(editCourseDto.getId());
        BeanUtils.copyProperties(editCourseDto, courseMarket);

        //更新营销信息
        int j = courseMarketMapper.updateById(courseMarket);
        Optional.of(j).filter(integer -> integer > 0).orElseThrow(() -> new XueChengPlusException("更新课程营销信息失败"));

        //组装要返回的结果(多添加一些必要的参数)
        return getCourseBaseInfo(editCourseDto.getId());
    }

    /**
     * @param editCourseDto
     * @return void
     * @description 校验参数的合法性，不合法抛出异常，合法则继续执行后面的代码
     */
    private void CheckParams(Long companyId, EditCourseDto editCourseDto) {

        CourseBase courseBase = courseBaseMapper.selectById(editCourseDto.getId());

        Optional.ofNullable(courseBase).orElseThrow(() -> new XueChengPlusException("课程不存在"));
        if (!courseBase.getCompanyId().equals(companyId)) {
            throw new XueChengPlusException("该课程不属于该机构");
        }

        CourseMarket courseMarket = courseMarketMapper.selectById(editCourseDto.getId());
        Optional.ofNullable(courseMarket).orElseThrow(() -> new XueChengPlusException("课程不存在"));

        if (StringUtils.isBlank(editCourseDto.getName())) {
            throw new XueChengPlusException("课程名称为空");
        }

        if (StringUtils.isBlank(editCourseDto.getMt())) {
            throw new XueChengPlusException("课程分类为空");
        }

        if (StringUtils.isBlank(editCourseDto.getSt())) {
            throw new XueChengPlusException("课程分类为空");
        }

        if (StringUtils.isBlank(editCourseDto.getGrade())) {
            throw new XueChengPlusException("课程等级为空");
        }

        if (StringUtils.isBlank(editCourseDto.getTeachmode())) {
            throw new XueChengPlusException("教育模式为空");
        }

        if (StringUtils.isBlank(editCourseDto.getUsers())) {
            throw new XueChengPlusException("适应人群为空");
        }

        if (StringUtils.isBlank(editCourseDto.getCharge())) {
            throw new XueChengPlusException("收费规则为空");
        }
    }


}