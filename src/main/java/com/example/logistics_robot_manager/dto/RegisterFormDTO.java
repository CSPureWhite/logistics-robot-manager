package com.example.logistics_robot_manager.dto;

import lombok.Data;

@Data
public class RegisterFormDTO {
    private String username;
    private String password;
    private String email;
    private String validateCode;
    private Integer userType;
}
