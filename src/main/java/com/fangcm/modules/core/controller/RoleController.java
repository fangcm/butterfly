package com.fangcm.modules.core.controller;

import com.fangcm.modules.core.service.RoleService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * Created by FangCM on 2018/5/24.
 */

//角色管理接口
@RestController
@RequestMapping("/core/role")
@RequiresRoles("admin")
public class RoleController {

    @Resource
    private RoleService roleService;


}
