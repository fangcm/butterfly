package com.fangcm.common.rest;

/**
 * Created by FangCM on 2018/5/23.
 */
public class ResultUtil {

    public static Result setData(int code, String msg, Object data) {
        Result result = new Result();
        result.setMessage(msg);
        result.setCode(code);
        result.setData(data);
        return result;
    }

    public static Result setData(Object data) {
        return ResultUtil.setData(0, "success", data);
    }

    public static Result setMessage(String msg) {
        return ResultUtil.setData(0, msg, null);
    }

    public static Result setMessage(int code, String msg) {
        return ResultUtil.setData(code, msg, null);
    }

    public static Result setErrorMsg(ErrorCode errorCode) {
        return ResultUtil.setData(errorCode.errcode, errorCode.errmsg, null);
    }
}
