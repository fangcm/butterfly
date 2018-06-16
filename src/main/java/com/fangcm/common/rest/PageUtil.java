package com.fangcm.common.rest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.io.Serializable;
import java.util.List;

/**
 * Created by FangCM on 2018/5/23.
 */
public class PageUtil {

    public static <T> Page<T> pageWrap(List<T> content, Page<? extends Serializable> pageable) {
        return new PageImpl<>(content, pageable.getPageable(), pageable.getTotalElements());
    }
}
