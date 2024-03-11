package com.example.logistics_robot_manager.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.logistics_robot_manager.common.Result;
import com.example.logistics_robot_manager.entity.User;
import com.example.logistics_robot_manager.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("userManage")
public class UserManageController {
    @Autowired
    IUserService userService;

    @GetMapping("page")
    public Result queryUsersPage(@RequestParam(value = "key", required = false) String key,
                                 @RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
                                 @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize
    ){
        Page<User> userPage;
        userPage=userService.queryByKey(currentPage, pageSize, key);
        return Result.ok(userPage.getRecords(),userPage.getTotal());
    }
}
