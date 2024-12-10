package com.tanght.helmet_detect_sys_demo.dao;

import com.tanght.helmet_detect_sys_demo.common.vo.ModifyUserSite;
import com.tanght.helmet_detect_sys_demo.pojo.Site;

import java.util.List;

public interface SiteDao {
    List<ModifyUserSite> getSiteNameList();

    int modifySite(Site site);

    int addSite(Site site);

    int deleteSiteById(Integer sId);
}
