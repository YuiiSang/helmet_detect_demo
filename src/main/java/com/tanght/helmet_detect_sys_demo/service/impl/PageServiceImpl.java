package com.tanght.helmet_detect_sys_demo.service.impl;

import com.tanght.helmet_detect_sys_demo.common.vo.PageVo;
import com.tanght.helmet_detect_sys_demo.common.vo.PagingUser;
import com.tanght.helmet_detect_sys_demo.dao.PageDao;
import com.tanght.helmet_detect_sys_demo.pojo.Area;
import com.tanght.helmet_detect_sys_demo.pojo.Site;
import com.tanght.helmet_detect_sys_demo.service.PageService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Title: PageServiceImpl
 * @Author Tanght 363993584@qq.com
 * @Date 2024/11/29 11:22
 * @description:
 */
@Service
public class PageServiceImpl implements PageService {
    @Resource
    private PageDao pageDao;
    @Override
    public PageVo<?> findObjectByPage(Integer pageNum, Integer pageSize, String pageName, String input) {
        if(pageName.equals("userPage")){
            return pageDao.findObjectByPage(pageNum,pageSize, PagingUser.class, input);
        }
        if(pageName.equals("sitePage")){
            return pageDao.findObjectByPage(pageNum,pageSize, Site.class, input);
        }
        if(pageName.equals("areaPage")){
            return pageDao.findObjectByPage(pageNum,pageSize, Area.class, input);
        }
        return null;
    }
}
