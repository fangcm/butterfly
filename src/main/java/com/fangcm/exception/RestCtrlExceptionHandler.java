package com.fangcm.exception;

import com.fangcm.common.rest.Result;
import com.fangcm.common.rest.ResultUtil;
import org.apache.shiro.ShiroException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by FangCM on 2018/5/25.
 */
@RestControllerAdvice
public class RestCtrlExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(RestCtrlExceptionHandler.class);

    // 捕捉shiro的异常
    @ExceptionHandler(ShiroException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result handle401(ShiroException e) {
        return ResultUtil.setErrorMsg(401, "Unauthorized");
    }

    // 捕捉UnauthorizedException
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result handle401() {
        return ResultUtil.setErrorMsg(401, "Unauthorized");
    }

    @ExceptionHandler(ButterflyException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public Result handleButterflyException(ButterflyException e) {
        String errorMsg = "Butterfly exception";
        if (e != null) {
            errorMsg = e.getMsg();
            logger.warn(e.toString());
        }
        return ResultUtil.setErrorMsg(500, errorMsg);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Result handleException(HttpServletRequest request, Throwable e) {
        String errorMsg = "Exception";
        if (e != null) {
            errorMsg = e.getMessage();
            logger.warn(e.toString());
        }
        return ResultUtil.setErrorMsg(getStatus(request).value(), errorMsg);
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}