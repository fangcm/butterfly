package com.fangcm.modules.core.service;

import com.fangcm.base.BaseService;
import com.fangcm.modules.core.dao.RoleDao;
import com.fangcm.modules.core.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by FangCM on 2018/5/24.
 */
@Service
@Transactional
public class RoleService implements BaseService<Role, String> {

    @Autowired
    private RoleDao roleDao;

    @Override
    public RoleDao getRepository() {
        return roleDao;
    }

    public List<Role> findByUserId(String userId) {
        return roleDao.findByUserId(userId);
    }

    public List<Role> findByDefaultRole(Boolean defaultRole) {
        return roleDao.findByDefaultRole(defaultRole);
    }
}
