package com.fangcm.modules.core.service;

import com.fangcm.base.BaseService;
import com.fangcm.common.constant.CommonConstant;
import com.fangcm.exception.ButterflyException;
import com.fangcm.modules.core.dao.RoleDao;
import com.fangcm.modules.core.dao.UserDao;
import com.fangcm.modules.core.dao.UserRoleDao;
import com.fangcm.modules.core.entity.Role;
import com.fangcm.modules.core.entity.User;
import com.fangcm.modules.core.entity.UserRole;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by FangCM on 2018/5/23.
 */
@Service
@Transactional
public class UserService implements BaseService<User, String> {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public UserDao getRepository() {
        return userDao;
    }

    private User fillUserRoles(User user) {
        List<Role> roleList = roleDao.findByUserId(user.getId());
        user.setRoles(roleList);
        return user;
    }

    public User getCurrentUserInfo() {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user == null) {
            return null;
        }
        String mobile = user.getUsername();
        User u = findByMobile(mobile);
        if (u == null) {
            return null;
        }

        u.setPassword(null);
        return u;
    }

    public User save(User u, String verify, String captchaId, String[] roles) {
        if (u == null) {
            throw new ButterflyException("用户参数错误");
        }

/*
        if(StringUtils.isBlank(verify)){
            throw new ButterflyException("必须填写验证码");
        }
        //验证码
        String code=redisTemplate.opsForValue().get(captchaId);
        if(StrUtil.isBlank(code)){
            throw new ButterflyException("验证码已过期，请重新获取");
        }

        if(!verify.toLowerCase().equals(code.toLowerCase())) {
            log.error("注册失败，验证码错误：code:"+ verify +",redisCode:"+code.toLowerCase());
            throw new ButterflyException("验证码输入错误");
        }
*/
        if (StringUtils.isBlank(u.getMobile())) {
            throw new ButterflyException("必需填写手机号码");
        }
        if (StringUtils.isBlank(u.getNickName())) {
            throw new ButterflyException("必需填写昵称");
        }

        User old = null;
        if (StringUtils.isNotBlank(u.getId())) {
            //修改信息
            old = userDao.getOne(u.getId());
        } else {
            //新建信息
            if (StringUtils.isBlank(u.getPassword())) {
                throw new ButterflyException("必须填写密码");
            }
        }

        User userByMobile = this.findByMobile(u.getMobile());
        if (userByMobile != null) {
            if (old == null) {
                //新建信息
                throw new ButterflyException("该手机号码已被注册");
            } else {
                //修改信息
                if (!StringUtils.equals(userByMobile.getId(), old.getId())) {
                    throw new ButterflyException("该手机号码已被注册");
                }
            }
        }

        User userByNickName = this.findByNickName(u.getNickName());
        if (userByNickName != null) {
            if (old == null) {
                //新建信息
                throw new ButterflyException("该昵称已被注册");
            } else {
                //修改信息
                if (!StringUtils.equals(userByNickName.getId(), old.getId())) {
                    throw new ButterflyException("该昵称已被注册");
                }
            }
        }

        if (old == null) {
            //新建信息
            String encryptPass = new BCryptPasswordEncoder().encode(u.getPassword());
            u.setPassword(encryptPass);
        } else {
            //修改信息
            u.setPassword(old.getPassword());
        }

        u = userDao.save(u);
        if (roles != null) {
            //删除该用户角色
            userRoleDao.deleteByUserId(u.getId());
            if (roles.length > 0) {
                //新角色
                for (String roleId : roles) {
                    UserRole ur = new UserRole();
                    ur.setRoleId(roleId);
                    ur.setUserId(u.getId());
                    userRoleDao.save(ur);
                }
            }
        }
        return u;
    }

    public void modifyPass(String userId, String password, String newPass) {
        User old = userDao.getOne(userId);

        if (!new BCryptPasswordEncoder().matches(password, old.getPassword())) {
            throw new ButterflyException("旧密码不正确");
        }

        String newEncryptPass = new BCryptPasswordEncoder().encode(newPass);
        old.setPassword(newEncryptPass);
        userDao.save(old);
    }


    //后台禁用用户
    public void disable(String userId) {
        User user = userDao.getOne(userId);
        user.setStatus(CommonConstant.USER_STATUS_DISABLE);
        userDao.save(user);
    }


    //后台启用用户
    public void enable(String userId) {
        User user = userDao.getOne(userId);
        user.setStatus(CommonConstant.USER_STATUS_NORMAL);
        userDao.save(user);
    }

    public User findByMobile(String mobile) {

        User user = userDao.findByMobile(mobile);
        if (user != null) {
            return fillUserRoles(user);
        }
        return null;
    }

    public User findByNickName(String nickName) {

        User user = userDao.findByNickName(nickName);
        if (user != null) {
            return fillUserRoles(user);
        }
        return null;
    }

    public Page<User> findByCondition(User user, Pageable pageable) {

        Page<User> page = userDao.findAll(new Specification<User>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                Path<String> nickNameField = root.get("nickName");
                Path<String> mobileField = root.get("mobile");
                Path<String> emailField = root.get("email");
                Path<Integer> statusField = root.get("status");

                List<Predicate> list = new ArrayList<>();
                list.add(cb.equal(root.get("delFlag"), CommonConstant.DEL_FLAG_NORMAL));

                //模糊搜素
                if (StringUtils.isNotBlank(user.getNickName())) {
                    list.add(cb.like(nickNameField, '%' + user.getNickName() + '%'));
                }
                if (StringUtils.isNotBlank(user.getMobile())) {
                    list.add(cb.like(mobileField, '%' + user.getMobile() + '%'));
                }
                if (StringUtils.isNotBlank(user.getEmail())) {
                    list.add(cb.like(emailField, '%' + user.getEmail() + '%'));
                }

                //状态
                if (user.getStatus() != null) {
                    list.add(cb.equal(statusField, user.getStatus()));
                }

                Predicate[] arr = new Predicate[list.size()];
                cq.where(list.toArray(arr));
                return null;
            }
        }, pageable);

        for (User u : page.getContent()) {
            fillUserRoles(u);
            u.setPassword(null);
        }
        return page;
    }

}
