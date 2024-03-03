package com.example.logistics_robot_manager.controller;

import com.example.logistics_robot_manager.dto.LoginFormDTO;
import com.example.logistics_robot_manager.common.Result;
import com.example.logistics_robot_manager.dto.RegisterFormDTO;
import com.example.logistics_robot_manager.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@RestController
@Validated
public class LoginController {
    @Autowired
    private IUserService userService;

    @PostMapping("login")
    public Result login(@Validated @RequestBody LoginFormDTO loginFormDTO){
        return userService.login(loginFormDTO);
    }

    @GetMapping("captcha/{captchaKey}")
    public Result sendCaptcha(@PathVariable @NotBlank(message = "验证码key不能为空值") String captchaKey){
        return userService.sendCaptcha(captchaKey);
    }

    @PostMapping("validateCode")
    public Result sendValidateCode(@RequestBody @Email(message = "邮箱地址格式错误") String email){
        return userService.sendValidateCode(email);
    }

    @PostMapping("register")
    public Result register(@Validated @RequestBody RegisterFormDTO registerFormDTO){
        return userService.register(registerFormDTO);
    }
}
