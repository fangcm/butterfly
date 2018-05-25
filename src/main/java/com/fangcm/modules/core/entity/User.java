package com.fangcm.modules.core.entity;

import com.fangcm.base.BaseEntity;
import com.fangcm.common.constant.CommonConstant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * Created by FangCM on 2018/5/23.
 */

@Entity
@Table(name = "sys_user")
public class User extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @NotEmpty
    @Column(length = 11)
    private String mobile;

    @NotEmpty
    @Column(length = 100)
    private String password;
    private String email;

    @Column(length = 32)
    private String nickName;
    private Integer status = CommonConstant.USER_STATUS_NORMAL; //状态 0正常 1禁用
    private String remarks;

    @Transient
    private List<Role> roles; //用户拥有角色


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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}