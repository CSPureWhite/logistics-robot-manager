package com.example.logistics_robot_manager.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.logistics_robot_manager.common.Result;
import com.example.logistics_robot_manager.dto.AddGoodsDTO;
import com.example.logistics_robot_manager.entity.Goods;
import com.example.logistics_robot_manager.service.IGoodsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("goodsManage")
public class GoodsController {
    @Autowired
    IGoodsService goodsService;

    @GetMapping("page")
    public Result queryGoodsPage(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize
    ){
        // 分页查询
        Page<Goods> goodsPage;
        if(StringUtils.isEmpty(key)){
            // 无特定筛选条件，返回全部数据
            goodsPage=goodsService.queryAll(currentPage,pageSize);
        }
        else{
            goodsPage=goodsService.queryByKey(currentPage, pageSize, key);
        }
        return Result.ok(goodsPage.getRecords(),goodsPage.getTotal());
    }

    @PostMapping("AddGoods")
    public Result addGoods(@RequestBody AddGoodsDTO addGoodsDTO){
        Goods goods=new Goods();
        goods.setGoodsName(addGoodsDTO.getGoodsName());
        goods.setGoodsType(addGoodsDTO.getGoodsType());
        goodsService.save(goods);
        return Result.ok();
    }
}
