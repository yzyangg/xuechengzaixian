package com.xuecheng.content.service.impl;

import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.service.CourseCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author mrt
 * @version 1.0
 * @description TODO
 * @date 2022/12/10 11:39
 */
@Slf4j
@Service
public class CourseCategoryServiceImpl implements CourseCategoryService {
    @Resource
    CourseCategoryMapper courseCategoryMapper;


    @Override
    public List<CourseCategoryTreeDto> queryTreeNodes(String id) {
        //得到了当前结点下边的所有子结点
        List<CourseCategoryTreeDto> categoryTreeDtos = courseCategoryMapper.selectTreeNodes(id);

        //nodeMap
        Map<String, CourseCategoryTreeDto> collect = categoryTreeDtos.stream()
                .filter(item -> !item.getId().equals(id))
                .collect(Collectors.toMap(CourseCategoryTreeDto::getId, item -> item, (k1, k2) -> k2));

        List<CourseCategoryTreeDto> result = new ArrayList<>();

        categoryTreeDtos.forEach(item -> {

            //添加到集合
            if (item.getParentid().equals(id)) {
                result.add(item);
            }

            //处理每个非直接与id相连的节点，找到他们的父节点，并且添加到父节点的childrenTreeNodes中
            CourseCategoryTreeDto courseCategoryParent = collect.get(item.getParentid());
            if (courseCategoryParent != null) {
                List<CourseCategoryTreeDto> childrenTreeNodes = courseCategoryParent.getChildrenTreeNodes();
                if (childrenTreeNodes == null) {
                    courseCategoryParent.setChildrenTreeNodes(new ArrayList<>());
                }
                courseCategoryParent.getChildrenTreeNodes().add(item);
            }
        });

        return result;
    }
}
