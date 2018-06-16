package com.fangcm.modules.core.dao;

import com.fangcm.common.base.BaseDao;
import com.fangcm.modules.core.entity.RoleMenu;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by FangCM on 2018/5/23.
 */

@Repository
public interface RoleMenuDao extends BaseDao<RoleMenu, String> {
    /**
     * 通过roleId查找
     */
    List<RoleMenu> findByRoleId(String roleId);


    /**
     * 删除角色的菜单
     */
    void deleteByRoleId(String roleId);
}