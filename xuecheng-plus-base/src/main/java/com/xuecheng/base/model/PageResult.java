package com.xuecheng.base.model;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * 页面结果
 *
 * @param <T> the type parameter
 * @author Mr.M
 * @version 1.0
 * @description TODO
 * @date 2022 /10/7 16:17
 */
@Data
@ToString
public class PageResult<T> {
    /**
     * The Items.
     */
    private List<T> items;

    /**
     * The Counts.
     */
    private long counts;

    /**
     * The Page.
     */
    private long page;

    /**
     * The Page size.
     */
    private long pageSize;


    /**
     * Instantiates a new Page result.
     *
     * @param items    the items
     * @param counts   the counts
     * @param page     the page
     * @param pageSize the page size
     */
    public PageResult(List<T> items, long counts, long page, long pageSize) {
        this.items = items;
        this.counts = counts;
        this.page = page;
        this.pageSize = pageSize;
    }

}
