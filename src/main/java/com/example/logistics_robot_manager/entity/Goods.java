package com.example.logistics_robot_manager.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true) // 当 chain 为 true 时，对应字段的 setter 方法调用后，会返回当前对象
public class Goods implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 货物id
     */
    @TableId(value = "goods_id",type = IdType.AUTO)
    private Long goodsId;

    /**
     * 货物名称
     */
    private String goodsName;

    /**
     * 货物状态
     */
    private Integer goodsStatus;

    /**
     * 货物类型id
     */
    private Long goodsTypeId;

    /**
     * 生产时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 上架时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime shelvingTime;

    /**
     * 货物明细
     */
    private String details;

    /**
     * 所在货架的id
     */
    private Long shelfId;

    /**
     * 货物类型名称
     */
    @TableField(exist = false)
    private String goodsTypeName;
}
