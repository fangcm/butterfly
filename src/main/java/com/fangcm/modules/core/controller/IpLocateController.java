package com.fangcm.modules.core.controller;

import com.fangcm.common.entity.Result;
import com.fangcm.common.utils.IpInfoUtil;
import com.fangcm.common.utils.ResultUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by FangCM on 2018/5/24.
 */
//IP接口
@RestController
@RequestMapping("/common/ip")
public class IpLocateController {

    //IP及天气相关信息
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Result<Object> upload(HttpServletRequest request) {

        String result = IpInfoUtil.getIpInfo(IpInfoUtil.getIpAddr(request));
        return new ResultUtil<Object>().setData(result);
    }
}
