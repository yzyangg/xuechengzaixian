package com.xuecheng;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.mapper.TeachplanMapper;
import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.service.CourseBaseInfoService;
import com.xuecheng.content.service.CourseCategoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class ContentServiceApplicationTests {

    @Autowired
    CourseBaseMapper courseBaseMapper;

    @Autowired
    CourseBaseInfoService courseBaseInfoService;

    @Autowired
    CourseCategoryService courseCategoryService;

    @Resource
    TeachplanMapper teachplanMapper;

    @Test
    void testCourseBaseMapper() {
        CourseBase courseBase = courseBaseMapper.selectById(22);
        Assertions.assertNotNull(courseBase);
    }

    @Test
    void testCourseBaseInfoService() {
        PageParams pageParams = new PageParams();
        PageResult<CourseBase> courseBasePageResult = courseBaseInfoService.queryCourseBaseList(pageParams, new QueryCourseParamsDto());
        System.out.println(courseBasePageResult);
    }

    @Test
    void testCourseCategoryService() {
        List<CourseCategoryTreeDto> courseCategoryTreeDtos = courseCategoryService.queryTreeNodes("1");
        System.out.println(courseCategoryTreeDtos);
    }

    @Test
    void testTeachPlanMapper() {
        teachplanMapper.selectTreeNodes(1L).forEach(System.out::println);
    }


}
