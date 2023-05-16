package com.xuecheng.content.service;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.EditCourseDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * The interface Course base info service.
 *
 * @author Mr.M
 * @version 1.0
 * @description 课程管理service
 * @date 2022 /10/8 9:44
 */
public interface CourseBaseInfoService {


    /**
     * Query course base list page result.
     *
     * @param params               分页参数
     * @param queryCourseParamsDto 查询条件
     * @return com.xuecheng.base.model.PageResult<com.xuecheng.content.model.po.CourseBase>  page result
     * @description 课程查询
     * @author Mr.M
     * @date 2022 /10/8 9:46
     */
    public PageResult<CourseBase> queryCourseBaseList(PageParams params, QueryCourseParamsDto queryCourseParamsDto);

    /**
     * 新增课程
     *
     * @param companyId    培训机构id(不是用户自己输入的)
     * @param addCourseDto 新增课程的信息
     * @return 课程信息包括基本信息 、营销信息
     */
    public CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto addCourseDto);

    /**
     * Gets course base info.
     *
     * @param courseId the course id
     * @return the course base info
     */
    public CourseBaseInfoDto getCourseBaseInfo(Long courseId);

    /**
     * Update course base course base info dto.
     *
     * @param companyId     the company id
     * @param editCourseDto the edit course dto
     * @return the course base info dto
     */
    CourseBaseInfoDto updateCourseBase(Long companyId, EditCourseDto editCourseDto);
}
