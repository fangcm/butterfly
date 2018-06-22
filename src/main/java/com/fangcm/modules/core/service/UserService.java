package com.fangcm.modules.core.service;

import com.fangcm.common.rest.PageUtil;
import com.fangcm.common.utils.BeanUtil;
import com.fangcm.common.utils.JWTUtil;
import com.fangcm.common.utils.UserUtil;
import com.fangcm.config.security.JWTToken;
import com.fangcm.exception.ButterflyException;
import com.fangcm.exception.UnauthorizedException;
import com.fangcm.modules.core.dao.RoleDao;
import com.fangcm.modules.core.dao.UserDao;
import com.fangcm.modules.core.dao.UserRoleDao;
import com.fangcm.modules.core.entity.User;
import com.fangcm.modules.core.entity.UserRole;
import com.fangcm.modules.core.vo.LoginDTO;
import com.fangcm.modules.core.vo.UserDTO;
import com.fangcm.modules.core.vo.UserFilter;
import com.fangcm.modules.core.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by FangCM on 2018/5/23.
 */
@Service
@Transactional
public class UserService {

    @Resource
    private UserDao userDao;

    @Resource
    private RoleDao roleDao;

    @Resource
    private UserRoleDao userRoleDao;

    private UserVO transformToVO(User data) {
        UserVO vo = BeanUtil.copy(data, UserVO.class);
        vo.setRoles(RoleService.transformToVO(roleDao.findByUserId(data.getId())));
        return vo;
    }

    private List<UserVO> transformToVO(List<User> dataList) {
        List<UserVO> voList = new ArrayList<>();
        if (dataList != null) {
            for (User data : dataList) {
                UserVO vo = transformToVO(data);
                if (vo != null) {
                    voList.add(vo);
                }
            }
        }
        return voList;
    }

    public UserVO getCurrentUserInfo() {
        Subject subject = SecurityUtils.getSubject();
        if (subject == null) {
            return null;
        }
        String mobile = JWTUtil.getUsername(subject.getPrincipal().toString());
        User u = userDao.findByMobile(mobile);
        if (u == null) {
            return null;
        }

        u.setPassword(null);
        return transformToVO(u);
    }

    public UserVO save(UserDTO dto, String[] roles) {
        if (dto == null) {
            throw new ButterflyException("用户参数错误");
        }

        if (StringUtils.isBlank(dto.getMobile())) {
            throw new ButterflyException("必需填写手机号码");
        }
        if (StringUtils.isBlank(dto.getNickName())) {
            throw new ButterflyException("必需填写昵称");
        }

        User old = null;
        if (StringUtils.isNotBlank(dto.getId())) {
            //修改信息
            old = userDao.getOne(dto.getId());
        } else {
            //新建信息
            if (StringUtils.isBlank(dto.getPassword())) {
                throw new ButterflyException("必须填写密码");
            }
        }

        User userByMobile = userDao.findByMobile(dto.getMobile());
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

        User userByNickName = userDao.findByNickName(dto.getNickName());
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

        User u = BeanUtil.copy(dto, User.class);
        if (old == null) {
            //新建信息
            String encryptPass = UserUtil.encrypt(u.getPassword());
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
        return transformToVO(u);
    }

    /**
     * 根据Id删除
     */
    public void deleteById(String userId) {
        userDao.deleteById(userId);
    }

    public String login(LoginDTO loginDTO) {
        User user = userDao.findByMobile(loginDTO.getMobile());
        if (user == null) {
            throw new UnauthorizedException("没有找到该用户");
        }

        String secret = UserUtil.encrypt(loginDTO.getPassword());
        if (!StringUtils.equals(secret, user.getPassword())) {
            throw new UnauthorizedException("密码不正确");
        }

        JWTToken token = new JWTToken(JWTUtil.sign(loginDTO.getMobile(), secret));
        return token.getCredentials().toString();
    }

    public void modifyPass(String userId, String password, String newPass) {
        User old = userDao.getOne(userId);
        if (!StringUtils.equalsIgnoreCase(UserUtil.encrypt(password), old.getPassword())) {
            throw new ButterflyException("旧密码不正确");
        }

        String newEncryptPass = UserUtil.encrypt(newPass);
        old.setPassword(newEncryptPass);
        userDao.save(old);
    }


    //后台禁用用户
    public void disable(String userId) {
        User user = userDao.getOne(userId);
        user.setStatus(User.USER_STATUS_DISABLE);
        userDao.save(user);
    }


    //后台启用用户
    public void enable(String userId) {
        User user = userDao.getOne(userId);
        user.setStatus(User.USER_STATUS_NORMAL);
        userDao.save(user);
    }

    public Page<UserVO> findByCondition(UserFilter filter, Pageable pageable) {

        Page<User> pageData = userDao.findAll(new Specification<User>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                Path<String> nickNameField = root.get("nickName");
                Path<String> mobileField = root.get("mobile");
                Path<String> emailField = root.get("email");
                Path<Integer> statusField = root.get("status");

                List<Predicate> list = new ArrayList<>();
                list.add(cb.equal(root.get("delFlag"), User.DEL_FLAG_NORMAL));

                //模糊搜素
                if (StringUtils.isNotBlank(filter.getNickName())) {
                    list.add(cb.like(nickNameField, '%' + filter.getNickName() + '%'));
                }
                if (StringUtils.isNotBlank(filter.getMobile())) {
                    list.add(cb.like(mobileField, '%' + filter.getMobile() + '%'));
                }
                if (StringUtils.isNotBlank(filter.getEmail())) {
                    list.add(cb.like(emailField, '%' + filter.getEmail() + '%'));
                }

                //状态
                if (filter.getStatus() != null) {
                    list.add(cb.equal(statusField, filter.getStatus()));
                }

                Predicate[] arr = new Predicate[list.size()];
                cq.where(list.toArray(arr));
                return null;
            }
        }, pageable);

        return PageUtil.pageWrap(transformToVO(pageData.getContent()), pageData);
    }

}
