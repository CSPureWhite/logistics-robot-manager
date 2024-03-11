package com.example.logistics_robot_manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.logistics_robot_manager.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper extends BaseMapper<User> {
    IPage<User> selectPageByKey(IPage<User> page, @Param("key") String key);
}
