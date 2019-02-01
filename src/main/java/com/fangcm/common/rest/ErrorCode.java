package com.fangcm.common.rest;

/**
 * Created by FangCM on 2019/2/1.
 */
public class ErrorCode {
    public static final ErrorCode SUCCESS = new ErrorCode(0, "成功");
    public static final ErrorCode UNAUTHORIZED = new ErrorCode(401, "未登录");
    public static final ErrorCode FORBIDDEN = new ErrorCode(403, "拒绝访问");
    public static final ErrorCode NOT_FOUND = new ErrorCode(404, "资源不存在");
    public static final ErrorCode METHOD_NOT_ALLOWED = new ErrorCode(405, "资源动词不支持");

    public final int errcode;
    public final String errmsg;

    public ErrorCode(int errcode, String errmsg) {
        this.errcode = errcode;
        this.errmsg = errmsg;
    }

}
