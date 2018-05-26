package com.fangcm.config.security;

import com.fangcm.common.utils.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class AuthenticationFailHandler extends SimpleUrlAuthenticationFailureHandler {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFailHandler.class);

    @Value("${butterfly.loginTimeLimit}")
    private Integer loginTimeLimit;

    @Value("${butterfly.loginAfterTime}")
    private Integer loginAfterTime;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {

        if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException) {
            String username = request.getParameter("username");
            recordLoginTime(username);
            String key = "loginTimeLimit:" + username;
            String value = redisTemplate.opsForValue().get(key);
            if (StringUtils.isBlank(value)) {
                value = "0";
            }
            //获取已登录错误次数
            int loginFailTime = Integer.parseInt(value);
            int restLoginTime = loginTimeLimit - loginFailTime;
            logger.info("用户" + username + "登录失败，还有" + restLoginTime + "次机会");
            ResponseUtil.out(response, ResponseUtil.resultMap(false, 500, "用户名或密码错误"));
        } else if (e instanceof DisabledException) {
            ResponseUtil.out(response, ResponseUtil.resultMap(false, 500, "账户被禁用，请联系管理员"));
        } else if (e instanceof LoginFailLimitException) {
            ResponseUtil.out(response, ResponseUtil.resultMap(false, 500, ((LoginFailLimitException) e).getMsg()));
        } else {
            ResponseUtil.out(response, ResponseUtil.resultMap(false, 500, "登录失败"));
        }
    }

    /**
     * 判断用户登陆错误次数
     */
    public boolean recordLoginTime(String username) {

        String key = "loginTimeLimit:" + username;
        String flagKey = "loginFailFlag:" + username;
        String value = redisTemplate.opsForValue().get(key);
        if (StringUtils.isBlank(value)) {
            value = "0";
        }
        //获取已登录错误次数
        int loginFailTime = Integer.parseInt(value) + 1;
        redisTemplate.opsForValue().set(key, String.valueOf(loginFailTime), loginAfterTime, TimeUnit.MINUTES);
        if (loginFailTime >= loginTimeLimit) {

            redisTemplate.opsForValue().set(flagKey, "fail", loginAfterTime, TimeUnit.MINUTES);
            return false;
        }
        return true;
    }
}
