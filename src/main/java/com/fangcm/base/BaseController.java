package com.fangcm.base;

import com.fangcm.common.entity.PageVo;
import com.fangcm.common.entity.Result;
import com.fangcm.common.utils.PageUtil;
import com.fangcm.common.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

/**
 * Created by FangCM on 2018/5/23.
 */
public abstract class BaseController<E, ID extends Serializable> {

    /**
     * 获取service
     */
    @Autowired
    public abstract BaseService<E, ID> getService();

    //通过id获取
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Result getOne(@PathVariable ID id) {
        return ResultUtil.setData(getService().getOne(id));
    }

    //获取全部数据
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    @ResponseBody
    public Result findAll() {
        return ResultUtil.setData(getService().findAll());
    }

    //分页获取
    @RequestMapping(value = "/findAllByPage", method = RequestMethod.GET)
    @ResponseBody
    public Result findAll(@ModelAttribute PageVo page) {
        return ResultUtil.setData(getService().findAll(PageUtil.initPage(page)));
    }

    //保存 or 更新
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(@ModelAttribute E entity) {
        return ResultUtil.setData(getService().save(entity));
    }

    //批量通过ids删除
    @RequestMapping(value = "/delByIds", method = RequestMethod.DELETE)
    @ResponseBody
    public Result delByIds(@RequestParam ID[] ids) {
        getService().delByIds(ids);
        return ResultUtil.setSuccessMsg("批量通过id删除数据成功");
    }
}