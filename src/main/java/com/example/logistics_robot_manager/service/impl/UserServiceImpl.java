package com.example.logistics_robot_manager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.logistics_robot_manager.common.Constant;
import com.example.logistics_robot_manager.dto.LoginFormDTO;
import com.example.logistics_robot_manager.common.Result;
import com.example.logistics_robot_manager.dto.RegisterFormDTO;
import com.example.logistics_robot_manager.entity.User;
import com.example.logistics_robot_manager.mapper.UserMapper;
import com.example.logistics_robot_manager.service.IUserService;
import com.example.logistics_robot_manager.utils.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    public Result login(LoginFormDTO loginFormDTO) {
        // 根据邮箱查找用户
        User user=query().eq("email",loginFormDTO.getEmail()).one();
        if(user==null){
            return Result.fail("400","邮箱或密码错误");
        }
        if(!user.getPassword().equals(loginFormDTO.getPassword())){
            return Result.fail("400","密码错误");
        }
        // 查询用户账号是否被禁用
        if(!user.getIsActive()){
            return Result.fail("400","账号已被禁用");
        }
        // 随机生成token
        String token= UUID.randomUUID().toString();
        String tokenKey= Constant.LOGIN_USER_KEY+token;
        // 将token与用户id存入redis中
        stringRedisTemplate.opsForValue().set(tokenKey,user.getUserId().toString(),Constant.LOGIN_USER_TTL, TimeUnit.MINUTES);
        return Result.ok(token);
    }

    /**
     * 向注册邮箱发送验证码
     * @param email
     * @return
     */
    @Override
    public Result sendValidateCode(String email) {
        String validateCode= UUID.randomUUID().toString().substring(0, 6);
        String key=Constant.LOGIN_CODE_KEY+email;
        // 将验证码存入Redis中
        stringRedisTemplate.opsForValue().set(key,validateCode,Constant.LOGIN_CODE_TTL,TimeUnit.SECONDS);
        // 发送邮件
        MailUtil.sendValidateCodeMail(email,validateCode);
        return Result.ok();
    }

    @Override
    public Result register(RegisterFormDTO registerFormDTO) {
        // 查找邮箱是否已经被注册
        if(query().eq("email",registerFormDTO.getEmail()).one()!=null){
            return Result.fail("400","邮箱已被注册");
        }
        // 校验验证码
        String validateCode=stringRedisTemplate.opsForValue().get(Constant.LOGIN_CODE_KEY+registerFormDTO.getEmail());
        if(!registerFormDTO.getValidateCode().equals(validateCode)){
            return Result.fail("400","验证码错误");
        }
        User user=new User();
        user.setUserName(registerFormDTO.getUsername());
        user.setPassword(registerFormDTO.getPassword());
        user.setUserType(registerFormDTO.getUserType());
        user.setIsActive(true);
        save(user);
        return Result.ok();
    }
}
