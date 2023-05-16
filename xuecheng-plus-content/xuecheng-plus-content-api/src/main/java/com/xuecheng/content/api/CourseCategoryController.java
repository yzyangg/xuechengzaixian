package com.xuecheng.content.api;

import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.service.CourseCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * The type Course category controller.
 *
 * @author mrt
 * @version 1.0
 * @description TODO
 * @date 2022 /12/10 10:04
 */
@Slf4j
@Api(value = "课程分类相关接口", tags = "课程分类相关接口")
@RestController
public class CourseCategoryController {

    /**
     * The Course category service.
     */
    @Resource
    CourseCategoryService courseCategoryService;


    /**
     * Query tree nodes list.
     *
     * @return the list
     */
    @ApiOperation("课程分类查询接口")
    @GetMapping("/course-category/tree-nodes")
    public List<CourseCategoryTreeDto> queryTreeNodes() {
        return courseCategoryService.queryTreeNodes("1");
    }

}
