package com.fangcm.modules.core.dao;

import com.fangcm.common.base.BaseDao;
import com.fangcm.modules.core.entity.UserRole;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by FangCM on 2018/5/23.
 */

@Repository
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