package com.example.logistics_robot_manager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 日活统计量实体类
 */
@Data
@AllArgsConstructor
public class DAUDTO {
    private String date;
    private Long dau;
}
