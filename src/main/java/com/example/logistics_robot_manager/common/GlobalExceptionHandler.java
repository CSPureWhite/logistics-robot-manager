package com.example.logistics_robot_manager.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * 全局异常处理类
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * BindException异常处理
     * <p>BindException: 作用于@Validated @Valid 注解，仅对于表单提交有效，对于以json格式提交将会失效
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleBindException(BindException e){
        log.error("BindException,参数校验异常: ",e);
        String msg=e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField()+": "+fieldError.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return Result.fail(Constant.CODE_BAD_REQUEST,msg);
    }

    /**
     * ConstraintViolationException-jsr规范中的验证异常，嵌套检验问题
     * ConstraintViolationException：作用于 @NotBlank @NotNull @NotEmpty 注解，校验单个String、Integer、Collection等参数异常处理
     * 注：Controller类上必须添加@Validated注解，否则接口单个参数校验无效
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleConstraintViolationException(ConstraintViolationException e){
        log.error("ConstraintViolationException,参数校验异常: ",e);
        String msg=e.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(";"));
        return Result.fail(Constant.CODE_BAD_REQUEST,msg);
    }

    /**
     * MethodArgumentNotValidException-Spring封装的参数验证异常处理
     * MethodArgumentNotValidException：作用于 @Validated @Valid 注解，接收参数加上@RequestBody注解（json格式）才会有这种异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        log.error("MethodArgumentNotValidException,参数校验异常: ",e);
        String msg=e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField()+": "+fieldError.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return Result.fail(Constant.CODE_BAD_REQUEST,msg);
    }
}
