package com.tanght.helmet_detect_sys_demo;

import com.tanght.helmet_detect_sys_demo.common.util.MD5Util;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class HelmetDetectSysDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelmetDetectSysDemoApplication.class, args);
        System.out.println(MD5Util.getMD5("123456"));
    }

}
