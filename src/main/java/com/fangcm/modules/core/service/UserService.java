package com.fangcm.modules.core.service;

import com.fangcm.base.BaseService;
import com.fangcm.common.constant.CommonConstant;
import com.fangcm.modules.core.dao.RoleDao;
import com.fangcm.modules.core.dao.UserDao;
import com.fangcm.modules.core.entity.Role;
import com.fangcm.modules.core.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
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

    @Override
    public UserDao getRepository() {
        return userDao;
    }

    private User fillUserRoles(User user) {
        List<Role> roleList = roleDao.findByUserId(user.getId());
        user.setRoles(roleList);
        return user;
    }

    public User findByMobile(String mobile) {

        List<User> list = userDao.findByMobileAndStatus(mobile, CommonConstant.USER_STATUS_NORMAL);
        if (list != null && list.size() > 0) {
            return fillUserRoles(list.get(0));
        }
        return null;
    }

    public User findByNickName(String nickName) {

        List<User> list = userDao.findByNickNameAndStatus(nickName, CommonConstant.USER_STATUS_NORMAL);
        if (list != null && list.size() > 0) {
            return fillUserRoles(list.get(0));
        }
        return null;
    }

    public List<User> findByStatusAndType(Integer status, Integer type) {

        return userDao.findByStatusAndType(status, type);
    }

    public Page<User> findByCondition(User user, Pageable pageable) {

        return userDao.findAll(new Specification<User>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                Path<String> nickNameField = root.get("nickName");
                Path<String> mobileField = root.get("mobile");
                Path<String> emailField = root.get("email");
                Path<Integer> sexField = root.get("sex");
                Path<Integer> typeField = root.get("type");
                Path<Integer> statusField = root.get("status");

                List<Predicate> list = new ArrayList<Predicate>();

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

                //类型
                if (user.getType() != null) {
                    list.add(cb.equal(typeField, user.getType()));
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
    }

}
