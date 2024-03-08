package com.example.logistics_robot_manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
     * 分页查找所有货架
     */
    @Override
    public Page<Shelf> queryAll(Integer currentPage, Integer pageSize) {
        return page(new Page<Shelf>(currentPage, pageSize).addOrder(OrderItem.desc("create_time"))); // 按激活时间倒序排列;
    }

    /**
     * 根据货架ID或名称分页查找货架
     */
    @Override
    public Page<Shelf> queryByKey(Integer currentPage, Integer pageSize, String key) {
        LambdaQueryWrapper<Shelf> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(Shelf::getShelfId, key).or().like(Shelf::getShelfName, "%" + key + "%"); // 添加id匹配和名称模糊匹配
        return page(new Page<Shelf>(currentPage, pageSize).addOrder(OrderItem.desc("create_time")),wrapper);
    }
}
