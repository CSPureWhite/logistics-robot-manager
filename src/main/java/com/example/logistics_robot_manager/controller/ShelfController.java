package com.example.logistics_robot_manager.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.logistics_robot_manager.common.Result;
import com.example.logistics_robot_manager.dto.AddShelfDTO;
import com.example.logistics_robot_manager.entity.Shelf;
import com.example.logistics_robot_manager.service.IShelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("shelfManage")
public class ShelfController {
    @Autowired
    IShelfService shelfService;

    @GetMapping("page")
    public Result queryShelvesPage(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize
    ){
        Page<Shelf> shelfPage;
        shelfPage=shelfService.queryPageByKey(currentPage, pageSize, key);
        return Result.ok(shelfPage.getRecords(),shelfPage.getTotal());
    }

    @PostMapping("addShelf")
    public Result addShelf(@Validated @RequestBody AddShelfDTO addShelfDTO){
        Shelf shelf=new Shelf();
        shelf.setShelfName(addShelfDTO.getShelfName());
        shelf.setShelfType(addShelfDTO.getShelfType());
        shelfService.save(shelf);
        return Result.ok();
    }
}
