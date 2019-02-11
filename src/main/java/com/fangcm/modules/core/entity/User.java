package com.fangcm.modules.core.entity;

import com.fangcm.common.base.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

/**
 * Created by FangCM on 2018/5/23.
 */
@Data
@Entity
@Table(name = "sys_user")
public class User extends BaseEntity {
    public static final int USER_STATUS_NORMAL = 0; //用户正常状态
    public static final int USER_STATUS_DISABLE = 1; //用户禁用状态
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
    private Integer status = USER_STATUS_NORMAL; //状态 0正常 1禁用
    private String remarks;


}