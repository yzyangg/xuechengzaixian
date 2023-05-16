package com.xuecheng.content.api;

import com.xuecheng.base.exception.ValidationGroup;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.EditCourseDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.service.CourseBaseInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * The type Course base info controller.
 *
 * @author Lenovo
 */
@Api(value = "课程管理接口", tags = "课程管理接口")
@RestController
public class CourseBaseInfoController {


    /**
     * The Course base info service.
     */
    @Resource
    CourseBaseInfoService courseBaseInfoService;


    /**
     * List page result.
     *
     * @param params               the params
     * @param queryCourseParamsDto the query course params dto
     * @return the page result
     */
    @ApiOperation("课程查询接口")
    @PostMapping("/course/list")
    public PageResult<CourseBase> list(PageParams params, @RequestBody QueryCourseParamsDto queryCourseParamsDto) {
        //调用service获取数据

        return courseBaseInfoService.queryCourseBaseList(params, queryCourseParamsDto);
    }


    /**
     * Create course base  info dto.
     *
     * @param addCourseDto the add course dto
     * @return the course base info dto
     */
    @ApiOperation("新增课程")
    @PostMapping("/course")
    public CourseBaseInfoDto createCourseBase(@RequestBody @Validated(ValidationGroup.Insert.class) AddCourseDto addCourseDto) {

        //获取当前用户所属培训机构的id
        Long companyId = 22L;

        //调用service
        return courseBaseInfoService.createCourseBase(companyId, addCourseDto);
    }

    /**
     * Gets course base by courseId.
     *
     * @param courseId the courseId
     * @return the course base by id
     */
    @ApiOperation("根据id查询课程")
    @GetMapping("/course/{courseId}")
    public CourseBaseInfoDto getCourseBaseById(@PathVariable Long courseId) {

        return courseBaseInfoService.getCourseBaseInfo(courseId);
    }

    /**
     * Update course base course base info dto.
     *
     * @param editCourseDto the edit course dto
     * @return the course base info dto
     */
    @ApiOperation("修改课程")
    @PutMapping("/course")
    public CourseBaseInfoDto updateCourseBase(@RequestBody @Validated(ValidationGroup.Update.class) EditCourseDto editCourseDto) {
        Long companyId = 22L;
        return courseBaseInfoService.updateCourseBase(companyId, editCourseDto);

    }

}
