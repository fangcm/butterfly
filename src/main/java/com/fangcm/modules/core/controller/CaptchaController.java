package com.fangcm.modules.core.controller;

import com.fangcm.common.entity.Result;
import com.fangcm.common.utils.ResultUtil;
import com.fangcm.modules.core.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping("/core/captcha")
@RestController
public class CaptchaController {

    @Autowired
    private CaptchaService captchaService;

    //初始化验证码
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public Result<Object> initCaptcha() {
        return new ResultUtil<Object>().setData(captchaService.initCaptcha());
    }

    //根据验证码ID获取图片
    @RequestMapping(value = "/draw/{captchaId}", method = RequestMethod.GET)
    public void drawCaptcha(@PathVariable("captchaId") String captchaId, HttpServletResponse response) throws IOException {
        captchaService.drawCaptcha(captchaId, response);
    }
}
