package com.fangcm.exception;

import com.fangcm.common.entity.Result;
import com.fangcm.common.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created by FangCM on 2018/5/25.
 */
@RestControllerAdvice
public class RestCtrlExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(RestCtrlExceptionHandler.class);

    @ExceptionHandler(ButterflyException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public Result handleXCloudException(ButterflyException e) {
        String errorMsg = "Butterfly exception";
        if (e != null) {
            errorMsg = e.getMsg();
            logger.warn(e.toString());
        }
        return ResultUtil.setErrorMsg(500, errorMsg);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.OK)
    public Result handleException(Exception e) {
        String errorMsg = "Exception";
        if (e != null) {
            errorMsg = e.getMessage();
            logger.warn(e.toString());
        }
        return ResultUtil.setErrorMsg(500, errorMsg);
    }
}