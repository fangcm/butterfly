package com.fangcm.modules.core.vo;

import com.fangcm.common.base.BaseDTO;
import lombok.Data;

/**
 * Created by FangCM on 2018/6/13.
 */
@Data
public class RoleDTO extends BaseDTO {
    private String name; //角色名称
    private String roleCode; //角色代码，具有roleCode的为系统角色


}
