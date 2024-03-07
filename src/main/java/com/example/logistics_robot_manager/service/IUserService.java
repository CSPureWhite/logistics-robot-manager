package com.example.logistics_robot_manager.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.logistics_robot_manager.dto.LoginFormDTO;
import com.example.logistics_robot_manager.common.Result;
import com.example.logistics_robot_manager.dto.RegisterFormDTO;
import com.example.logistics_robot_manager.dto.UpdatePasswordDTO;
import com.example.logistics_robot_manager.entity.User;

public interface IUserService extends IService<User> {
    Result login(LoginFormDTO loginFormDTO);

    Result sendCaptcha(String captchaKey);

    Result sendValidateCode(String email);

    Result register(RegisterFormDTO registerFormDTO);

    Result getUserInfo(Long userId);

    Result updatePassword(Long userId, UpdatePasswordDTO updatePasswordDTO);

    Page<User> queryAll(Integer currentPage, Integer pageSize);

    Page<User> queryByKey(Integer currentPage, Integer pageSize, String key);
}
