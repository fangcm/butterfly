package com.fangcm.modules.core.controller;

import com.fangcm.common.rest.Result;
import com.fangcm.common.rest.ResultUtil;
import com.fangcm.modules.core.service.UserService;
import com.fangcm.modules.core.vo.LoginForm;
import com.fangcm.modules.core.vo.UserFilter;
import com.fangcm.modules.core.vo.UserForm;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by FangCM on 2018/5/23.
 */

@RestController
@RequestMapping("/core/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 登录请求
     */
    @PostMapping("/login")
    public Result submitLogin(@RequestBody LoginForm loginForm) {
        return ResultUtil.setData(userService.login(loginForm));
    }


    //获取当前登录用户接口
    @GetMapping(value = "/currentInfo")
    @RequiresAuthentication
    public Result getUserInfo() {
        return ResultUtil.setData(userService.getCurrentUserInfo());
    }


    //修改用户自己资料
    //用户名密码不会修改 需要通过id获取原用户信息
    @PostMapping(value = "/editOwn")
    @RequiresAuthentication
    public Result editOwn(@ModelAttribute UserForm form) {
        userService.save(form, null);
        return ResultUtil.setMessage("修改成功");
    }


    /**
     * 修改密码
     */
    @PostMapping(value = "/modifyPass")
    @RequiresAuthentication
    public Result modifyPass(@RequestParam String userId,
                             @RequestParam String password,
                             @RequestParam String newPass) {
        userService.modifyPass(userId, password, newPass);
        return ResultUtil.setMessage("修改密码成功");
    }

    /**
     * 修改资料仅允许ADMIN权限
     * 用户名密码不会修改 需要通过id获取原用户信息
     */
    @PostMapping(value = "/editByAdmin")
    @RequiresRoles("admin")
    public Result edit(@ModelAttribute UserForm form,
                       @RequestParam(required = false) String[] roles) {
        userService.save(form, roles);
        return ResultUtil.setMessage("修改成功");
    }


    //多条件分页获取用户列表
    @GetMapping(value = "/findByCondition")
    //@RequiresRoles("admin")
    public Result findByCondition(@ModelAttribute UserFilter filter, @PageableDefault Pageable pageable) {
        return ResultUtil.setData(userService.findByCondition(filter, pageable));
    }


    //添加用户
    @PostMapping(value = "/addByAdmin")
    @RequiresRoles("admin")
    public Result addByAdmin(@ModelAttribute UserForm form,
                             @RequestParam(required = false) String[] roles) {
        return ResultUtil.setData(userService.save(form, roles));
    }


    //后台禁用用户
    @PostMapping(value = "/disable")
    @RequiresRoles("admin")
    public Result disable(@RequestParam String userId) {
        userService.disable(userId);
        return ResultUtil.setMessage("禁用用户成功");
    }


    //后台启用用户
    @PostMapping(value = "/enable")
    @RequiresRoles("admin")
    public Result enable(@RequestParam String userId) {
        userService.enable(userId);
        return ResultUtil.setMessage("启用用户成功");
    }


    @DeleteMapping(value = "/delById")
    @RequiresRoles("admin")
    public Result deleteById(@RequestParam String userId) {
        userService.deleteById(userId);
        return ResultUtil.setMessage("删除数据成功");
    }
}