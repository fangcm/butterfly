package com.fangcm.modules.core.entity;

import com.fangcm.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by FangCM on 2018/5/24.
 */
@Entity
@Table(name = "t_role")
public class Role extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String name; //角色名 以ROLE_开头
    private String title; //角色显示标题
    private Integer access; //对应权限值 权限菜单所需

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getAccess() {
        return access;
    }

    public void setAccess(Integer access) {
        this.access = access;
    }

}
