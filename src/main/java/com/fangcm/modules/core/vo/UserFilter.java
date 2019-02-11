package com.fangcm.modules.core.vo;

import lombok.Data;

/**
 * Created by FangCM on 2018/6/13.
 */
@Data
public class UserFilter {
    private String mobile;
    private String email;
    private String nickName;
    private Integer status; //状态 0正常 1禁用


}
