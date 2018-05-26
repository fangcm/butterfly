package com.fangcm.modules.core.controller;

import com.fangcm.common.entity.Result;
import com.fangcm.common.utils.ResultUtil;
import com.fangcm.modules.core.entity.Menu;
import com.fangcm.modules.core.service.MenuService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by FangCM on 2018/5/24.
 */

//菜单管理接口
//拥有ROLE_ADMIN角色的用户可以访问
@RestController
@RequestMapping("/core/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;


    //获取数据
    @GetMapping("/findAll")
    public Result<List<Menu>> findAll() {
        return new ResultUtil<List<Menu>>().setData(menuService.findAll());
    }

    //添加 or 编辑
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @RequiresRoles("admin")
    public Result<Menu> add(@ModelAttribute Menu menu) {
        return new ResultUtil<Menu>().setData(menuService.save(menu));
    }

    //批量通过id删除
    @RequestMapping(value = "/delByIds", method = RequestMethod.DELETE)
    @RequiresRoles("admin")
    public Result<Object> delByIds(@RequestParam String[] ids) {
        menuService.delByIds(ids);
        return new ResultUtil<Object>().setSuccessMsg("批量通过id删除数据成功");
    }

}
