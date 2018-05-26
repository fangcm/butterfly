package com.fangcm.common.entity;

import java.io.Serializable;

public class Captcha implements Serializable {

    private String captchaId; //验证码id
    private String code; //验证码

    public String getCaptchaId() {
        return captchaId;
    }

    public void setCaptchaId(String captchaId) {
        this.captchaId = captchaId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
