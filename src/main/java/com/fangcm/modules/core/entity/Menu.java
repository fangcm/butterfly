package com.fangcm.modules.core.entity;

import com.fangcm.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;


/**
 * Created by FangCM on 2018/5/24.
 */
@Entity
@Table(name = "t_menu")
public class Menu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String name; //菜单名
    private String title; //标题
    private String icon; //图标
    private String path; //路径
    private String component; //前端组件
    private Boolean parent; //是否为一级菜单
    private String parentId; //父菜单id
    private Integer access; //所需权限值

    @Transient
    private List<Menu> children; //二级菜单

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public Boolean getParent() {
        return parent;
    }

    public void setParent(Boolean parent) {
        this.parent = parent;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getAccess() {
        return access;
    }

    public void setAccess(Integer access) {
        this.access = access;
    }

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }
}
