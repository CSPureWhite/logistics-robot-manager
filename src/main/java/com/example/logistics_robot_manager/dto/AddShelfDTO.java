package com.example.logistics_robot_manager.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddShelfDTO {
    @NotBlank(message = "货架名不能为空值")
    private String shelfName;
    private Integer shelfType;
}
