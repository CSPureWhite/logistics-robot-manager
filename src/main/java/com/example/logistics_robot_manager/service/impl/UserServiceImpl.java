package com.example.logistics_robot_manager.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    MailUtil mailUtil;

    @Override
    public Result login(LoginFormDTO loginFormDTO) {
        // 校验验证码是否正确（忽略大小写）
        String codeKey=Constant.LOGIN_CODE_KEY+loginFormDTO.getCaptchaKey();
        String captchaCode=stringRedisTemplate.opsForValue().get(codeKey);
        if(StringUtils.isEmpty(captchaCode)||!captchaCode.equalsIgnoreCase(loginFormDTO.getCaptchaCode())){
            return Result.fail(Constant.CODE_BAD_REQUEST,"验证码错误");
        }
        // 根据邮箱查找用户
        User user=query().eq("email",loginFormDTO.getEmail()).one();
        if(user==null){
            return Result.fail(Constant.CODE_BAD_REQUEST,"邮箱错误");
        }
        // 判断密码是否正确
        if(!PassWordUtil.check(loginFormDTO.getPassword(),user.getPassword())){
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
        // 更新用户的最近一次登录时间
        user.setLoginTime(LocalDateTime.now());
        updateById(user);
        return Result.ok(token);
    }

    /**
     * 生成图形验证码并以Base64编码返回给前端
     */
    public Result sendCaptcha(String captchaKey){
        // 生成线段干扰图形验证码
        LineCaptcha captcha=CaptchaUtil.createLineCaptcha(75,50,4,20);
        int r=RandomUtils.nextInt(200,250);
        int g=RandomUtils.nextInt(200,250);
        int b=RandomUtils.nextInt(200,250);
        captcha.setBackground(new Color(r,g,b));
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
     * 向注册邮箱发送验证码（请求最小时间间隔为60秒，验证码有效期为5分钟）
     */
    @Override
    public Result sendValidateCode(String email) {
        String key=Constant.REGISTER_CODE_KEY +email;
        String cacheValue=stringRedisTemplate.opsForValue().get(key);
        // 如果redis键值对不为空，先判断是否60秒内重复请求发送验证码
        if(!StringUtils.isBlank(cacheValue)){
            long ttl=Long.parseLong(cacheValue.split("_")[1]);
            // 当前时间戳-redis中验证码时间戳，如果小于60秒，则不允许再次发送验证码
            long timeInterval=System.currentTimeMillis()-ttl;
            if(timeInterval<Constant.REGISTER_MAIL_TTL){
                String msg=String.format("发送邮件验证码请求过于频繁，剩余时间间隔：%d秒",60-(int)Math.floor(timeInterval/1000.0));
                log.warn(msg);
                return Result.fail(Constant.CODE_BAD_REQUEST,msg);
            }
        }
        // key不存在redis中，则生成验证码存入redis，并发送邮件
        String validateCode= UUID.randomUUID().toString().substring(0, 6);
        // 将验证码与当前时间戳进行拼接
        String value=validateCode+"_"+System.currentTimeMillis();
        // 将拼接后带有时间戳的验证码存入Redis中，验证码有效期设为5分钟
        stringRedisTemplate.opsForValue().set(key,value,Constant.REGISTER_CODE_TTL,TimeUnit.SECONDS);
        // 发送邮件
        mailUtil.sendValidateCodeMail(email,validateCode);
        return Result.ok();
    }

    @Override
    public Result register(RegisterFormDTO registerFormDTO) {
        // 查找邮箱是否已经被注册
        if(query().eq("email",registerFormDTO.getEmail()).one()!=null){
            return Result.fail(Constant.CODE_BAD_REQUEST,"邮箱已被注册");
        }
        // 校验验证码
        String cacheValue=stringRedisTemplate.opsForValue().get(Constant.REGISTER_CODE_KEY +registerFormDTO.getEmail());
        if(StringUtils.isBlank(cacheValue)){
            // redis键值对为空
            return Result.fail(Constant.CODE_BAD_REQUEST,"验证码失效或邮箱地址错误");
        }
        String validateCode=cacheValue.split("_")[0];
        if(!registerFormDTO.getValidateCode().equals(validateCode)){
            // redis缓存验证码与用户输入验证码不一致
            return Result.fail(Constant.CODE_BAD_REQUEST,"验证码错误");
        }
        User user=new User();
        user.setUsername(registerFormDTO.getUsername());
        user.setEmail(registerFormDTO.getEmail());
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

    /**
     * 分页查找所有用户
     */
    @Override
    public Page<User> queryAll(Integer currentPage, Integer pageSize) {
        return page(new Page<User>(currentPage,pageSize).addOrder(OrderItem.desc("login_time"))); // 按照登录时间倒序排列
    }

    /**
     * 根据用户ID或名称分页查找用户
     */
    @Override
    public Page<User> queryByKey(Integer currentPage, Integer pageSize, String key) {
        LambdaQueryWrapper<User> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserId, key).or().like(User::getUsername, "%" + key + "%"); // 添加id匹配和名称模糊匹配
        return page(new Page<User>(currentPage,pageSize).addOrder(OrderItem.desc("login_time")), wrapper);
    }
}
