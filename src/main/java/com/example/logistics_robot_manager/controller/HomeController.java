package com.example.logistics_robot_manager.controller;

import com.example.logistics_robot_manager.common.Result;
import com.example.logistics_robot_manager.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("home")
public class HomeController {
    @Autowired
    IGoodsService goodsService;

    /**
     * 获取货物类型统计图数据
     */
    @GetMapping("goodsTypeChart")
    public Result getGoodsTypeChart(){
        return Result.ok(goodsService.countByGoodsType());
    }
}
