package com.tanght.helmet_detect_sys_demo.service.impl;

import com.tanght.helmet_detect_sys_demo.common.vo.ModifyUserSite;
import com.tanght.helmet_detect_sys_demo.dao.SiteDao;
import com.tanght.helmet_detect_sys_demo.pojo.Site;
import com.tanght.helmet_detect_sys_demo.service.SiteService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Title: SiteService
 * @Author Tanght 363993584@qq.com
 * @Date 2024/12/4 8:37
 * @description:
 */
@Service
public class SiteServiceImpl implements SiteService {
    @Resource
    private SiteDao siteDao;
    @Override
    public List<ModifyUserSite> getSiteNameList() {
        return siteDao.getSiteNameList();
    }

    @Override
    public boolean modifySite(Site site) {
        return siteDao.modifySite(site) > 0;
    }

    @Override
    public boolean addSite(Site site) {
        int result = siteDao.addSite(site);
        return result > 0;
    }

    @Override
    public boolean deleteSiteById(Integer sId) {
        return siteDao.deleteSiteById(sId) > 0;
    }
}
