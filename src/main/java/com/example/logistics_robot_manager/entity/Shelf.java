package com.example.logistics_robot_manager.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class Shelf implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 货架id
     */
    @TableId(value = "shelf_id",type = IdType.AUTO)
    private Long shelfId;

    /**
     * 货架名称
     */
    private String shelfName;

    /**
     * 货架状态，0为无货物，1为有货物，2为已满
     */
    private Integer shelfStatus;

    /**
     * 货架类型
     */
    private Integer shelfType;

    /**
     * 货架存放的货物数量
     */
    private Integer goodsAmount;

    /**
     * 货架激活时间
     */
    private LocalDateTime createTime;
}
