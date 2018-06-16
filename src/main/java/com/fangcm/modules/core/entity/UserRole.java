package com.fangcm.modules.core.entity;

import com.fangcm.common.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by FangCM on 2018/5/24.
 */
@Entity
@Table(name = "sys_user_role")
public class UserRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String userId; //用户id
    private String roleId; //角色id

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

}
