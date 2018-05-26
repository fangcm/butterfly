package com.fangcm.common.constant;

/**
 * Created by FangCM on 2018/5/23.
 */
public interface CommonConstant {

    /**
     * 用户正常状态
     */
    Integer USER_STATUS_NORMAL = 0;

    /**
     * 用户禁用状态
     */
    Integer USER_STATUS_DISABLE = 1;

    /**
     * 普通用户
     */
    Integer USER_TYPE_NORMAL = 0;

    /**
     * Admin用户
     */
    Integer USER_TYPE_ADMIN = 1;

    /**
     * 正常状态
     */
    Integer DEL_FLAG_NORMAL = 0;

    /**
     * 删除标志
     */
    Integer DEL_FLAG_DELETE = 1;

    /**
     * 限流标识
     */
    String LIMIT_ALL = "LIMIT_ALL";

}
