package com.tanght.helmet_detect_sys_demo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.code.kaptcha.impl.DefaultKaptcha;

import com.tanght.helmet_detect_sys_demo.common.config.JwtComponent;
import com.tanght.helmet_detect_sys_demo.common.result.Result;
import com.tanght.helmet_detect_sys_demo.common.util.MD5Util;
import com.tanght.helmet_detect_sys_demo.common.vo.LoginUser;

import com.tanght.helmet_detect_sys_demo.dao.UserDao;
import com.tanght.helmet_detect_sys_demo.pojo.User;
import com.tanght.helmet_detect_sys_demo.service.UserService;
import jakarta.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

/**
 * @Title: UserServiceImpl
 * @Author Tanght 363993584@qq.com
 * @Date 2024/11/27 17:30
 * @description:
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private DefaultKaptcha captchaProducer;

    @Resource
    private StringRedisTemplate redisTemplate;

    @Resource
    private UserDao userDao;

    @Value("${captcha.ttl}")
    private int captchaTtl; // 验证码的过期时间，单位为秒

    @Resource
    private JwtComponent jwtComponent;

    @Override
    public JSONObject getKaptcha(){
        JSONObject obj = new JSONObject();
        // 生成验证码文本
        String capText = captchaProducer.createText();
        obj.put("vcode",capText);
        // 将验证码文本存储到Redis，并设置过期时间
        String key = capText + "_key";
        redisTemplate.opsForValue().set(key, capText, captchaTtl, TimeUnit.SECONDS);
        // 生成验证码图片
        BufferedImage bi = captchaProducer.createImage(capText);
        // 将图片转换为Base64编码的字符串
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        try {
            ImageIO.write(bi, "png", bao);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        byte[] imageBytes = bao.toByteArray();
        String base64Image = "data:image/png;base64," + Base64.getEncoder().encodeToString(imageBytes);

        obj.put("kaptchaImage", base64Image);
        // 返回Base64编码的图片字符串
        return obj;
    }

    @Override
    public boolean validateCaptcha(String inputCode) {
        // 从Redis中获取存储的验证码
        String key = inputCode + "_key";
        String storedCode = redisTemplate.opsForValue().get(key);
        // 验证输入的验证码是否正确，并从Redis中删除该验证码
        if (storedCode != null && storedCode.equals(inputCode)) {
            redisTemplate.delete(key);
            return true;
        }
        return false;
    }

    @Override
    public Result<?> login(LoginUser loginUser) {
        User user = userDao.findUserByUsername(loginUser.getUsername());

        // 检查用户是否存在
        if (user == null) {
            // 如果用户为null，说明没有查询到该账号
            return new Result<>().error("没有查询到该账号!");
        }

        // 用户存在，进行密码校验
        String inputPwd = MD5Util.getMD5(loginUser.getPassword());
        if (inputPwd.equals(user.getPassword())) {
            String sign = jwtComponent.sign(user.getUsername(), 60*60);
            HashMap<String, String> map = new HashMap<>();
            map.put("token", sign);
            return new Result<>().success("成功登录").put(map);
        } else {
            // 密码错误
            return new Result<>().error("密码错误");
        }
    }

    @Override
    public boolean isValidUser(String username){
        return userDao.findUserByUsername(username) != null;
    }
}
