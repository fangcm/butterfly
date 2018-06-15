package com.fangcm.modules.core.service;

import com.fangcm.exception.ButterflyException;
import com.fangcm.modules.core.dao.RoleDao;
import com.fangcm.modules.core.dao.UserRoleDao;
import com.fangcm.modules.core.entity.UserRole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by FangCM on 2018/5/24.
 */
@Service
@Transactional
public class RoleService {

    @Resource
    private RoleDao roleDao;

    @Resource
    private UserRoleDao userRoleDao;

    public void delByIds(String[] ids) {
        for (String id : ids) {
            List<UserRole> list = userRoleDao.findByRoleId(id);
            if (list != null && list.size() > 0) {
                throw new ButterflyException("删除失败，包含正被使用中的角色");
            }
        }

        for (String id : ids) {
            roleDao.deleteById(id);
        }
    }

}
