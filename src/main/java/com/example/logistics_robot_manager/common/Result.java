package com.example.logistics_robot_manager.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 回传前端结果封装类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private String code;
    private String msg;
    private Object data;
    private Long total;

    public static Result ok(){
        return new Result( Constant.CODE_OK,"操作成功", null,null);
    }
    public static Result ok(Object data){
        return new Result( Constant.CODE_OK,"操作成功", data,null);
    }
    public static Result ok(List<?> data, Long total) {
        return new Result( Constant.CODE_OK,"操作成功", data, total);
    }
    public static Result fail(String errorCode,String msg){
        return new Result( errorCode,msg, null,null);
    }
}
