package com.fangcm.modules.core.service;

import com.fangcm.common.utils.BeanUtil;
import com.fangcm.common.rest.PageUtil;
import com.fangcm.modules.core.dao.MenuDao;
import com.fangcm.modules.core.entity.Menu;
import com.fangcm.modules.core.vo.MenuForm;
import com.fangcm.modules.core.vo.MenuDTO;
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
public class MenuService {

    @Resource
    private MenuDao menuDao;

    private MenuDTO transformToDTO(Menu data) {
        MenuDTO dto = BeanUtil.copy(data, MenuDTO.class);

        return dto;
    }

    private List<MenuDTO> transformToDTO(List<Menu> dataList) {
        List<MenuDTO> dtoList = new ArrayList<>();
        if (dataList != null) {
            for (Menu data : dataList) {
                MenuDTO dto = transformToDTO(data);
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
    public MenuDTO save(MenuForm form) {
        Menu item = BeanUtil.copy(form, Menu.class);
        return transformToDTO(menuDao.save(item));
    }

    /**
     * 根据Id删除
     */
    public void deleteById(String id) {
        menuDao.deleteById(id);
    }

    public Page<MenuDTO> findByPage(Pageable pageable) {
        Page<Menu> pageData = menuDao.findAll(pageable);
        return PageUtil.pageWrap(transformToDTO(pageData.getContent()), pageData);
    }

    public List<MenuDTO> getMenuTree() {
        List<MenuDTO> dtoList = transformToDTO(menuDao.findRootLevel());
        for (MenuDTO dto : dtoList) {
            if (dto.getRootLevel()) {
                List<MenuDTO> children = transformToDTO(menuDao.findByParentId(dto.getId()));
                if (children.size() > 0) {
                    dto.setChildren(children);
                }
            }
        }
        return dtoList;
    }

}
