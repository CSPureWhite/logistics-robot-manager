package com.example.logistics_robot_manager.controller;

import com.example.logistics_robot_manager.common.Constant;
import com.example.logistics_robot_manager.common.Result;
import com.example.logistics_robot_manager.dto.DAUDTO;
import com.example.logistics_robot_manager.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("home")
public class HomeController {
    @Autowired
    IGoodsService goodsService;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 获取货物类型统计图数据
     */
    @GetMapping("goodsTypeChart")
    public Result getGoodsTypeChart(){
        return Result.ok(goodsService.countByGoodsType());
    }

    /**
     * 获取用户日活量统计图数据
     */
    @GetMapping("DAUChart")
    public Result getDAUChart(){
        LocalDateTime now=LocalDateTime.now();
        List<DAUDTO> resultList=new ArrayList<>();
        // 从redis获取过去7天的用户日活量（包括今天）
        for(long i=6;i>=0;i--){
            LocalDateTime localDateTime=now.minusDays(i);
            String date=localDateTime.format(DateTimeFormatter.ISO_DATE);
            String dauKey= Constant.DAU_KEY+date;
            // 使用bitCount命令统计BitMap中1的个数
            Long dau=stringRedisTemplate.execute((RedisCallback<Long>) con->con.bitCount(dauKey.getBytes()));
            resultList.add(new DAUDTO(date,dau));
        }
        return Result.ok(resultList,(long) resultList.size());
    }
}
