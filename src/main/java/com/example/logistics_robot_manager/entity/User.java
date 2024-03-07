package com.example.logistics_robot_manager.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "user_id",type = IdType.AUTO)
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户类型，1为普通用户，2为管理员
     */
    private Integer userType;

    /**
     * 用户状态，false为被禁用，true为活跃
     */
    private Boolean isActive;

    /**
     * 创建时间
     */

    private LocalDateTime createTime;

    /**
     * 最近一次更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 最近一次登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime loginTime;

    /**
     * 最近一次执行任务的任务id
     */
    private Long taskId;

    /**
     * 最近一次执行任务的任务名称
     */
    @TableField(exist = false)
    private String taskName;

    /**
     * 最近一次任务分配时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField(exist = false)
    private LocalDateTime taskStartTime;
}
