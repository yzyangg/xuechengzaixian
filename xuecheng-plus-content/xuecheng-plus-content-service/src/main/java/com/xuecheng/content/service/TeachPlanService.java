package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.SaveTeachplanDto;
import com.xuecheng.content.model.dto.TeachplanDto;

import java.util.List;

/**
 * The interface Teach plan service.
 *
 * @author yzy
 * @version 1.0
 * @description TODO
 * @date 2023 /5/16 11:58
 */
public interface TeachPlanService {
    /**
     * Query tree nodes list.
     *
     * @param courseId the course id
     * @return the list
     */
    List<TeachplanDto> queryTreeNodes(long courseId);


    /**
     * Save teachplan.
     *
     * @param teachplanDto the teachplan dto
     */
    void saveTeachplan(SaveTeachplanDto teachplanDto);
}
