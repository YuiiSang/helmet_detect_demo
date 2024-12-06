package com.tanght.helmet_detect_sys_demo.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Title: User
 * @Author Tanght 363993584@qq.com
 * @Date 2024/11/27 16:38
 * @description:
 */
@Data
public class User {
    @JsonProperty("uId")
    private Integer uId;
    private String username;
    private String password;
    private String realName;
    private Integer age;
    private String phone;
    private String email;
    private Integer siteId;
}
