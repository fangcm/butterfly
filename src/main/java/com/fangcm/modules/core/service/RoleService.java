package com.fangcm.modules.core.service;

import com.fangcm.common.rest.PageUtil;
import com.fangcm.common.utils.BeanUtil;
import com.fangcm.exception.ButterflyException;
import com.fangcm.modules.core.dao.RoleDao;
import com.fangcm.modules.core.dao.UserRoleDao;
import com.fangcm.modules.core.entity.Role;
import com.fangcm.modules.core.entity.UserRole;
import com.fangcm.modules.core.vo.RoleForm;
import com.fangcm.modules.core.vo.RoleDTO;
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

    public static RoleDTO transformToDTO(Role data) {
        RoleDTO dto = BeanUtil.copy(data, RoleDTO.class);

        return dto;
    }

    public static List<RoleDTO> transformToDTO(List<Role> dataList) {
        List<RoleDTO> dtoList = new ArrayList<>();
        if (dataList != null) {
            for (Role data : dataList) {
                RoleDTO dto = transformToDTO(data);
                if (dto != null) {
                    dtoList.add(dto);
                }
            }
        }
        return dtoList;
    }

    /**
     * 保存 or 修改
     */
    public RoleDTO save(RoleForm dto) {
        Role entity = BeanUtil.copy(dto, Role.class);
        return transformToDTO(roleDao.save(entity));
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
    public Page<RoleDTO> findByPage(Pageable pageable) {
        Page<Role> pageData = roleDao.findAll(pageable);
        return PageUtil.pageWrap(transformToDTO(pageData.getContent()), pageData);
    }

}
