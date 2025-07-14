package org.felixcjy.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 分页数据类
 * 一般直接返回 IPage，某些情况下，如逻辑分页，需要用到本类
 *
 * @author: Felix(蔡济阳)
 * @since : 2025/7/11 10:08
 */
@Data
@AllArgsConstructor
public class PageDTO<T> {
    /** 总条数 */
    private Long total;

    /** 总页数 */
    private Long pages;

    /** 分页结果集合 */
    private List<T> records;

    /** 单前页码 */
    private Long currentPage;

    /** 分页大小 */
    private Long pageSize;
}
