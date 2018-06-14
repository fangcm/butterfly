package com.fangcm.modules.core.vo;

public class MenuDTO {
    private String id;
    private String name; //菜单名称
    private String icon; //图标
    private String path; //路径
    private Boolean rootLevel; //是否为根级菜单
    private String parentId; //父菜单id
    private Integer sort; //排序（升序）

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
}
