package com.example.logistics_robot_manager.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.logistics_robot_manager.entity.Goods;

public interface IGoodsService extends IService<Goods> {
    Page<Goods> queryAll(Integer currentPage, Integer pageSize);

    Page<Goods> queryByKey(Integer currentPage, Integer pageSize, String key);
}
