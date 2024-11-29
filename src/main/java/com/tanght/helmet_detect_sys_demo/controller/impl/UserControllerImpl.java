package com.tanght.helmet_detect_sys_demo.controller.impl;

import com.alibaba.fastjson.JSONObject;
import com.tanght.helmet_detect_sys_demo.common.result.Result;
import com.tanght.helmet_detect_sys_demo.common.vo.LoginUser;
import com.tanght.helmet_detect_sys_demo.controller.UserController;
import com.tanght.helmet_detect_sys_demo.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Title: UserController
 * @Author Tanght 363993584@qq.com
 * @Date 2024/11/27 17:21
 * @description:
 */
@RestController
public class UserControllerImpl implements UserController {

    @Resource
    private UserService userService;

    @Override
    @GetMapping("/getKaptcha")
    public Result<?> getKaptcha() {
        JSONObject obj = userService.getKaptcha();
        Result<JSONObject> result = new Result<>();
        return result.success().put(obj);
    }

    @Override
    @PostMapping("/login")
    public Result<?> login(@RequestBody LoginUser loginUser) {
        return userService.login(loginUser);
    }
}
