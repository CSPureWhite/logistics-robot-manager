package com.example.logistics_robot_manager.controller;

import com.example.logistics_robot_manager.dto.LoginFormDTO;
import com.example.logistics_robot_manager.common.Result;
import com.example.logistics_robot_manager.dto.RegisterFormDTO;
import com.example.logistics_robot_manager.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    @Autowired
    private IUserService userService;

    @PostMapping("login")
    public Result login(@RequestBody LoginFormDTO loginFormDTO){
        return userService.login(loginFormDTO);
    }

    @PostMapping("validateCode")
    public Result sendValidateCode(@RequestParam("email") String email){
        return userService.sendValidateCode(email);
    }

    @PostMapping("register")
    public Result register(@RequestBody RegisterFormDTO registerFormDTO){
        return userService.register(registerFormDTO);
    }
}
