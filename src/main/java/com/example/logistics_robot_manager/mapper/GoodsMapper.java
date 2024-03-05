package com.example.logistics_robot_manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.logistics_robot_manager.dto.GoodsTypeCountDTO;
import com.example.logistics_robot_manager.entity.Goods;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface GoodsMapper extends BaseMapper<Goods> {
    @Select("select goods_type,count(goods_id) as value from goods group by goods_type")
    List<GoodsTypeCountDTO> countByGoodsType();
}
