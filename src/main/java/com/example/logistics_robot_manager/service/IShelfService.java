package com.example.logistics_robot_manager.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.logistics_robot_manager.entity.Shelf;

public interface IShelfService extends IService<Shelf> {
    Page<Shelf> queryPageByKey(Integer currentPage, Integer pageSize, String key);
}
