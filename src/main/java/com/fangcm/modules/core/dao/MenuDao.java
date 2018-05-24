package com.fangcm.modules.core.dao;

import com.fangcm.base.BaseDao;
import com.fangcm.modules.core.entity.Menu;

import java.util.List;

/**
 * Created by FangCM on 2018/5/23.
 */


public interface MenuDao extends BaseDao<Menu, String> {

    /**
     * 获取一级菜单
     */
    List<Menu> findByParent(Boolean parent);

    /**
     * 通过parendId查找
     */
    List<Menu> findByParentId(String parentId);
}