package com.fangcm.modules.core.controller;

import com.fangcm.common.entity.PageVo;
import com.fangcm.common.entity.Result;
import com.fangcm.common.utils.JWTUtil;
import com.fangcm.common.utils.PageUtil;
import com.fangcm.common.utils.ResultUtil;
import com.fangcm.common.utils.UserUtil;
import com.fangcm.config.security.JWTToken;
import com.fangcm.modules.core.entity.User;
import com.fangcm.modules.core.service.UserService;
import com.fangcm.modules.core.vo.LoginDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
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
    public Result submitLogin(@ModelAttribute LoginDTO loginDTO) {
        User user = userService.findByMobile(loginDTO.getUsername());
        if (user == null) {
            return ResultUtil.setErrorMsg("没有找到该用户");
        }

        String secret = UserUtil.encrypt(loginDTO.getPassword());
        if (!StringUtils.equals(secret, user.getPassword())) {
            return ResultUtil.setErrorMsg("密码不正确");
        }

        JWTToken token = new JWTToken(JWTUtil.sign(loginDTO.getUsername(), secret));
        return ResultUtil.setData(token.getCredentials());
    }


    //获取当前登录用户接口
    @RequestMapping(value = "/currentInfo", method = RequestMethod.GET)
    @RequiresAuthentication
    public Result getUserInfo() {
        return ResultUtil.setData(userService.getCurrentUserInfo());
    }


    //修改用户自己资料
    //用户名密码不会修改 需要通过id获取原用户信息
    @RequestMapping(value = "/editOwn", method = RequestMethod.POST)
    @RequiresAuthentication
    public Result editOwn(@ModelAttribute User u) {
        userService.save(u, null);
        return ResultUtil.setSuccessMsg("修改成功");
    }


    /**
     * 修改密码
     */
    @RequestMapping(value = "/modifyPass", method = RequestMethod.POST)
    @RequiresAuthentication
    public Result modifyPass(@RequestParam String userId,
                             @RequestParam String password,
                             @RequestParam String newPass) {
        userService.modifyPass(userId, password, newPass);
        return ResultUtil.setSuccessMsg("修改密码成功");
    }

    /**
     * 修改资料仅允许ADMIN权限
     * 用户名密码不会修改 需要通过id获取原用户信息
     */
    @RequestMapping(value = "/editByAdmin", method = RequestMethod.POST)
    @RequiresRoles("admin")
    public Result edit(@ModelAttribute User u,
                       @RequestParam(required = false) String[] roles) {
        userService.save(u, roles);
        return ResultUtil.setSuccessMsg("修改成功");
    }


    //多条件分页获取用户列表
    @RequestMapping(value = "/findByCondition", method = RequestMethod.GET)
    @RequiresRoles("admin")
    public Result findByCondition(@ModelAttribute User user, @ModelAttribute PageVo pageVo) {
        return ResultUtil.setData(userService.findByCondition(user, PageUtil.initPage(pageVo)));
    }


    //添加用户
    @RequestMapping(value = "/addByAdmin", method = RequestMethod.POST)
    @RequiresRoles("admin")
    public Result addByAdmin(@ModelAttribute User u,
                             @RequestParam(required = false) String[] roles) {
        return ResultUtil.setData(userService.save(u, roles));
    }


    //后台禁用用户
    @RequestMapping(value = "/disable", method = RequestMethod.POST)
    @RequiresRoles("admin")
    public Result disable(@RequestParam String userId) {
        userService.disable(userId);
        return ResultUtil.setSuccessMsg("禁用用户成功");
    }


    //后台启用用户
    @RequestMapping(value = "/enable", method = RequestMethod.POST)
    @RequiresRoles("admin")
    public Result enable(@RequestParam String userId) {
        userService.enable(userId);
        return ResultUtil.setSuccessMsg("启用用户成功");
    }


    //批量通过ids删除
    @RequestMapping(value = "/delByIds", method = RequestMethod.DELETE)
    @RequiresRoles("admin")
    public Result delByIds(@RequestParam String[] ids) {
        userService.delByIds(ids);
        return ResultUtil.setSuccessMsg("批量通过id删除数据成功");
    }
}