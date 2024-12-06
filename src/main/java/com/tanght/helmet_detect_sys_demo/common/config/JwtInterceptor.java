package com.tanght.helmet_detect_sys_demo.common.config;


import com.tanght.helmet_detect_sys_demo.common.Exceptions.UnAuthException;
import com.tanght.helmet_detect_sys_demo.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Date;

/**
 * @Title:
 * @Author Tanght 363993584@qq.com
 * @Date 2024/11/29 15:57
 * @description:
 */


@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Resource
    private JwtComponent jwtComponent;

    @Resource
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 忽略OPTIONS请求
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        // 从请求头获取 Token
        String token = request.getHeader("Authorization");
        //System.out.println("Token: " + token);
        // 判断 token 是否为空
        if (ObjectUtils.isEmpty(token)) {
            // 抛出 Token 为空异常
            throw new UnAuthException("Token 为空");
        }
        //去除前缀
        token = token.replace("Bearer ", "");
        // 验证 Token
        if(!jwtComponent.verify(token)){
            throw new UnAuthException("Token 验证失败");
        }
        // 检查 Token 是否过期
        Date expirationDate = jwtComponent.getExpiresAt(token);
        if (expirationDate.before(new Date())) {
            // 抛出 Token 过期异常
            throw new UnAuthException("Token 已过期");
        }

        // 添加进一步的验证：验证用户名是否有效
        String username = jwtComponent.getUsernameFromToken(token);
         //在这里你可以根据用户名查数据库验证用户是否有效，
         if (!userService.isValidUser(username)) {
             throw new UnAuthException("无效的用户");
         }

        // Token 和用户验证通过，允许请求继续
        return true;
    }
}