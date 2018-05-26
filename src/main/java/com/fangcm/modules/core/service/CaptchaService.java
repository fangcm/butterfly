package com.fangcm.modules.core.service;

import com.fangcm.common.entity.Captcha;
import com.fangcm.common.utils.CreateVerifyCode;
import com.fangcm.exception.ButterflyException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class CaptchaService implements Serializable {
    @Autowired
    private StringRedisTemplate redisTemplate;

    public Captcha initCaptcha() {

        String captchaId = UUID.randomUUID().toString().replace("-", "");
        String code = new CreateVerifyCode().randomStr(4);
        Captcha captcha = new Captcha();
        captcha.setCaptchaId(captchaId);
        //缓存验证码
        redisTemplate.opsForValue().set(captchaId, code, 3L, TimeUnit.MINUTES);
        return captcha;
    }

    public void drawCaptcha(String captchaId, HttpServletResponse response) throws IOException {
        //得到验证码 生成指定验证码
        String code = redisTemplate.opsForValue().get(captchaId);
        CreateVerifyCode vCode = new CreateVerifyCode(116, 36, 4, 10, code);
        vCode.write(response.getOutputStream());
    }

    public void validateCaptcha(String verify, String captchaId) {

        if (StringUtils.isBlank(verify)) {
            throw new ButterflyException("必须填写验证码");
        }
        //验证码
        String code = redisTemplate.opsForValue().get(captchaId);
        if (StringUtils.isBlank(code)) {
            throw new ButterflyException("验证码已过期，请重新获取");
        }

        if (!verify.toLowerCase().equals(code.toLowerCase())) {
            throw new ButterflyException("验证码输入错误");
        }

    }

}
