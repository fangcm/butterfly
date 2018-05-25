package com.fangcm.modules.core.dao;

import com.fangcm.base.BaseDao;
import com.fangcm.modules.core.entity.Role;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by FangCM on 2018/5/23.
 */


public interface RoleDao extends BaseDao<Role, String> {

    /**
     * 通过userId查找
     */

    @Query(value = "SELECT r FROM  Role r, UserRole ur WHERE r.delFlag=0 AND r.id=ur.roleId AND ur.userId=?1 ")
    List<Role> findByUserId(String userId);

}