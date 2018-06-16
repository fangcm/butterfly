package com.fangcm.modules.core.service;

import com.fangcm.common.utils.BeanUtil;
import com.fangcm.common.rest.PageUtil;
import com.fangcm.exception.ButterflyException;
import com.fangcm.modules.core.dao.RoleDao;
import com.fangcm.modules.core.dao.UserRoleDao;
import com.fangcm.modules.core.entity.Role;
import com.fangcm.modules.core.entity.UserRole;
import com.fangcm.modules.core.vo.RoleDTO;
import com.fangcm.modules.core.vo.RoleVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
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

    private RoleVO transformToVO(Role data) {
        RoleVO vo = BeanUtil.copy(data, RoleVO.class);

        return vo;
    }

    private List<RoleVO> transformToVO(List<Role> dataList) {
        List<RoleVO> voList = new ArrayList<>();
        if (dataList != null) {
            for (Role data : dataList) {
                RoleVO vo = transformToVO(data);
                if (vo != null) {
                    voList.add(vo);
                }
            }
        }
        return voList;
    }

    /**
     * 保存 or 修改
     */
    public RoleVO save(RoleDTO dto) {
        Role entity = BeanUtil.copy(dto, Role.class);
        return transformToVO(roleDao.save(entity));
    }

    public void deleteById(String id) {
        List<UserRole> list = userRoleDao.findByRoleId(id);
        if (list != null && list.size() > 0) {
            throw new ButterflyException("删除失败，包含正被使用中的角色");
        }
        roleDao.deleteById(id);
    }

    /**
     * 分页获取
     */
    public Page<RoleVO> findByPage(Pageable pageable) {
        Page<Role> pageData = roleDao.findAll(pageable);
        return PageUtil.pageWrap(transformToVO(pageData.getContent()), pageData);
    }

}
