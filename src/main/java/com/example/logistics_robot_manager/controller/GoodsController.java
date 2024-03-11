package com.example.logistics_robot_manager.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.logistics_robot_manager.common.Result;
import com.example.logistics_robot_manager.dto.AddGoodsDTO;
import com.example.logistics_robot_manager.entity.Goods;
import com.example.logistics_robot_manager.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
        goodsPage=goodsService.queryPageByKey(currentPage, pageSize, key);
        return Result.ok(goodsPage.getRecords(),goodsPage.getTotal());
    }

    @PostMapping("AddGoods")
    public Result addGoods(@Validated @RequestBody AddGoodsDTO addGoodsDTO){
        Goods goods=new Goods();
        goods.setGoodsName(addGoodsDTO.getGoodsName());
        goods.setGoodsTypeId(addGoodsDTO.getGoodsType());
        goodsService.save(goods);
        return Result.ok();
    }
}
