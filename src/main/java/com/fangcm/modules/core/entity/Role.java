package com.fangcm.modules.core.entity;

import com.fangcm.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by FangCM on 2018/5/24.
 */
@Entity
@Table(name = "sys_role")
public class Role extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String name; //角色名称
    private String roleCode; //角色代码

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }
}
