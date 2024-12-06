package com.tanght.helmet_detect_sys_demo.service;

import com.tanght.helmet_detect_sys_demo.common.vo.PageVo;

public interface PageService {

    PageVo<?> findObjectByPage(Integer pageNum, Integer pageSize, String pageName, String input);
}
