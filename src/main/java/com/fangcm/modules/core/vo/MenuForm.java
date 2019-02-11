package com.fangcm.modules.core.vo;

import lombok.Data;

@Data
public class MenuForm {
    private String id;
    private String name; //菜单名称
    private String icon; //图标
    private String path; //路径
    private Boolean rootLevel; //是否为根级菜单
    private String parentId; //父菜单id
    private Integer sort; //排序（升序）

}
