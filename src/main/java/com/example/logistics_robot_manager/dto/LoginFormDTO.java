package com.example.logistics_robot_manager.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class LoginFormDTO {

    @NotNull(message = "邮箱地址不能为空值")
    @Email(message = "邮箱地址格式错误")
    private String email;

    @NotBlank(message = "密码不能为空值")
    private String password;

    @NotBlank(message = "验证码key不能为空值")
    private String captchaKey;
    
    @NotBlank(message = "验证码不能为空值")
    private String captchaCode;
}
