package com.tanght.helmet_detect_sys_demo.service;

import com.tanght.helmet_detect_sys_demo.common.vo.ModifyUserSite;

import java.util.List;

/**
 * @Title: SiteImpl
 * @Author Tanght 363993584@qq.com
 * @Date 2024/12/4 8:37
 * @description:
 */
public interface SiteService {
    /**
     * 获取所有工地名称
     * @return
     */
    List<ModifyUserSite> getSiteNameList();
}
