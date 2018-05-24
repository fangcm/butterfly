package com.fangcm.common.entity;

import java.io.Serializable;

/**
 * Created by FangCM on 2018/5/24.
 */
public class IpLocate implements Serializable {

    private String retCode;
    private City result;

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public City getResult() {
        return result;
    }

    public void setResult(City result) {
        this.result = result;
    }
}
