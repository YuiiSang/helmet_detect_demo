package com.tanght.helmet_detect_sys_demo.common.config;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * @Title: WebConfig
 * @Author Tanght 363993584@qq.com
 * @Date 2024/11/27 19:00
 * @description:
 */

@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer{

    @Resource
    private JwtInterceptor jwtInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 允许跨域的路径
                .allowedOrigins("*") // 允许访问的源
                .allowedMethods("GET", "POST", "PUT", "DELETE","OPTIONS") // 允许的方法
                .allowedHeaders("*") // 允许的头部信息
                .maxAge(3600); // 预检请求的缓存时间
    }

    // 注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**") // 拦截所有路径
                .excludePathPatterns("/login", "/getKaptcha"); // 排除登录和验证码

    }

}