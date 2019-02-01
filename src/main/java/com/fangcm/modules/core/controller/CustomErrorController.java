package com.fangcm.modules.core.controller;


import com.fangcm.common.rest.ErrorCode;
import com.fangcm.common.rest.Result;
import com.fangcm.common.rest.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
public class CustomErrorController implements ErrorController {

    private static final String PATH = "/error";


    @Resource
    private ErrorAttributes errorAttributes;

    @RequestMapping(value = PATH, produces = {MediaType.APPLICATION_JSON_VALUE})
    Result error(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> errorAttributes = this.errorAttributes.getErrorAttributes(new ServletWebRequest(request), false);
        Integer status = (Integer) errorAttributes.get("status");
        String path = (String) errorAttributes.get("path");
        String messageFound = (String) errorAttributes.get("message");
        String message = "";
        if (!StringUtils.isEmpty(path)) {
            message = String.format("%s [%s]", messageFound, path);
        }
        return ResultUtil.setMessage(status, message);
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }

    @RequestMapping(path = "/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result unauthorized() {
        return ResultUtil.setErrorMsg(ErrorCode.UNAUTHORIZED);
    }
}