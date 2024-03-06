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
    @Override
    public Page<Shelf> queryAll(Integer currentPage, Integer pageSize) {
        return page(new Page<Shelf>(currentPage, pageSize).addOrder(OrderItem.desc("create_time"))); // 按激活时间倒序排列;
    }

    @Override
    public Page<Shelf> queryByKey(Integer currentPage, Integer pageSize, String key) {
        return page(new Page<Shelf>(currentPage, pageSize).addOrder(OrderItem.desc("create_time")),
                query().eq("goods_id", key).or().like("goods_name", "%" + key + "%")); // 添加id匹配和名称模糊匹配
    }
}
