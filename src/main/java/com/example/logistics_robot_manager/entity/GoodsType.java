package com.example.logistics_robot_manager.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class GoodsType {
    private static final long serialVersionUID = 1L;

    /**
     * 货物类型id
     */
    @TableId(value = "goods_type_id",type = IdType.AUTO)
    private Long goodsTypeId;
    /**
     * 货物类型名称
     */
    private String goodsTypeName;
}
