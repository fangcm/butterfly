package com.fangcm.config.security;

import com.fangcm.exception.LoginFailLimitException;
import com.fangcm.modules.core.entity.User;
import com.fangcm.modules.core.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Value("${butterfly.loginAfterTime}")
    private Integer loginAfterTime;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String flagKey = "loginFailFlag:" + username;
        String value = redisTemplate.opsForValue().get(flagKey);
        if (StringUtils.isNotBlank(value)) {
            //超过限制次数
            throw new LoginFailLimitException("登录错误次数超过限制，请" + loginAfterTime + "分钟后再试");
        }
        User user = userService.findByMobile(username);
        return new SecurityUserDetails(user);
    }
}
