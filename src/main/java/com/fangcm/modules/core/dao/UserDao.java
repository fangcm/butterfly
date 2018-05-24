package com.fangcm.modules.core.dao;

import com.fangcm.base.BaseDao;
import com.fangcm.modules.core.entity.User;

import java.util.List;

/**
 * Created by HEZY on 2018/5/23.
 */


public interface UserDao extends BaseDao<User, String> {
    /**
     * 通过手机号和状态获取用户
     */
    List<User> findByMobileAndStatus(String mobile, Integer status);

    /**
     * 通过昵称和状态获取用户
     */
    List<User> findByNickNameAndStatus(String nickName, Integer status);

    /**
     * 通过状态和类型获取用户
     */
    List<User> findByStatusAndType(Integer status, Integer type);
}