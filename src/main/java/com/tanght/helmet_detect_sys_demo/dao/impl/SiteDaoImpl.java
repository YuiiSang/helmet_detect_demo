package com.tanght.helmet_detect_sys_demo.dao.impl;

import com.tanght.helmet_detect_sys_demo.common.vo.ModifyUserSite;
import com.tanght.helmet_detect_sys_demo.dao.SiteDao;
import jakarta.annotation.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Title: SiteDaoImpl
 * @Author Tanght 363993584@qq.com
 * @Date 2024/12/4 8:36
 * @description:
 */
@Repository
public class SiteDaoImpl implements SiteDao {
    @Resource
    JdbcTemplate jdbcTemplate;

    @Override
    public List<ModifyUserSite> getSiteNameList() {
        return jdbcTemplate.query("select sId, site_name from site", (rs, rowNum) -> {
            ModifyUserSite modifyUserSite = new ModifyUserSite();
            modifyUserSite.setSId(rs.getInt("sId"));
            modifyUserSite.setSiteName(rs.getString("site_name"));
            return modifyUserSite;
        });
    }
}
