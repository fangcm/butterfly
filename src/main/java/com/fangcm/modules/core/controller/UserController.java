package com.fangcm.modules.core.controller;

import com.fangcm.common.entity.PageVo;
import com.fangcm.common.entity.Result;
import com.fangcm.common.utils.JWTUtil;
import com.fangcm.common.utils.PageUtil;
import com.fangcm.common.utils.ResultUtil;
import com.fangcm.common.utils.UserUtil;
import com.fangcm.config.security.JWTToken;
import com.fangcm.modules.core.dto.LoginDTO;
import com.fangcm.modules.core.entity.User;
import com.fangcm.modules.core.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * Created by FangCM on 2018/5/23.
 */

@RestController
@RequestMapping("/core/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 登录请求
     */
    @PostMapping("/login")
    public Result<Object> submitLogin(@RequestBody(required = true) LoginDTO loginDTO) {
        User user = userService.findByMobile(loginDTO.getUsername());
        if (user == null) {
            return new ResultUtil<Object>().setErrorMsg("没有找到该用户");
        }

        String secret = UserUtil.encrypt(loginDTO.getPassword());
        if (!StringUtils.equals(secret, user.getPassword())) {
            return new ResultUtil<Object>().setErrorMsg("密码不正确");
        }

        JWTToken token = new JWTToken(JWTUtil.sign(loginDTO.getUsername(), secret));
        return new ResultUtil<Object>().setData(token.getCredentials());
    }

    /**
     * 退出
     */
    @GetMapping(value = "/logout")
    @RequiresAuthentication
    public Result<Object> logout() {
        SecurityUtils.getSubject().logout();
        return new ResultUtil<Object>().setSuccessMsg("退出成功");
    }

    //获取当前登录用户接口
    @RequestMapping(value = "/currentInfo", method = RequestMethod.GET)
    @RequiresAuthentication
    public Result<User> getUserInfo() {
        return new ResultUtil<User>().setData(userService.getCurrentUserInfo());
    }


    //修改用户自己资料
    //用户名密码不会修改 需要通过id获取原用户信息
    @RequestMapping(value = "/editOwn", method = RequestMethod.POST)
    @RequiresAuthentication
    public Result<Object> editOwn(@ModelAttribute User u) {
        userService.save(u, null);
        return new ResultUtil<Object>().setSuccessMsg("修改成功");
    }


    /**
     * 修改密码
     */
    @RequestMapping(value = "/modifyPass", method = RequestMethod.POST)
    @RequiresAuthentication
    public Result<Object> modifyPass(@RequestParam String userId,
                                     @RequestParam String password,
                                     @RequestParam String newPass) {
        userService.modifyPass(userId, password, newPass);
        return new ResultUtil<Object>().setSuccessMsg("修改密码成功");
    }

    /**
     * 修改资料仅允许ADMIN权限
     * 用户名密码不会修改 需要通过id获取原用户信息
     */
    @RequestMapping(value = "/editByAdmin", method = RequestMethod.POST)
    @RequiresRoles("admin")
    public Result<Object> edit(@ModelAttribute User u,
                               @RequestParam(required = false) String[] roles) {
        userService.save(u, roles);
        return new ResultUtil<Object>().setSuccessMsg("修改成功");
    }


    //多条件分页获取用户列表
    @RequestMapping(value = "/findByCondition", method = RequestMethod.GET)
    @RequiresRoles("admin")
    public Result<Page<User>> findByCondition(@ModelAttribute User user, @ModelAttribute PageVo pageVo) {
        return new ResultUtil<Page<User>>().setData(userService.findByCondition(user, PageUtil.initPage(pageVo)));
    }


    //添加用户
    @RequestMapping(value = "/addByAdmin", method = RequestMethod.POST)
    @RequiresRoles("admin")
    public Result<Object> addByAdmin(@ModelAttribute User u,
                                     @RequestParam(required = false) String[] roles) {
        return new ResultUtil<Object>().setData(userService.save(u, roles));
    }


    //后台禁用用户
    @RequestMapping(value = "/disable", method = RequestMethod.POST)
    @RequiresRoles("admin")
    public Result<Object> disable(@RequestParam String userId) {
        userService.disable(userId);
        return new ResultUtil<Object>().setSuccessMsg("禁用用户成功");
    }


    //后台启用用户
    @RequestMapping(value = "/enable", method = RequestMethod.POST)
    @RequiresRoles("admin")
    public Result<Object> enable(@RequestParam String userId) {
        userService.enable(userId);
        return new ResultUtil<Object>().setSuccessMsg("启用用户成功");
    }


    //批量通过ids删除
    @RequestMapping(value = "/delByIds", method = RequestMethod.DELETE)
    @RequiresRoles("admin")
    public Result<Object> delByIds(@RequestParam String[] ids) {
        userService.delByIds(ids);
        return new ResultUtil<Object>().setSuccessMsg("批量通过id删除数据成功");
    }
}