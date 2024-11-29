package com.tanght.helmet_detect_sys_demo.dao.impl;

import com.tanght.helmet_detect_sys_demo.common.util.PageUtil;
import com.tanght.helmet_detect_sys_demo.common.vo.PageVo;
import com.tanght.helmet_detect_sys_demo.common.vo.PagingUser;
import com.tanght.helmet_detect_sys_demo.dao.UserDao;
import com.tanght.helmet_detect_sys_demo.pojo.User;
import jakarta.annotation.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @Title: UserDaoImpl
 * @Author Tanght 363993584@qq.com
 * @Date 2024/11/27 21:52
 * @description:
 */
@Repository
public class UserDaoImpl implements UserDao {
    @Resource
    JdbcTemplate jdbcTemplate;


    @Override
    public User findUserByUsername(String username) {
        String sql = "select * from user where username = ?";
        List<User> users = jdbcTemplate.query(sql, new Object[]{username}, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User();
                user.setUId(rs.getInt("uId"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRealName(rs.getString("realname"));
                user.setAge(rs.getInt("age"));
                user.setPhone(rs.getString("phone"));
                user.setEmail(rs.getString("email"));
                user.setSiteId(rs.getInt("site_id"));
                return user;
            }
        });
        return users.isEmpty() ? null : users.get(0);
    }

}
