package com.fangcm.modules.core.vo;

import com.fangcm.common.base.BaseDTO;
import lombok.Data;

import java.util.List;

/**
 * Created by FangCM on 2018/6/13.
 */
@Data
public class MenuDTO extends BaseDTO {
    private String name; //菜单名称
    private String icon; //图标
    private String path; //路径
    private Boolean rootLevel; //是否为根级菜单
    private String parentId; //父菜单id
    private Integer sort; //排序（升序）
    private List<MenuDTO> children; //二级菜单


}
