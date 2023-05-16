package com.xuecheng.content.model.dto;

import com.xuecheng.content.model.po.CourseCategory;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Scanner;

/**
 * @author yzy
 * @version 1.0
 * @description TODO
 * @date
 */
@Data
public class CourseCategoryTreeDto extends CourseCategory implements Serializable {
    List<CourseCategoryTreeDto> childrenTreeNodes;

}
