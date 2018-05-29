package com.fangcm.modules.core.controller;


import com.fangcm.common.entity.Result;
import com.fangcm.common.utils.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
public class CustomErrorController implements ErrorController {

    private static final String PATH = "/error";


    @Autowired
    private ErrorAttributes errorAttributes;

    @RequestMapping(value = PATH, produces = {MediaType.APPLICATION_JSON_VALUE})
    Result<Object> error(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> errorAttributes = this.errorAttributes.getErrorAttributes(new ServletWebRequest(request), false);
        Integer status = (Integer) errorAttributes.get("status");
        String path = (String) errorAttributes.get("path");
        String messageFound = (String) errorAttributes.get("message");
        String message = "";
        if (!StringUtils.isEmpty(path)) {
            message = String.format("Requested path [%s] with result: %s", path, messageFound);
        }
        return new ResultUtil<Object>().setErrorMsg(status, message);
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }


}