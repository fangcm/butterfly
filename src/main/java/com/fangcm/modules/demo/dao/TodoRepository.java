package com.fangcm.modules.demo.dao;

/**
 * Created by HEZY on 2018/5/23.
 */


public interface UserDao extends BaseDao<User,String> {
    /**
     * 通过用户名和状态获取用户
     * @param username
     * @param status
     * @return
     */
    List<User> findByUsernameAndStatus(String username, Integer status);

    /**
     * 通过状态和类型获取用户
     * @param status
     * @param type
     * @return
     */
    List<User> findByStatusAndType(Integer status, Integer type);
}