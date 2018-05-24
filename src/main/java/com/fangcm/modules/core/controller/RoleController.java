package com.fangcm.modules.core.controller;

import com.fangcm.base.BaseController;
import com.fangcm.common.entity.Result;
import com.fangcm.common.utils.ResultUtil;
import com.fangcm.modules.core.entity.Role;
import com.fangcm.modules.core.entity.UserRole;
import com.fangcm.modules.core.service.RoleService;
import com.fangcm.modules.core.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Created by FangCM on 2018/5/24.
 */

//拥有ROLE_ADMIN角色的用户可以访问
//角色管理接口
@RestController
@RequestMapping("/role")
@PreAuthorize("hasRole('ADMIN')")
public class RoleController extends BaseController<Role, String> {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public RoleService getService() {
        return roleService;
    }


    //添加角色
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<Role> add(@ModelAttribute Role role) {

        Role r = roleService.save(role);
        return new ResultUtil<Role>().setData(r);
    }

    //更新角色数据
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public Result<Role> edit(@ModelAttribute Role entity) {

        Role r = roleService.update(entity);
        return new ResultUtil<Role>().setData(r);
    }

    //批量通过ids删除
    @RequestMapping(value = "/delAllByIds", method = RequestMethod.DELETE)
    public Result<Object> delByIds(@RequestParam String[] ids) {

        for (String id : ids) {
            List<UserRole> list = userRoleService.findByRoleId(id);
            if (list != null && list.size() > 0) {
                return new ResultUtil<Object>().setErrorMsg("删除失败，包含正被使用中的角色");
            }
        }
        for (String id : ids) {
            roleService.delete(id);
        }
        return new ResultUtil<Object>().setSuccessMsg("批量通过id删除数据成功");
    }

}
