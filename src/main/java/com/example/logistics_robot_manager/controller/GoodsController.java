package com.example.logistics_robot_manager.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.logistics_robot_manager.common.Constant;
import com.example.logistics_robot_manager.common.Result;
import com.example.logistics_robot_manager.dto.AddGoodsDTO;
import com.example.logistics_robot_manager.entity.Goods;
import com.example.logistics_robot_manager.entity.GoodsType;
import com.example.logistics_robot_manager.service.IGoodsService;
import com.example.logistics_robot_manager.service.IGoodsTypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("goodsManage")
public class GoodsController {
    @Autowired
    IGoodsService goodsService;
    @Autowired
    IGoodsTypeService goodsTypeService;

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

    @PostMapping("addGoods")
    public Result addGoods(@Validated @RequestBody AddGoodsDTO addGoodsDTO){
        Goods goods=new Goods();
        try {
            BeanUtils.copyProperties(addGoodsDTO,goods);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.fail(Constant.CODE_BAD_REQUEST,"添加货物参数异常，添加货物失败！");
        }
        goodsService.save(goods);
        return Result.ok();
    }

    /**
     * 获取货物类型列表
     */
    @GetMapping("/addGoods/getGoodsType")
    public Result getGoodsTypeList(){
        List<GoodsType> goodsTypeList=goodsTypeService.list();
        return Result.ok(goodsTypeList);
    }
}
