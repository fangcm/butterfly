package com.fangcm.common.rest;

import java.io.Serializable;

/**
 * Created by FangCM on 2018/5/23.
 */
public class Result implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean success; //成功标志
    private String message; //失败消息
    private Integer code; //返回代码
    private long timestamp = System.currentTimeMillis(); //时间戳
    private Object result; //结果对象

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}