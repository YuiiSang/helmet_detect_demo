package com.tanght.helmet_detect_sys_demo.common.util;

import com.tanght.helmet_detect_sys_demo.common.vo.PageVo;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Title: PageUtil
 * @Author Tanght 363993584@qq.com
 * @Date 2024/11/29 10:17
 * @description:
 */

@Component
public class PageUtil {
    @Resource
    private JdbcTemplate jdbcTemplate;

    public PageVo queryForPage(String sql, Integer pageNum, Integer pageSize, Object[] params, RowMapper rowMapper) throws DataAccessException {
        if (pageNum == null || pageSize == null) {
            return null;
        }
        if (pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize < 1) {
            pageSize = 10;
        }
        PageVo result = new PageVo();

        // 获取记录条数
        String countSql = "select count(1) as count from (" + sql + ") temp";

        List<Integer> countList = jdbcTemplate.queryForList(countSql, params, Integer.class);

        result.setTotal(countList.get(0));
        result.setPageNum(pageNum);
        result.setPageSize(pageSize);

        int pageCount = result.getTotal() % result.getPageSize();
        result.setPages(pageCount == 0 ? (result.getTotal() / result.getPageSize()) : (result.getTotal() / result.getPageSize() + 1));

        sql += parseLimit(result);

        List<?> data = jdbcTemplate.query(sql, params, rowMapper);
        result.setList(data);
        return result;
    }

    private String parseLimit(PageVo pagination) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(" limit ");
        stringBuffer.append(pagination.getPageSize());

        stringBuffer.append(" offset ");
        stringBuffer.append(pagination.getPageSize() * (pagination.getPageNum() - 1));

        return stringBuffer.toString();
    }
}
