package com.example.logistics_robot_manager.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class RegisterFormDTO {

    @NotBlank(message = "用户名不能为空值")
    private String username;

    @NotBlank(message = "密码不能为空值")
    private String password;

    @NotNull(message = "邮箱地址不能为空值")
    @Email(message = "邮箱地址格式错误")
    private String email;

    @NotNull(message = "验证码不能为空值")
    private String validateCode;

    private Integer userType;
}
