package com.fangcm.common.entity;

import java.io.Serializable;

/**
 * Created by FangCM on 2018/5/23.
 */
public class PageVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private int pageNumber;
    private int pageSize;
    private String sort;
    private String order;

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}