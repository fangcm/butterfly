package com.fangcm.modules.core.controller;

import com.fangcm.common.entity.Result;
import com.fangcm.common.utils.JWTUtil;
import com.fangcm.common.utils.ResultUtil;
import com.fangcm.config.security.JWTToken;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Security相关接口
@RestController
@RequestMapping("/core")
public class SecurityController {

    /**
     * 登录请求
     */
    @PostMapping("/login")
    public Result<Object> submitLogin(String username, String password) {
        JWTToken token = new JWTToken(JWTUtil.sign(username, password));
        SecurityUtils.getSubject().login(token);
        return new ResultUtil<Object>().setSuccessMsg("登录成功");
    }

    /**
     * 退出
     */
    @GetMapping(value = "/logout")
    public Result<Object> logout() {
        SecurityUtils.getSubject().logout();
        return new ResultUtil<Object>().setSuccessMsg("退出成功");
    }
}