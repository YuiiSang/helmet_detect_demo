package com.tanght.helmet_detect_sys_demo.controller;

import com.tanght.helmet_detect_sys_demo.common.result.Result;
import com.tanght.helmet_detect_sys_demo.pojo.Site;


public interface SiteController {

    Result<?> getSiteNameList();

    Result<?> modifySite(Site Site);

    Result<?> addSite(Site site);

    Result<?> deleteSiteById(Integer sId);
}
