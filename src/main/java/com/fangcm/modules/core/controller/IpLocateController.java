package com.fangcm.modules.core.controller;

import com.fangcm.common.entity.Result;
import com.fangcm.common.utils.IpInfoUtil;
import com.fangcm.common.utils.ResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


/**
 * IP接口
 * Created by FangCM on 2018/5/24.
 */

@RestController
@RequestMapping("/core/ip")
public class IpLocateController {

    //IP及天气相关信息
    @GetMapping("/info")
    public Result<Object> upload(HttpServletRequest request) {
        return new ResultUtil<Object>().setData(IpInfoUtil.getIpAddr(request));
    }
}
