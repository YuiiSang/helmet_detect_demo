package com.tanght.helmet_detect_sys_demo.common.Exceptions;

/**
 * @Title: q
 * @Author Tanght 363993584@qq.com
 * @Date 2024/11/29 19:56
 * @description:
 */
public class UnAuthException extends RuntimeException{
    public UnAuthException(){
        super();
    }
    public UnAuthException(String message){
        super(message);
    }
}
