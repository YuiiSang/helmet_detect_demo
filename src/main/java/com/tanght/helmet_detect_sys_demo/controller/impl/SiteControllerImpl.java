package com.tanght.helmet_detect_sys_demo.controller.impl;

import com.tanght.helmet_detect_sys_demo.common.result.Result;
import com.tanght.helmet_detect_sys_demo.common.vo.ModifyUserSite;
import com.tanght.helmet_detect_sys_demo.controller.SiteController;
import com.tanght.helmet_detect_sys_demo.pojo.Site;
import com.tanght.helmet_detect_sys_demo.service.SiteService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

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
    @Override
    @PostMapping("/modifySite")
    public Result<?> modifySite(@RequestBody Site site){
        Result<?> result = new Result<>();
        boolean isOk = siteService.modifySite(site);
        if(isOk){
            result.success(site.getSiteName()+"的信息修改成功");
        }else {
            result.error(site.getSiteName()+"的信息修改失败");
        }
        return result;
    }

    @Override
    @PostMapping("/addSite")
    public Result<?> addSite(@RequestBody Site site) {
        Result<?> result = new Result<>();
        boolean isOk = siteService.addSite(site);
        if(isOk){
            result.success("工地新增成功");
        }else{
            result.error("工地新增失败");
        }
        return result;
    }

    @Override
    @GetMapping("/deleteSiteById")
    public Result<?> deleteSiteById(Integer sId) {
        Result<?> result = new Result<>();
        boolean isOk = siteService.deleteSiteById(sId);
        if(isOk){
            result.success("工地删除成功");
        }else{
            result.error("工地删除失败");
        }
        return result;
    }
}
