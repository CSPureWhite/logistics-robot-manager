package com.example.logistics_robot_manager.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdatePasswordDTO {
    @NotBlank(message = "密码不能为空值")
    private String oldPassword;

    @NotBlank(message = "密码不能为空值")
    private String newPassword;
}
