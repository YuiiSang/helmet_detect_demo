package com.tanght.helmet_detect_sys_demo.controller.impl;

import com.alibaba.fastjson.JSONObject;
import com.tanght.helmet_detect_sys_demo.common.dto.ModifyUserPwd;
import com.tanght.helmet_detect_sys_demo.common.result.Result;
import com.tanght.helmet_detect_sys_demo.common.dto.LoginUser;
import com.tanght.helmet_detect_sys_demo.controller.UserController;
import com.tanght.helmet_detect_sys_demo.pojo.User;
import com.tanght.helmet_detect_sys_demo.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @Title: UserController
 * @Author Tanght 363993584@qq.com
 * @Date 2024/11/27 17:21
 * @description:
 */
@RestController
@RequestMapping("/user")
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

    @Override
    @PostMapping("/modifyPwd")
    public Result<?> modifyPwd(@RequestBody ModifyUserPwd modifyUserPwd) {
        Result<?> result = new Result<>();
        boolean isOk = userService.modifyPwd(modifyUserPwd.getUid(), modifyUserPwd.getPassword());
        return isOk ? result.success("密码修改成功") : result.error("修改密码失败");
    }

    @Override
    @GetMapping("/deleteUserById")
    public Result<?> deleteUser(int uId) {
        Result<?> result = new Result<>();
        boolean isOk = userService.deleteUserById(uId);
        return isOk ? result.success("删除成功") : result.error("删除失败");
    }

    @Override
    @PostMapping("/modifyUser")
    public Result<?> modifyUser(@RequestBody User user) {
        Result<?> result = new Result<>();
        boolean isOk = userService.modifyUser(user);
        return isOk ? result.success("修改成功") : result.error("修改失败");
    }

    @Override
    @PostMapping("/addUser")
    public Result<?> addUser(@RequestBody User user) {
        Result<?> result = new Result<>();
        boolean isOk = userService.addUser(user);
        return isOk ? result.success("新增用户成功") : result.error("新增用户失败");
    }


}
