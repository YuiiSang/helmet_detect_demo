package com.tanght.helmet_detect_sys_demo.common.config;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @Title: KaptchaConfig
 * @Author Tanght 363993584@qq.com
 * @Date 2024/11/27 17:23
 * @description:
 */
@Configuration
public class KaptchaConfig {
    @Bean
    public DefaultKaptcha captchaProducer() {
        DefaultKaptcha kaptcha = new DefaultKaptcha();
        Properties prop = new Properties();
        prop.setProperty(Constants.KAPTCHA_BORDER, "no");
        prop.setProperty(Constants.KAPTCHA_IMAGE_WIDTH, "200");
        prop.setProperty(Constants.KAPTCHA_IMAGE_HEIGHT, "40");
        //验证码的取值范围
        prop.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_STRING, "0123456789");
        //样验证码的字符长度
        prop.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, "6");

        Config config = new Config(prop);

        kaptcha.setConfig(config);

        return kaptcha;
    }
}
