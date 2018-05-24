package com.fangcm.modules.core.controller;

import com.fangcm.base.BaseController;
import com.fangcm.modules.core.dao.UserRoleDao;
import com.fangcm.modules.core.entity.UserRole;
import com.fangcm.modules.core.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by FangCM on 2018/5/24.
 */
//角色管理接口
@RestController
@RequestMapping("/userRole")
@PreAuthorize("hasRole('ADMIN')")
public class UserRoleController extends BaseController<UserRole, String> {

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public UserRoleService getService() {
        return userRoleService;
    }

}