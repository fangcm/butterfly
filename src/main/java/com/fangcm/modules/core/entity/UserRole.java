package com.fangcm.modules.core.entity;

import com.fangcm.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Created by FangCM on 2018/5/24.
 */
@Entity
@Table(name = "t_user_role")
public class UserRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String userId; //用户唯一id
    private String roleId; //角色唯一id

    @Transient
    private String roleName; //角色名

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

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
