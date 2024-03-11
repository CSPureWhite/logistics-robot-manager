package com.example.logistics_robot_manager.service.impl;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.logistics_robot_manager.entity.Shelf;
import com.example.logistics_robot_manager.mapper.ShelfMapper;
import com.example.logistics_robot_manager.service.IShelfService;
import org.springframework.stereotype.Service;

@Service
public class ShelfServiceImpl extends ServiceImpl<ShelfMapper, Shelf> implements IShelfService {
    /**
     * 根据货架ID或名称分页查找货架（key为空时，返回所有数据）
     */
    @Override
    public Page<Shelf> queryPageByKey(Integer currentPage, Integer pageSize, String key) {
        Page<Shelf> page=new Page<>(currentPage,pageSize);
        page.addOrder(OrderItem.desc("create_time")); // 按照激活时间倒序排列
        getBaseMapper().selectPageByKey(page,key);
        return page;
    }
}
