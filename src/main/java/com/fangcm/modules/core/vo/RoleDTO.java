package com.fangcm.modules.core.vo;

/**
 * Created by FangCM on 2018/5/24.
 */
public class RoleDTO {

    private String name; //角色名称
    private String roleCode; //角色代码，具有roleCode的为系统角色

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
