package com.tanght.helmet_detect_sys_demo.controller;


import com.tanght.helmet_detect_sys_demo.common.result.Result;
import com.tanght.helmet_detect_sys_demo.common.vo.LoginUser;

/**
 * @Title: UserController
 * @Author Tanght 363993584@qq.com
 * @Date 2024/11/27 17:20
 * @description:
 */
public interface UserController {

    //获取验证码
    Result<?> getKaptcha();

    //登录方法
    Result<?> login(LoginUser loginUser);
}
