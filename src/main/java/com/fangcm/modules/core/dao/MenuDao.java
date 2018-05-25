package com.fangcm.modules.core.dao;

import com.fangcm.base.BaseDao;
import com.fangcm.modules.core.entity.Menu;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by FangCM on 2018/5/23.
 */


public interface MenuDao extends BaseDao<Menu, String> {

    /**
     * 获取根级菜单
     */
    @Query("select m from Menu m where m.rootLevel=true AND m.delFlag=0 order by m.sort asc")
    List<Menu> findRootLevel();

    /**
     * 通过parendId查找
     */
    @Query("select m from Menu m where m.parentId=?1 AND m.delFlag=0 order by m.sort asc")
    List<Menu> findByParentId(String parentId);
}