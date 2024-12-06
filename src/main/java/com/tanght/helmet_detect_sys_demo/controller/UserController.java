package com.tanght.helmet_detect_sys_demo.controller;


import com.tanght.helmet_detect_sys_demo.common.dto.ModifyUserPwd;
import com.tanght.helmet_detect_sys_demo.common.result.Result;
import com.tanght.helmet_detect_sys_demo.common.dto.LoginUser;
import com.tanght.helmet_detect_sys_demo.pojo.User;

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

    Result<?> modifyPwd(ModifyUserPwd modifyUserPwd);

    //删除用户
    Result<?> deleteUser(int id);
    //修改用户
    Result<?> modifyUser(User user);
}
