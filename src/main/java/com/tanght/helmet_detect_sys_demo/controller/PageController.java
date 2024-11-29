package com.tanght.helmet_detect_sys_demo.controller;

import com.tanght.helmet_detect_sys_demo.common.result.Result;


public interface PageController {

    Result<?> getPage(int pageNum, int pageSize, String pageName);
}
