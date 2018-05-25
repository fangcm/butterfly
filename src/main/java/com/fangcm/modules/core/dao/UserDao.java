package com.fangcm.modules.core.dao;

import com.fangcm.base.BaseDao;
import com.fangcm.modules.core.entity.User;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by HEZY on 2018/5/23.
 */


public interface UserDao extends BaseDao<User, String> {
    /**
     * 通过手机号和状态获取用户
     */
    @Query("select u from User u where u.mobile=?1 AND u.delFlag=0 AND u.status=0")
    User findByMobile(String mobile);

    /**
     * 通过昵称和状态获取用户
     */
    @Query("select u from User u where u.nickName=?1 AND u.delFlag=0 AND u.status=0")
    User findByNickName(String nickName);

}