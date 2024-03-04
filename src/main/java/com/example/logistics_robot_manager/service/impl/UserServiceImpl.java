package com.example.logistics_robot_manager.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.logistics_robot_manager.common.Constant;
import com.example.logistics_robot_manager.dto.*;
import com.example.logistics_robot_manager.common.Result;
import com.example.logistics_robot_manager.entity.User;
import com.example.logistics_robot_manager.mapper.UserMapper;
import com.example.logistics_robot_manager.service.IUserService;
import com.example.logistics_robot_manager.utils.MailUtil;
import com.example.logistics_robot_manager.utils.PassWordUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    public Result login(LoginFormDTO loginFormDTO) {
        // 验证验证码是否正确
        String codeKey=Constant.LOGIN_CODE_TTL+loginFormDTO.getCaptchaKey();
        String captchaCode=stringRedisTemplate.opsForValue().get(codeKey);
        if(StringUtils.isEmpty(captchaCode)||captchaCode.equals(loginFormDTO.getCaptchaCode())){
            return Result.fail(Constant.CODE_BAD_REQUEST,"验证码错误");
        }
        // 根据邮箱查找用户
        User user=query().eq("email",loginFormDTO.getEmail()).one();
        if(user==null){
            return Result.fail(Constant.CODE_BAD_REQUEST,"邮箱错误");
        }
        // 判断密码是否正确
        if(!PassWordUtil.check(user.getPassword(),loginFormDTO.getPassword())){
            return Result.fail(Constant.CODE_BAD_REQUEST,"密码错误");
        }
        // 查询用户账号是否被禁用
        if(!user.getIsActive()){
            return Result.fail(Constant.CODE_BAD_REQUEST,"账号已被禁用");
        }
        // 随机生成token
        String token= UUID.randomUUID().toString();
        String tokenKey= Constant.LOGIN_USER_KEY+token;
        // 将token与用户信息存入redis中
        UserDTO userDTO=new UserDTO();
        try {
            BeanUtils.copyProperties(userDTO, user);
            Map<String,String> userMap=BeanUtils.describe(userDTO);
            stringRedisTemplate.opsForHash().putAll(tokenKey,userMap);
            stringRedisTemplate.expire(tokenKey,Constant.LOGIN_USER_TTL, TimeUnit.MINUTES);
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail(Constant.CODE_BAD_REQUEST,"参数异常");
        }
        return Result.ok(token);
    }

    /**
     * 生成图形验证码并以Base64编码返回给前端
     */
    public Result sendCaptcha(String captchaKey){
        // 生成线段干扰图形验证码
        LineCaptcha captcha=CaptchaUtil.createLineCaptcha(80,32,4,5);
        // 将验证码存入redis，有效期为60s
        String code=captcha.getCode();
        String codeKey=Constant.LOGIN_CODE_KEY+captchaKey;
        stringRedisTemplate.opsForValue().set(codeKey,code,Constant.LOGIN_CODE_TTL,TimeUnit.SECONDS);
        // 将图片转换为Base64编码
        String imgBase64=captcha.getImageBase64Data();
        Map<String,String> resultMap=new HashMap<>();
        resultMap.put("img",imgBase64);
        return Result.ok(resultMap);
    }

    /**
     * 向注册邮箱发送验证码
     */
    @Override
    public Result sendValidateCode(String email) {
        String validateCode= UUID.randomUUID().toString().substring(0, 6);
        String key=Constant.REGISTER_CODE_KEY +email;
        // 将验证码存入Redis中
        stringRedisTemplate.opsForValue().set(key,validateCode,Constant.REGISTER_CODE_TTL,TimeUnit.SECONDS);
        // 发送邮件
        MailUtil.sendValidateCodeMail(email,validateCode);
        return Result.ok();
    }

    @Override
    public Result register(RegisterFormDTO registerFormDTO) {
        // 查找邮箱是否已经被注册
        if(query().eq("email",registerFormDTO.getEmail()).one()!=null){
            return Result.fail(Constant.CODE_BAD_REQUEST,"邮箱已被注册");
        }
        // 校验验证码
        String validateCode=stringRedisTemplate.opsForValue().get(Constant.REGISTER_CODE_KEY +registerFormDTO.getEmail());
        if(!registerFormDTO.getValidateCode().equals(validateCode)){
            return Result.fail(Constant.CODE_BAD_REQUEST,"验证码错误");
        }
        User user=new User();
        user.setUsername(registerFormDTO.getUsername());
        user.setPassword(PassWordUtil.encrypt(registerFormDTO.getPassword()));
        user.setUserType(registerFormDTO.getUserType());
        user.setIsActive(true);
        save(user);
        return Result.ok();
    }

    @Override
    public Result getUserInfo(Long userId) {
        User user=getById(userId);
        UserInfoDTO userInfoDTO=new UserInfoDTO();
        try {
            BeanUtils.copyProperties(userInfoDTO,user);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(Constant.CODE_BAD_REQUEST,"运行异常");
        }
        return Result.ok(userInfoDTO);
    }

    @Override
    public Result updatePassword(Long userId, UpdatePasswordDTO updatePasswordDTO) {
        // 检查原密码是否正确
        User user=getById(userId);
        if(!PassWordUtil.check(user.getPassword(),updatePasswordDTO.getOldPassword())){
            return Result.fail(Constant.CODE_BAD_REQUEST,"密码错误");
        }
        // 更新密码
        user.setPassword(PassWordUtil.encrypt(updatePasswordDTO.getNewPassword()));
        updateById(user);
        return Result.ok();
    }
}
