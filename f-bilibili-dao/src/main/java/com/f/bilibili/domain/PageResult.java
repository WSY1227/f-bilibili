package com.f.bilibili.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @ClassName: PageResult
 * @Description: 分页辅助
 * @author: XU
 * @date: 2022年05月02日 22:08
 **/
@Data
@AllArgsConstructor
public class PageResult<T> {
    /**
     * 当前页总数
     */
    private Integer total;
    /**
     * 当前页数据
     */
    private List<T> list;
}
