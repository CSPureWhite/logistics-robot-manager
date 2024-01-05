package com.example.logistics_robot_manager.dto;

import lombok.Data;

@Data
public class LoginFormDTO {
    private String email;
    private String password;
    private String imgValidateCode;
}
