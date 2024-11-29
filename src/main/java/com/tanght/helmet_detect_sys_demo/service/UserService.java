package com.tanght.helmet_detect_sys_demo.service;

import com.alibaba.fastjson.JSONObject;
import com.tanght.helmet_detect_sys_demo.common.result.Result;
import com.tanght.helmet_detect_sys_demo.common.vo.LoginUser;
import com.tanght.helmet_detect_sys_demo.common.vo.PageVo;


public interface UserService {
    /**
     * 获取验证码
     * @param
     * @return
     */
    JSONObject getKaptcha();

    /**
     * 验证验证码
     * @param inputCode
     * @return
     */
    boolean validateCaptcha(String inputCode);

    /**
     * 根据登录账号判断登录
     * @param loginUser
     * @return
     * */
    Result<?> login(LoginUser loginUser);

    /**
     *  根据用户名判断用户是否存在
     * @param username
     * @return
     * */
    boolean isValidUser(String username);
}
