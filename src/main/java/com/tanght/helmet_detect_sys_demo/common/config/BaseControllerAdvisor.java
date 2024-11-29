package com.tanght.helmet_detect_sys_demo.common.config;

import com.tanght.helmet_detect_sys_demo.common.Exceptions.UnAuthException;
import com.tanght.helmet_detect_sys_demo.common.result.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Title:
 * @Author Tanght 363993584@qq.com
 * @Date 2024/11/29 19:56
 * @description:
 */
@RestControllerAdvice
public class BaseControllerAdvisor {
    @ExceptionHandler(UnAuthException.class)
    public Result<?> handleAunAuthoException(UnAuthException e){
        e.printStackTrace();
        return new Result<>().unAuth("操作未授权");
    }

    //如果没有匹配到自定义异常则找到父类异常,进行处理
    @ExceptionHandler(RuntimeException.class)
    public Result<?> handleRuntimeException(RuntimeException e){
        e.printStackTrace();
        return new Result<>().error("操作错误");
    }
}