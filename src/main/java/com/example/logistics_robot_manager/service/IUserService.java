package com.example.logistics_robot_manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.logistics_robot_manager.dto.LoginFormDTO;
import com.example.logistics_robot_manager.common.Result;
import com.example.logistics_robot_manager.dto.RegisterFormDTO;
import com.example.logistics_robot_manager.entity.User;

public interface IUserService extends IService<User> {
    Result login(LoginFormDTO loginFormDTO);
    Result sendCaptcha(String captchaKey);
    Result sendValidateCode(String eamil);
    Result register(RegisterFormDTO registerFormDTO);
}
