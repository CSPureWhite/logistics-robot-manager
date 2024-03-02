package com.example.logistics_robot_manager.interceptor;

import com.example.logistics_robot_manager.common.Constant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 访问所有页面都会自动将token续期
 */
public class RefreshTokenInterceptor implements HandlerInterceptor {
    StringRedisTemplate stringRedisTemplate;

    public RefreshTokenInterceptor(StringRedisTemplate stringRedisTemplate){
        this.stringRedisTemplate=stringRedisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token=request.getHeader("authorization");
        if(StringUtils.isBlank(token)){
            // token不存在，放行
            return true;
        }
        String tokenKey=Constant.LOGIN_USER_KEY+token;
        Map<Object,Object> userMap=stringRedisTemplate.opsForHash().entries(tokenKey);
        if(userMap.isEmpty()){
            // 用户不存在，放行
            return true;
        }
        // 刷新token有效期
        stringRedisTemplate.expire(tokenKey, Constant.LOGIN_USER_TTL, TimeUnit.MINUTES);
        return true;
    }
}
