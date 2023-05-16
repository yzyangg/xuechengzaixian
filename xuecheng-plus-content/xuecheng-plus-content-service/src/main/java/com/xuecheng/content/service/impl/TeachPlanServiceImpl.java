package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xuecheng.content.mapper.TeachplanMapper;
import com.xuecheng.content.model.dto.SaveTeachplanDto;
import com.xuecheng.content.model.dto.TeachplanDto;
import com.xuecheng.content.model.po.Teachplan;
import com.xuecheng.content.service.TeachPlanService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * The type Teach plan service.
 *
 * @author yzy
 * @version 1.0
 * @description TODO
 * @date 2023 /5/16 11:58
 */
@Service
public class TeachPlanServiceImpl implements TeachPlanService {
    @Resource
    private TeachplanMapper teachplanMapper;


    @Override
    public List<TeachplanDto> queryTreeNodes(long courseId) {
        return teachplanMapper.selectTreeNodes(courseId);
    }

    @Override
    public void saveTeachplan(SaveTeachplanDto teachplanDto) {

        //课程计划id
        Long id = teachplanDto.getId();
        //修改课程计划
        if (id != null) {
            Teachplan teachplan = teachplanMapper.selectById(id);
            BeanUtils.copyProperties(teachplanDto, teachplan);
            teachplanMapper.updateById(teachplan);
        } else {

            //取出同父同级别的课程计划数量
            int count = getTeachplanCount(teachplanDto.getCourseId(), teachplanDto.getParentid());
            Teachplan teachplanNew = new Teachplan();

            //设置排序号
            teachplanNew.setOrderby(count + 1);
            BeanUtils.copyProperties(teachplanDto, teachplanNew);

            teachplanMapper.insert(teachplanNew);
        }
    }


    /**
     * @param courseId 课程id
     * @param parentId 父课程计划id
     * @return int 最新排序号
     * @description 同一个课程下，同一个父节点下的课程计划数量
     * @author Mr.M
     * @date 2022/9/9 13:43
     */
    private int getTeachplanCount(long courseId, long parentId) {
        LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teachplan::getCourseId, courseId);
        queryWrapper.eq(Teachplan::getParentid, parentId);
        Integer count = teachplanMapper.selectCount(queryWrapper);
        return count == null ? 0 : count;
    }

}
