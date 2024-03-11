package com.example.logistics_robot_manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.logistics_robot_manager.dto.GoodsTypeCountDTO;
import com.example.logistics_robot_manager.entity.Goods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsMapper extends BaseMapper<Goods> {
    IPage<Goods> selectPageByKey(IPage<Goods> page, @Param("key") String key);

    List<GoodsTypeCountDTO> countByGoodsType();
}

