package com.fangcm.common.utils;

import com.fangcm.common.entity.Result;

/**
 * Created by FangCM on 2018/5/23.
 */
public class ResultUtil {

    public static Result setData(boolean success, Integer code, String msg, Object data) {
        Result result = new Result();
        result.setSuccess(success);
        result.setMessage(msg);
        result.setCode(code);
        result.setResult(data);
        return result;
    }

    public static Result setData(Object data, String msg) {
        return ResultUtil.setData(true, 200, msg, data);
    }

    public static Result setData(Object data) {
        return ResultUtil.setData(true, 200, "success", data);
    }


    public static Result setSuccessMsg(String msg) {
        return ResultUtil.setData(true, 200, msg, null);
    }


    public static Result setErrorMsg(String msg) {
        return ResultUtil.setData(false, 500, msg, null);
    }

    public static Result setErrorMsg(Integer code, String msg) {
        return ResultUtil.setData(false, code, msg, null);
    }
}
