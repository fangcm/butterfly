package com.fangcm.common.rest;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by FangCM on 2018/5/23.
 */
@Data
public class Result implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer code; //返回代码
    private String message; //失败消息
    private Object data; //结果对象

}