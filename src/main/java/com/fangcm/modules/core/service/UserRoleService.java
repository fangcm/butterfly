package com.fangcm.modules.core.service;

import com.fangcm.base.BaseService;
import com.fangcm.modules.core.dao.UserRoleDao;
import com.fangcm.modules.core.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by FangCM on 2018/5/24.
 */
@Service
@Transactional
public class UserRoleService implements BaseService<UserRole, String> {

    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public UserRoleDao getRepository() {
        return userRoleDao;
    }

    public List<UserRole> findByRoleId(String roleId) {
        return userRoleDao.findByRoleId(roleId);
    }

    public void deleteByUserId(String userId) {
        userRoleDao.deleteByUserId(userId);
    }

}
