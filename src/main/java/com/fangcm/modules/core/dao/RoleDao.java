package com.fangcm.modules.core.dao;

import com.fangcm.base.BaseDao;
import com.fangcm.modules.core.entity.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by FangCM on 2018/5/23.
 */


public interface RoleDao extends BaseDao<Role, String> {
    /**
     * 获取默认角色
     */
    List<Role> findByDefaultRole(Boolean defaultRole);

    /**
     * 通过userId查找
     */

    @Query(value = "SELECT r FROM  Role r, UserRole ur WHERE r.id = ur.roleId AND ur.userId = :userId ")
    List<Role> findByUserId(@Param("userId") String userId);

}