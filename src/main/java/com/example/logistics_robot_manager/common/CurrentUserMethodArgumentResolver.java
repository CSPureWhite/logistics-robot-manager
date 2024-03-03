package com.example.logistics_robot_manager.common;

import com.example.logistics_robot_manager.annotation.CurrentUser;
import com.example.logistics_robot_manager.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 实现CurrentUser注解的参数解析器
 */
@Component
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().isAssignableFrom(UserDTO.class) // 指定使用UserDTO类封装用户信息
                &&methodParameter.hasParameterAnnotation(CurrentUser.class);      // 指定使用CurrentUser注解进行标识
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        String token = nativeWebRequest.getHeader("Authorization");
        String tokenKey=Constant.LOGIN_USER_KEY+token;
        // TODO
        UserDTO userDTO= (UserDTO) stringRedisTemplate.opsForHash().entries(tokenKey);
        return userDTO;
    }
}
