package com.fangcm.modules.core.entity;

import com.fangcm.common.base.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;


/**
 * Created by FangCM on 2018/5/24.
 */
@Data
@Entity
@Table(name = "sys_menu")
public class Menu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @NotBlank
    @Column(length = 32)
    private String name; //菜单名称

    private String icon; //图标
    private String color;
    private String path; //路径
    private Boolean rootLevel; //是否为根级菜单
    private String parentId; //父菜单id
    private Integer sort; //排序（升序）


}
