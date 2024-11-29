package com.tanght.helmet_detect_sys_demo.dao.impl;

import com.tanght.helmet_detect_sys_demo.common.util.PageUtil;
import com.tanght.helmet_detect_sys_demo.common.vo.PageVo;
import com.tanght.helmet_detect_sys_demo.common.vo.PagingUser;
import com.tanght.helmet_detect_sys_demo.dao.PageDao;
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
    public PageVo<?> findObjectByPage(Integer pageNum, Integer pageSize, Class clazz) {
        String sql = "";
        if(clazz.equals(PagingUser.class)){
            sql = "select * from user left join site on user.site_id = site.sId ";
            PageVo<PagingUser> pageVo = pageUtil.queryForPage(sql, pageNum, pageSize,new Object[]{}, getRowMapper(clazz));
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
                    user.setRealname(rs.getString("realname"));
                    user.setAge(rs.getInt("age"));
                    user.setPhone(rs.getString("phone"));
                    user.setEmail(rs.getString("email"));
                    user.setSiteName(rs.getString("site_name"));
                    user.setSiteLocation(rs.getString("site_location"));
                    user.setSiteDesc(rs.getString("site_desc"));
                    return user;
                }
            };
        }

        return null;
    }
}
