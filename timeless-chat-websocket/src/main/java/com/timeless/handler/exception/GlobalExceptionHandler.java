package com.timeless.handler.exception;

import com.timeless.domain.ResponseResult;
import com.timeless.enums.AppHttpCodeEnum;
import com.timeless.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author timeless
 * @create 2022-12-08 20:52
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExceptionHandler(SystemException e){
        e.printStackTrace();
        log.error(e.getMessage());
        return ResponseResult.errorResult(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public ResponseResult bindExceptionHandler(BindException exception){
        List<String> collect = exception.getAllErrors().stream().map(item -> item.getDefaultMessage())
                .collect(Collectors.toList());
        for (String s : collect) {
            log.error(s);
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.PARAMS_ERROR , collect.get(0));
    }

    /**
     * 处理其他异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandler(Exception e){
        e.printStackTrace();
        log.error(e.getMessage());
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),e.getMessage());
    }

}
