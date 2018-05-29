package com.fangcm.modules.core.entity;

import com.fangcm.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by FangCM on 2018/5/24.
 */
@Entity
@Table(name = "sys_role_menu")
public class RoleMenu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String menuId; //菜单id
    private String roleId; //角色id

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

}
