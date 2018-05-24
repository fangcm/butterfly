package com.fangcm.modules.core.dao;

import com.fangcm.base.BaseDao;
import com.fangcm.modules.core.entity.UserRole;

import java.util.List;

/**
 * Created by FangCM on 2018/5/23.
 */


public interface UserRoleDao extends BaseDao<UserRole, String> {
    /**
     * 通过roleId查找
     */
    List<UserRole> findByRoleId(String roleId);


    /**
     * 删除用户角色
     */
    void deleteByUserId(String userId);
}