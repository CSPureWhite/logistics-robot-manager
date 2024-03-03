package com.example.logistics_robot_manager.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.logistics_robot_manager.entity.Goods;
import com.example.logistics_robot_manager.mapper.GoodsMapper;
import com.example.logistics_robot_manager.service.IGoodsService;
import org.springframework.stereotype.Service;

@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

    @Override
    public Page<Goods> queryAll(Integer currentPage, Integer pageSize) {
        return query().page(new Page<>(currentPage,pageSize));
    }

    /**
     * 根据货物ID或名称分页查找货物
     */
    @Override
    public Page<Goods> queryByKey(Integer currentPage, Integer pageSize, String key) {
        return query()
                .eq("goods_id",key)
                .or()
                .like("goods_name","%"+key+"%")
                .page(new Page<>(currentPage,pageSize));
    }
}
