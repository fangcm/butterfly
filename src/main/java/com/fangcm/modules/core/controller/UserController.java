package com.fangcm.modules.core.controller;

import com.fangcm.common.constant.CommonConstant;
import com.fangcm.common.entity.PageVo;
import com.fangcm.common.entity.Result;
import com.fangcm.common.utils.JasyptUtil;
import com.fangcm.common.utils.PageUtil;
import com.fangcm.common.utils.ResultUtil;
import com.fangcm.modules.core.entity.Role;
import com.fangcm.modules.core.entity.User;
import com.fangcm.modules.core.entity.UserRole;
import com.fangcm.modules.core.service.RoleService;
import com.fangcm.modules.core.service.UserRoleService;
import com.fangcm.modules.core.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by FangCM on 2018/5/23.
 */

@RestController
@RequestMapping("/core")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;

    //注册用户
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Result<Object> register(@ModelAttribute User u,
                                   @RequestParam String verify,
                                   @RequestParam String captchaId) {

/*
        if(StringUtils.isBlank(verify)){
            return new ResultUtil<Object>().setErrorMsg("必须填写验证码");
        }
        //验证码
        String code=redisTemplate.opsForValue().get(captchaId);
        if(StrUtil.isBlank(code)){
            return new ResultUtil<Object>().setErrorMsg("验证码已过期，请重新获取");
        }

        if(!verify.toLowerCase().equals(code.toLowerCase())) {
            log.error("注册失败，验证码错误：code:"+ verify +",redisCode:"+code.toLowerCase());
            return new ResultUtil<Object>().setErrorMsg("验证码输入错误");
        }
*/
        if (StringUtils.isBlank(u.getMobile())) {
            return new ResultUtil<Object>().setErrorMsg("必需填写手机号码");
        }
        if (StringUtils.isBlank(u.getNickName())) {
            return new ResultUtil<Object>().setErrorMsg("必需填写昵称");
        }
        if (StringUtils.isBlank(u.getPassword())) {
            return new ResultUtil<Object>().setErrorMsg("必须填写密码");
        }
        if (userService.findByMobile(u.getMobile()) != null) {
            return new ResultUtil<Object>().setErrorMsg("该手机号码已被注册");
        }
        if (userService.findByNickName(u.getNickName()) != null) {
            return new ResultUtil<Object>().setErrorMsg("该昵称已被注册");
        }

        String encryptPass = JasyptUtil.encryptPwd(u.getPassword());
        u.setPassword(encryptPass);
        u.setType(CommonConstant.USER_TYPE_NORMAL);
        User user = userService.save(u);
        if (user == null) {
            return new ResultUtil<Object>().setErrorMsg("注册失败");
        }

        return new ResultUtil<Object>().setData(user);
    }


    //获取当前登录用户接口
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Result<User> getUserInfo() {

        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User u = userService.findByMobile(user.getUsername());
        u.setPassword(null);
        return new ResultUtil<User>().setData(u);
    }


    //修改用户自己资料
    //用户名密码不会修改 需要通过id获取原用户信息
    @RequestMapping(value = "/editOwn", method = RequestMethod.POST)
    public Result<Object> editOwn(@ModelAttribute User u) {

        User old = userService.get(u.getId());
        u.setPassword(old.getPassword());
        User user = userService.update(u);
        if (user == null) {
            return new ResultUtil<Object>().setErrorMsg("修改失败");
        }
        return new ResultUtil<Object>().setSuccessMsg("修改成功");
    }

    /**
     * 修改资料仅允许ADMIN权限
     * 用户名密码不会修改 需要通过id获取原用户信息
     */
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public Result<Object> edit(@ModelAttribute User u,
                               @RequestParam(required = false) String[] roles) {

        User old = userService.get(u.getId());
        u.setPassword(old.getPassword());
        User user = userService.update(u);
        if (user == null) {
            return new ResultUtil<Object>().setErrorMsg("修改失败");
        }
        //删除该用户角色
        userRoleService.deleteByUserId(u.getId());
        if (roles != null && roles.length > 0) {
            //新角色
            for (String roleId : roles) {
                UserRole ur = new UserRole();
                ur.setRoleId(roleId);
                ur.setUserId(u.getId());
                userRoleService.save(ur);
            }
        }
        return new ResultUtil<Object>().setSuccessMsg("修改成功");
    }

    /**
     * 修改密码仅允许ADMIN权限改密码
     */
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/modifyPass", method = RequestMethod.POST)
    public Result<Object> modifyPass(@RequestParam String userId,
                                     @RequestParam String password,
                                     @RequestParam String newPass) {

        User old = userService.get(userId);

        if (!new BCryptPasswordEncoder().matches(password, old.getPassword())) {
            return new ResultUtil<Object>().setErrorMsg("旧密码不正确");
        }

        String newEncryptPass = new BCryptPasswordEncoder().encode(newPass);
        old.setPassword(newEncryptPass);
        User user = userService.update(old);
        if (user == null) {
            return new ResultUtil<Object>().setErrorMsg("修改失败");
        }

        return new ResultUtil<Object>().setData(user);
    }


    //多条件分页获取用户列表
    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    public Result<Page<User>> getByCondition(@ModelAttribute User user, @ModelAttribute PageVo pageVo) {

        Page<User> page = userService.findByCondition(user, PageUtil.initPage(pageVo));
        for (User u : page.getContent()) {
            List<Role> list = roleService.findByUserId(u.getId());
            u.setRoles(list);
            u.setPassword(null);
        }
        return new ResultUtil<Page<User>>().setData(page);
    }


    //添加用户
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/admin/add", method = RequestMethod.POST)
    public Result<Object> register(@ModelAttribute User u,
                                   @RequestParam(required = false) String[] roles) {

        if (StringUtils.isBlank(u.getMobile())) {
            return new ResultUtil<Object>().setErrorMsg("必需填写手机号码");
        }
        if (StringUtils.isBlank(u.getNickName())) {
            return new ResultUtil<Object>().setErrorMsg("必需填写昵称");
        }
        if (StringUtils.isBlank(u.getPassword())) {
            return new ResultUtil<Object>().setErrorMsg("必须填写密码");
        }
        if (userService.findByMobile(u.getMobile()) != null) {
            return new ResultUtil<Object>().setErrorMsg("该手机号码已被注册");
        }
        if (userService.findByNickName(u.getNickName()) != null) {
            return new ResultUtil<Object>().setErrorMsg("该昵称已被注册");
        }

        String encryptPass = new BCryptPasswordEncoder().encode(u.getPassword());
        u.setPassword(encryptPass);
        User user = userService.save(u);
        if (user == null) {
            return new ResultUtil<Object>().setErrorMsg("添加失败");
        }
        if (roles != null && roles.length > 0) {
            //添加角色
            for (String roleId : roles) {
                UserRole ur = new UserRole();
                ur.setUserId(u.getId());
                ur.setRoleId(roleId);
                userRoleService.save(ur);
            }
        }

        return new ResultUtil<Object>().setData(user);
    }


    //后台禁用用户
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/admin/disable", method = RequestMethod.POST)
    public Result<Object> disable(@RequestParam String userId) {

        User user = userService.get(userId);
        if (user == null) {
            return new ResultUtil<Object>().setErrorMsg("通过userId获取用户失败");
        }
        user.setStatus(CommonConstant.USER_STATUS_DISABLE);
        userService.update(user);
        return new ResultUtil<Object>().setData(null);
    }


    //后台启用用户
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/admin/enable", method = RequestMethod.POST)
    public Result<Object> enable(@RequestParam String userId) {

        User user = userService.get(userId);
        if (user == null) {
            return new ResultUtil<Object>().setErrorMsg("通过userId获取用户失败");
        }
        user.setStatus(CommonConstant.USER_STATUS_NORMAL);
        userService.update(user);
        return new ResultUtil<Object>().setData(null);
    }


    //批量通过ids删除
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/delByIds", method = RequestMethod.DELETE)
    public Result<Object> delAllByIds(@RequestParam String[] ids) {

        for (String id : ids) {
            userService.delete(id);
        }
        return new ResultUtil<Object>().setSuccessMsg("批量通过id删除数据成功");
    }
}