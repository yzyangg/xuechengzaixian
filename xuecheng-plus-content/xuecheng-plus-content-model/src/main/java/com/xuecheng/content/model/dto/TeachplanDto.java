package com.xuecheng.content.model.dto;

import com.xuecheng.content.model.po.Teachplan;
import com.xuecheng.content.model.po.TeachplanMedia;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author yzy
 * @version 1.0
 * @description TODO
 * @date 2023/5/16 11:05
 */
@Data
@ToString
public class TeachplanDto extends Teachplan {

    /**
     * 媒资信息
     */
    TeachplanMedia teachplanMedia;

    /**
     * 子节点
     */
    List<TeachplanDto> teachPlanTreeNodes;

}