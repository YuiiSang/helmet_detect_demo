package com.tanght.helmet_detect_sys_demo.dao;

import com.tanght.helmet_detect_sys_demo.common.vo.PageVo;
import com.tanght.helmet_detect_sys_demo.common.vo.PagingUser;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface PageDao {


    PageVo<?> findObjectByPage(Integer pageNum, Integer pageSize, Class clazz);

}
