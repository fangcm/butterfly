package com.fangcm.modules.core.vo;

import com.fangcm.common.base.BaseVO;

import java.util.List;

/**
 * Created by FangCM on 2018/6/13.
 */
public class UserVO extends BaseVO {
    private String mobile;
    private String password;
    private String email;
    private String nickName;
    private Integer status; //状态 0正常 1禁用
    private String remarks;

    private List<RoleVO> roles; //用户拥有角色

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public List<RoleVO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleVO> roles) {
        this.roles = roles;
    }
}
