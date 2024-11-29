package com.tanght.helmet_detect_sys_demo.common.vo;

import lombok.Data;

import java.util.List;

/**
 * @Title: PageVo
 * @Author Tanght 363993584@qq.com
 * @Date 2024/11/29 10:14
 * @description:
 */
@Data
public class PageVo<T> {
    private Integer total;        // 总记录数
    private List<T> list;         // 结果集
    private Integer pageNum;      // 当前页码
    private Integer pageSize;     // 每页记录数
    private Integer pages;        // 总页数
}
