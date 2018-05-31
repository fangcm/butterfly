package com.fangcm.modules.core.controller;

import com.fangcm.common.entity.Result;
import com.fangcm.common.utils.JWTUtil;
import com.fangcm.common.utils.ResultUtil;
import com.fangcm.config.security.JWTToken;
import com.fangcm.modules.core.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.*;

//Security相关接口
@RestController
@RequestMapping("/core")
public class SecurityController {

    /**
     * 登录请求
     */
    @PostMapping("/login")
    public Result<Object> submitLogin(@RequestBody(required=true) User u) {
        JWTToken token = new JWTToken(JWTUtil.sign(u.getMobile(), u.getPassword()));
        SecurityUtils.getSubject().login(token);
        return new ResultUtil<Object>().setSuccessMsg("登录成功");
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
}