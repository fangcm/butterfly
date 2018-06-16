package com.fangcm.modules.core.controller;

import com.fangcm.common.rest.Result;
import com.fangcm.common.rest.ResultUtil;
import com.fangcm.modules.core.service.RoleService;
import com.fangcm.modules.core.vo.RoleDTO;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * Created by FangCM on 2018/5/24.
 */

//角色管理接口
@RestController
@RequestMapping("/core/role")
@RequiresRoles("admin")
public class RoleController {

    @Resource
    private RoleService roleService;


    //保存 or 更新
    @PostMapping(value = "/save")
    @ResponseBody
    public Result save(@ModelAttribute RoleDTO dto) {
        return ResultUtil.setData(roleService.save(dto));
    }

    @DeleteMapping(value = "/delById")
    @ResponseBody
    public Result deleteById(@RequestParam String id) {
        roleService.deleteById(id);
        return ResultUtil.setSuccessMsg("批量通过id删除数据成功");
    }

    //分页获取
    @GetMapping(value = "/findByPage")
    @ResponseBody
    public Result findByPage(@PageableDefault Pageable pageable) {
        return ResultUtil.setData(roleService.findByPage(pageable));
    }

}
