package com.fangcm.modules.demo.entity;

import com.fangcm.base.BaseEntity;
import com.fangcm.common.constant.CommonConstant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

/**
 * Created by FangCM on 2018/5/23.
 */

@Entity
@Table(name = "t_user")
public class User extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @NotEmpty
    @Column(length = 11)
    private String mobile;

    @NotEmpty
    @Column(length = 32)
    private String password;
    private String email;

    @Column(name = "nick_name", length = 32)
    private String nickName;
    private Integer type = CommonConstant.USER_TYPE_NORMAL; //用户类型 0普通用户 1管理员
    private Integer status = CommonConstant.USER_STATUS_NORMAL; //状态 0正常 1禁用
    private String remarks;


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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
}