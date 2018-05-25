package com.fangcm.modules.core.service;

import com.fangcm.base.BaseService;
import com.fangcm.modules.core.dao.MenuDao;
import com.fangcm.modules.core.entity.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by FangCM on 2018/5/24.
 */
@Service
@Transactional
public class MenuService implements BaseService<Menu, String> {

    @Autowired
    private MenuDao menuDao;

    @Override
    public MenuDao getRepository() {
        return menuDao;
    }


    @Override
    public List<Menu> findAll() {

        List<Menu> list = menuDao.findRootLevel();
        for (Menu menu : list) {
            List<Menu> children = menuDao.findByParentId(menu.getId());
            menu.setChildren(children);
        }
        return list;
    }

}
