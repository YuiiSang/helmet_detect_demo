package com.tanght.helmet_detect_sys_demo.dao;

import com.tanght.helmet_detect_sys_demo.common.vo.PageVo;
import com.tanght.helmet_detect_sys_demo.common.vo.PagingUser;
import com.tanght.helmet_detect_sys_demo.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface UserDao {
    /**
     * 查询全部用户信息
     * @param
     * @return
     */
    //List<User> findUserByUsername();
    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    User findUserByUsername(String username);

}