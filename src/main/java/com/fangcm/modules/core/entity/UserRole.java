package com.fangcm.modules.core.entity;

import com.fangcm.common.base.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by FangCM on 2018/5/24.
 */
@Data
@Entity
@Table(name = "sys_user_role")
public class UserRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String userId; //用户id
    private String roleId; //角色id


}
