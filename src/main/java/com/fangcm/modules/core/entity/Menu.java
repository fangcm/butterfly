package com.fangcm.modules.core.entity;

import com.fangcm.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import java.util.List;


/**
 * Created by FangCM on 2018/5/24.
 */
@Entity
@Table(name = "sys_menu")
public class Menu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @NotBlank
    @Column(length = 64)
    private String name; //菜单名称

    private String icon; //图标
    private String path; //路径
    private Boolean rootLevel; //是否为根级菜单
    private String parentId; //父菜单id
    private Integer sort; //排序（升序）

    @Transient
    private List<Menu> children; //二级菜单


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Boolean getRootLevel() {
        return rootLevel;
    }

    public void setRootLevel(Boolean rootLevel) {
        this.rootLevel = rootLevel;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }
}
