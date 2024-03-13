package com.example.logistics_robot_manager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.logistics_robot_manager.entity.GoodsType;
import com.example.logistics_robot_manager.mapper.GoodsTypeMapper;
import com.example.logistics_robot_manager.service.IGoodsTypeService;
import org.springframework.stereotype.Service;

@Service
public class GoodsTypeServiceImpl extends ServiceImpl<GoodsTypeMapper, GoodsType> implements IGoodsTypeService {

}
