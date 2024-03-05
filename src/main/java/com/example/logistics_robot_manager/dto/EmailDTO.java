package com.example.logistics_robot_manager.dto;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class EmailDTO {
    @Email(message = "邮箱地址格式错误")
    private String email;
}
