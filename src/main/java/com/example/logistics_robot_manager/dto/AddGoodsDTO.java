package com.example.logistics_robot_manager.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AddGoodsDTO {
    @NotBlank(message = "货物名不能为空值")
    private String goodsName;
    @NotNull(message = "货物类型不能为空值")
    private Long goodsType;
}
