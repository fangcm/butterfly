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
@Table(name = "sys_role")
public class Role extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String name; //角色名称
    private String roleCode; //角色代码，具有roleCode的为系统角色


}
