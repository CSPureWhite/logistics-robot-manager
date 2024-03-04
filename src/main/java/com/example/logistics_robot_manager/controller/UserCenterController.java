package com.example.logistics_robot_manager.controller;

import com.example.logistics_robot_manager.annotation.CurrentUser;
import com.example.logistics_robot_manager.common.Result;
import com.example.logistics_robot_manager.dto.UpdatePasswordDTO;
import com.example.logistics_robot_manager.dto.UserDTO;
import com.example.logistics_robot_manager.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("userCenter")
public class UserCenterController {
    @Autowired
    IUserService userService;

    @GetMapping("info")
    public Result showUserInfo(@CurrentUser UserDTO userDTO){
        return userService.getUserInfo(userDTO.getUserId());
    }

    @PostMapping("updatePassword")
    public Result updatePassword(@CurrentUser UserDTO userDTO,
                                 @Validated @RequestBody UpdatePasswordDTO updatePasswordDTO){
        return userService.updatePassword(userDTO.getUserId(),updatePasswordDTO);
    }
}
