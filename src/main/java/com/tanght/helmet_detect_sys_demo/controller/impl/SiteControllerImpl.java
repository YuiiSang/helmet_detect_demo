package com.tanght.helmet_detect_sys_demo.controller.impl;

import com.tanght.helmet_detect_sys_demo.common.result.Result;
import com.tanght.helmet_detect_sys_demo.common.vo.ModifyUserSite;
import com.tanght.helmet_detect_sys_demo.controller.SiteController;
import com.tanght.helmet_detect_sys_demo.service.SiteService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Title: SiteControllerImpl
 * @Author Tanght 363993584@qq.com
 * @Date 2024/12/4 8:35
 * @description:
 */
@RestController
@RequestMapping("/site")
public class SiteControllerImpl implements SiteController {

    @Resource
    private SiteService siteService;

    @Override
    @GetMapping("/getSiteNameList")
    public Result<?> getSiteNameList() {
        Result<List<ModifyUserSite>> result = new Result<>();
        List<ModifyUserSite> siteNameList = siteService.getSiteNameList();
        if(siteNameList.size() > 0){
            result.success().put(siteNameList);
        }else {
            result.error("查询失败");
        }
        return result;
    }
}
