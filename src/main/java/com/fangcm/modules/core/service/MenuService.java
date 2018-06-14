package com.fangcm.modules.core.service;

import com.fangcm.common.utils.BeanUtil;
import com.fangcm.modules.core.dao.MenuDao;
import com.fangcm.modules.core.entity.Menu;
import com.fangcm.modules.core.vo.MenuDTO;
import com.fangcm.modules.core.vo.MenuVO;
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

    private MenuVO fillDataVO(Menu data) {
        MenuVO vo = BeanUtil.copy(data, MenuVO.class);

        return vo;
    }

    private List<MenuVO> fillDataVO(List<Menu> dataList) {
        List<MenuVO> voList = new ArrayList<>();
        if (dataList != null) {
            for (Menu data : dataList) {
                MenuVO vo = fillDataVO(data);
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
    public MenuVO save(MenuDTO dto) {
        Menu item = BeanUtil.copy(dto, Menu.class);
        return fillDataVO(menuDao.save(item));
    }

    /**
     * 根据Id删除
     */
    public void deleteById(String id) {
        menuDao.deleteById(id);
    }


    public List<MenuVO> getMenuTree() {
        List<MenuVO> voList = fillDataVO(menuDao.findRootLevel());
        for (MenuVO vo : voList) {
            if (vo.getRootLevel()) {
                List<MenuVO> children = fillDataVO(menuDao.findByParentId(vo.getId()));
                if (children.size() > 0) {
                    vo.setChildren(children);
                }
            }
        }
        return voList;
    }

}
