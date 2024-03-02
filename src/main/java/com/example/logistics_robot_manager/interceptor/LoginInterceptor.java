package com.example.logistics_robot_manager.interceptor;

import com.example.logistics_robot_manager.common.Constant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


public class LoginInterceptor implements HandlerInterceptor {

    StringRedisTemplate stringRedisTemplate;

    public LoginInterceptor(StringRedisTemplate stringRedisTemplate){
        this.stringRedisTemplate=stringRedisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token=request.getHeader("authorization");
        if(StringUtils.isBlank(token)){
            // token为空，拒绝放行，并将状态码设为401
            response.setStatus(401);
            return false;
        }
        Map<Object, Object> userMap=stringRedisTemplate.opsForHash().entries((Constant.LOGIN_USER_KEY+token));
        if(userMap.isEmpty()){
            // 用户不存在，拒绝放行，并将状态码设为401
            response.setStatus(401);
            return false;
        }
        return true;
    }
}
