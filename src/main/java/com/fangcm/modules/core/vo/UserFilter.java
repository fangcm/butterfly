package com.fangcm.modules.core.vo;

/**
 * Created by FangCM on 2018/6/13.
 */
public class UserFilter {
    private String mobile;
    private String email;
    private String nickName;
    private Integer status; //状态 0正常 1禁用


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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


}
