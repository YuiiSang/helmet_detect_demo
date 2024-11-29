package com.tanght.helmet_detect_sys_demo.common.vo;

import lombok.Data;

/**
 * @Title: PagingUser
 * @Author Tanght 363993584@qq.com
 * @Date 2024/11/29 9:55
 * @description:
 */
@Data
public class PagingUser {
    private Integer uId;
    private String username;
    private String password;
    private String realname;
    private Integer age;
    private String phone;
    private String email;
    private String siteName;
    private String siteLocation;
    private String siteDesc;
}
