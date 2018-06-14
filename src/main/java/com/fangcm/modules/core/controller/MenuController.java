package com.fangcm.modules.core.controller;

import com.fangcm.common.entity.Result;
import com.fangcm.common.utils.ResultUtil;
import com.fangcm.modules.core.service.MenuService;
import com.fangcm.modules.core.vo.MenuDTO;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by FangCM on 2018/5/24.
 */

//菜单管理接口
@RestController
@RequestMapping("/core/menu")
public class MenuController {

    @Resource
    private MenuService menuService;


    //获取数据
    @GetMapping("/getMenuTree")
    public Result findAll() {
        return ResultUtil.setData(menuService.getMenuTree());
    }

    //添加 or 编辑
    @PostMapping(value = "/save")
    @RequiresRoles("admin")
    public Result add(@ModelAttribute MenuDTO param) {
        return ResultUtil.setData(menuService.save(param));
    }

    //批量通过id删除
    @DeleteMapping(value = "/delById")
    @RequiresRoles("admin")
    public Result delById(@RequestParam String id) {
        menuService.deleteById(id);
        return ResultUtil.setSuccessMsg("删除数据成功");
    }

}
