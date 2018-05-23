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
     *
     * @return
     */
    @Autowired
    public abstract BaseService<E, ID> getService();

    //通过id获取
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Result<E> get(@PathVariable ID id) {

        E entity = getService().get(id);
        return new ResultUtil<E>().setData(entity);
    }

    //获取全部数据
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ResponseBody
    public Result<List<E>> getAll() {

        List<E> list = getService().getAll();
        return new ResultUtil<List<E>>().setData(list);
    }

    //分页获取
    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ResponseBody
    public Result<Page<E>> getByPage(@ModelAttribute PageVo page) {

        Page<E> list = getService().findAll(PageUtil.initPage(page));
        return new ResultUtil<Page<E>>().setData(list);
    }

    //保存数据
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Result<E> save(@ModelAttribute E entity) {

        E e = getService().save(entity);
        return new ResultUtil<E>().setData(e);
    }

    //更新数据
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Result<E> update(@ModelAttribute E entity) {

        E e = getService().update(entity);
        return new ResultUtil<E>().setData(e);
    }

    //删除数据
    @RequestMapping(value = "/del", method = RequestMethod.DELETE)
    @ResponseBody
    public Result<Object> delAll(@RequestBody List<E> entities) {

        getService().delete(entities);
        return new ResultUtil<Object>().setSuccessMsg("批量删除数据成功");
    }

    //批量通过ids删除
    @RequestMapping(value = "/delByIds", method = RequestMethod.DELETE)
    @ResponseBody
    public Result<Object> delAllByIds(@RequestParam ID[] ids) {

        for (ID id : ids) {
            getService().delete(id);
        }
        return new ResultUtil<Object>().setSuccessMsg("批量通过id删除数据成功");
    }
}