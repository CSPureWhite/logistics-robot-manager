package com.example.logistics_robot_manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.logistics_robot_manager.entity.Shelf;
import org.apache.ibatis.annotations.Param;

public interface ShelfMapper extends BaseMapper<Shelf> {
    IPage<Shelf> selectPageByKey(IPage<Shelf> page, @Param("key") String key);
}
