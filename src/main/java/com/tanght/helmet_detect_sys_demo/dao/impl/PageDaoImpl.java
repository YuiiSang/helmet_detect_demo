package com.tanght.helmet_detect_sys_demo.dao.impl;

import com.tanght.helmet_detect_sys_demo.common.util.PageUtil;
import com.tanght.helmet_detect_sys_demo.common.vo.PageVo;
import com.tanght.helmet_detect_sys_demo.common.vo.PagingUser;
import com.tanght.helmet_detect_sys_demo.dao.PageDao;
import com.tanght.helmet_detect_sys_demo.pojo.Area;
import com.tanght.helmet_detect_sys_demo.pojo.Site;
import jakarta.annotation.Resource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Title: PageDaoImpl
 * @Author Tanght 363993584@qq.com
 * @Date 2024/11/29 11:20
 * @description:
 */
@Repository
public class PageDaoImpl implements PageDao {
    @Resource
    PageUtil pageUtil;

    @Override
    public PageVo<?> findObjectByPage(Integer pageNum, Integer pageSize, Class clazz, String input) {
        String sql = "";
        RowMapper rowMapper = getRowMapper(clazz);
        if (clazz.equals(PagingUser.class)) {
            sql = "SELECT " +
                    "uId," +
                    "username," +
                    "password," +
                    "realName," +
                    "age," +
                    "phone," +
                    "email," +
                    "site_id," +
                    "site_name," +
                    "site_location," +
                    "site_desc" +
                    " FROM user LEFT JOIN site ON user.site_id = site.sId WHERE username LIKE ?"; // 使用占位符
            //模糊查询
            PageVo<PagingUser> pageVo = pageUtil.queryForPage(sql, pageNum, pageSize, new Object[]{"%" + input + "%"}, rowMapper);
            return pageVo;
        }
        if (clazz.equals(Site.class)) {
            sql = "SELECT * FROM site WHERE site_name LIKE ?"; // 使用占位符
            //模糊查询
            PageVo<Site> pageVo = pageUtil.queryForPage(sql, pageNum, pageSize, new Object[]{"%" + input + "%"}, rowMapper);
            return pageVo;
        }
        if (clazz.equals(Area.class)) {
            sql = "SELECT * FROM area WHERE area_name LIKE ?"; // 使用占位符
            //模糊查询
            PageVo<Area> pageVo = pageUtil.queryForPage(sql, pageNum, pageSize, new Object[]{"%" + input + "%"}, rowMapper);
            return pageVo;
        }

        return null;
    }



    public RowMapper getRowMapper(Class clazz){
        if(clazz.equals(PagingUser.class)){
            return new RowMapper<PagingUser>() {
                @Override
                public PagingUser mapRow(ResultSet rs, int rowNum) throws SQLException {
                    PagingUser user = new PagingUser();
                    user.setUId(rs.getInt("uId"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setRealName(rs.getString("realname"));
                    user.setAge(rs.getInt("age"));
                    user.setPhone(rs.getString("phone"));
                    user.setEmail(rs.getString("email"));
                    user.setSId(rs.getInt("site_id"));
                    user.setSiteName(rs.getString("site_name"));
                    user.setSiteLocation(rs.getString("site_location"));
                    user.setSiteDesc(rs.getString("site_desc"));
                    return user;
                }
            };
        }
        if(clazz.equals(Site.class)){
            return new RowMapper<Site>() {
                @Override
                public Site mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Site site = new Site();
                    site.setSId(rs.getInt("sId"));
                    site.setSiteName(rs.getString("site_name"));
                    site.setSiteLocation(rs.getString("site_location"));
                    site.setSiteDesc(rs.getString("site_desc"));
                    return site;
                }
            };
        }
        if(clazz.equals(Area.class)){
            return new RowMapper<Area>() {
                @Override
                public Area mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Area area = new Area();
                    area.setAId(rs.getInt("aId"));
                    area.setAreaName(rs.getString("area_name"));
                    area.setAreaDescription(rs.getString("area_description"));
                    area.setSiteId(rs.getInt("site_id"));
                    area.setCameraIp(rs.getString("camera_ip"));
                    return area;
                }
            };
        }

        return null;
    }
}
