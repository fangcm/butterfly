package com.fangcm.modules.core.controller;

import com.fangcm.common.entity.PageVo;
import com.fangcm.common.entity.Result;
import com.fangcm.common.utils.PageUtil;
import com.fangcm.common.utils.ResultUtil;
import com.fangcm.modules.core.entity.User;
import com.fangcm.modules.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Created by FangCM on 2018/5/23.
 */

@RestController
@RequestMapping("/core/user")
public class UserController {

    @Autowired
    private UserService userService;

    //获取当前登录用户接口
    @RequestMapping(value = "/currentInfo", method = RequestMethod.GET)
    public Result<User> getUserInfo() {
        return new ResultUtil<User>().setData(userService.getCurrentUserInfo());
    }

    //注册用户
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Result<Object> register(@ModelAttribute User u,
                                   @RequestParam String verify,
                                   @RequestParam String captchaId) {
        return new ResultUtil<Object>().setData(userService.save(u, verify, captchaId, null));
    }


    //修改用户自己资料
    //用户名密码不会修改 需要通过id获取原用户信息
    @RequestMapping(value = "/editOwn", method = RequestMethod.POST)
    public Result<Object> editOwn(@ModelAttribute User u) {
        userService.save(u, null, null, null);
        return new ResultUtil<Object>().setSuccessMsg("修改成功");
    }

    /**
     * 修改资料仅允许ADMIN权限
     * 用户名密码不会修改 需要通过id获取原用户信息
     */
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/editByAdmin", method = RequestMethod.POST)
    public Result<Object> edit(@ModelAttribute User u,
                               @RequestParam(required = false) String[] roles) {
        userService.save(u, null, null, roles);
        return new ResultUtil<Object>().setSuccessMsg("修改成功");
    }

    /**
     * 修改密码仅允许ADMIN权限改密码
     */
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/modifyPassByAdmin", method = RequestMethod.POST)
    public Result<Object> modifyPass(@RequestParam String userId,
                                     @RequestParam String password,
                                     @RequestParam String newPass) {
        userService.modifyPass(userId, password, newPass);
        return new ResultUtil<Object>().setSuccessMsg("修改密码成功");
    }


    //多条件分页获取用户列表
    @RequestMapping(value = "/findByCondition", method = RequestMethod.GET)
    public Result<Page<User>> findByCondition(@ModelAttribute User user, @ModelAttribute PageVo pageVo) {
        return new ResultUtil<Page<User>>().setData(userService.findByCondition(user, PageUtil.initPage(pageVo)));
    }


    //添加用户
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/addByAdmin", method = RequestMethod.POST)
    public Result<Object> register(@ModelAttribute User u,
                                   @RequestParam(required = false) String[] roles) {
        return new ResultUtil<Object>().setData(userService.save(u, null, null, roles));
    }


    //后台禁用用户
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/disable", method = RequestMethod.POST)
    public Result<Object> disable(@RequestParam String userId) {
        userService.disable(userId);
        return new ResultUtil<Object>().setSuccessMsg("禁用用户成功");
    }


    //后台启用用户
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/enable", method = RequestMethod.POST)
    public Result<Object> enable(@RequestParam String userId) {
        userService.enable(userId);
        return new ResultUtil<Object>().setSuccessMsg("启用用户成功");
    }


    //批量通过ids删除
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/delByIds", method = RequestMethod.DELETE)
    public Result<Object> delByIds(@RequestParam String[] ids) {
        userService.delByIds(ids);
        return new ResultUtil<Object>().setSuccessMsg("批量通过id删除数据成功");
    }
}