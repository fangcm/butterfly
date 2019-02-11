package com.fangcm.modules.core.vo;

import com.fangcm.common.base.BaseDTO;
import lombok.Data;

import java.util.List;

/**
 * Created by FangCM on 2018/6/13.
 */
@Data
public class UserDTO extends BaseDTO {
    private String mobile;
    private String password;
    private String email;
    private String nickName;
    private Integer status; //状态 0正常 1禁用
    private String remarks;

    private List<RoleDTO> roles; //用户拥有角色

}
