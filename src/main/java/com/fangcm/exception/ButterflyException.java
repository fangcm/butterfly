package com.fangcm.exception;

/**
 * Created by FangCM on 2018/5/25.
 */
public class ButterflyException extends RuntimeException {
    private String msg;

    public ButterflyException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
