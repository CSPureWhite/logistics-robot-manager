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
     * 分页查找所有货物
     */
    @Override
    public Page<Goods> queryAll(Integer currentPage, Integer pageSize) {
        return page(new Page<>(currentPage,pageSize)).addOrder(OrderItem.desc("create_time")); // 按照生产时间倒序排列
    }

    /**
     * 根据货物ID或名称分页查找货物
     */
    @Override
    public Page<Goods> queryByKey(Integer currentPage, Integer pageSize, String key) {
        return page(new Page<>(currentPage,pageSize),
                query().eq("goods_id",key).or().like("goods_name","%"+key+"%"))
                .addOrder(OrderItem.desc("create_time")); // 按照生产时间倒序排列
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
