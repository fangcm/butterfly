package com.fangcm.base;

import com.fangcm.common.entity.PageVo;
import com.fangcm.common.entity.Result;
import com.fangcm.common.utils.PageUtil;
import com.fangcm.common.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

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
    public Result<E> getOne(@PathVariable ID id) {
        return new ResultUtil<E>().setData(getService().getOne(id));
    }

    //获取全部数据
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    @ResponseBody
    public Result<List<E>> findAll() {
        return new ResultUtil<List<E>>().setData(getService().findAll());
    }

    //分页获取
    @RequestMapping(value = "/findAllByPage", method = RequestMethod.GET)
    @ResponseBody
    public Result<Page<E>> findAll(@ModelAttribute PageVo page) {
        return new ResultUtil<Page<E>>().setData(getService().findAll(PageUtil.initPage(page)));
    }

    //保存 or 更新
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Result<E> save(@ModelAttribute E entity) {
        return new ResultUtil<E>().setData(getService().save(entity));
    }

    //批量通过ids删除
    @RequestMapping(value = "/delByIds", method = RequestMethod.DELETE)
    @ResponseBody
    public Result<Object> delByIds(@RequestParam ID[] ids) {
        getService().delByIds(ids);
        return new ResultUtil<Object>().setSuccessMsg("批量通过id删除数据成功");
    }
}