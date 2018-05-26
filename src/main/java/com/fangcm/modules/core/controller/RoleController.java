package com.fangcm.modules.core.controller;

import com.fangcm.base.BaseController;
import com.fangcm.modules.core.entity.Role;
import com.fangcm.modules.core.service.RoleService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by FangCM on 2018/5/24.
 */

//拥有ROLE_ADMIN角色的用户可以访问
//角色管理接口
@RestController
@RequestMapping("/core/role")
@RequiresRoles("admin")
public class RoleController extends BaseController<Role, String> {

    @Autowired
    private RoleService roleService;

    @Override
    public RoleService getService() {
        return roleService;
    }

}
