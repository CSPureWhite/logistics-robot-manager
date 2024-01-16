package com.example.logistics_robot_manager.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.net.BindException;
import java.util.stream.Collectors;

/**
 * 全局异常处理类
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // TODO
//    @ExceptionHandler(BindException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public Result handleBindException(BindException e){
//
//    }

    /**
     * ConstraintViolationException-jsr规范中的验证异常，嵌套检验问题
     * ConstraintViolationException：作用于 @NotBlank @NotNull @NotEmpty 注解，校验单个String、Integer、Collection等参数异常处理
     * 注：Controller类上必须添加@Validated注解，否则接口单个参数校验无效
     */
    public Result handleConstraintViolationException(ConstraintViolationException e){
        log.error("ConstraintViolationException,参数校验异常:",e);
        String msg=e.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("；"));
        return Result.fail("400",msg);
    }
}
