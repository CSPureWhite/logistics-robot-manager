package com.example.logistics_robot_manager.service.impl;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.logistics_robot_manager.dto.GoodsTypeCountDTO;
import com.example.logistics_robot_manager.entity.Goods;
import com.example.logistics_robot_manager.mapper.GoodsMapper;
import com.example.logistics_robot_manager.service.IGoodsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

    /**
     * 根据货物ID或名称分页查找货物（key为空时，返回所有数据）
     */
    @Override
    public Page<Goods> queryPageByKey(Integer currentPage, Integer pageSize, String key) {
        Page<Goods> page=new Page<>(currentPage,pageSize);
        page.addOrder(OrderItem.desc("create_time"));  // 按照生产时间倒序排列
        getBaseMapper().selectPageByKey(page,key);
        return page;
    }

    /**
     * 查询每种货物类型下的货物数量
     * @return List<GoodsTypeCountDTO>
     */
    @Override
    public List<GoodsTypeCountDTO> countByGoodsType() {
        return getBaseMapper().countByGoodsType();
    }
}
