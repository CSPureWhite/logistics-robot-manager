package com.example.logistics_robot_manager.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class RegisterFormDTO {

    @NotBlank(message = "用户名不能为空值")
    private String username;

    @NotBlank(message = "密码不能为空值")
    @Pattern(regexp = "^[a-zA-Z0-9]{6,20}$",message = "密码不符合规范，应为一个由数字、英文字母组成的6-20位字符串")
    private String password;

    @NotNull(message = "邮箱地址不能为空值")
    @Email(message = "邮箱地址格式错误")
    private String email;

    @NotNull(message = "验证码不能为空值")
    private String validateCode;
}
