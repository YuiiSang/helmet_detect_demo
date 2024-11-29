package com.tanght.helmet_detect_sys_demo.controller.impl;

import com.tanght.helmet_detect_sys_demo.common.result.Result;
import com.tanght.helmet_detect_sys_demo.controller.PageController;
import com.tanght.helmet_detect_sys_demo.service.PageService;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Title: PageControllerImpl
 * @Author Tanght 363993584@qq.com
 * @Date 2024/11/29 11:27
 * @description:
 */
@RestController
public class PageControllerImpl implements PageController {
    @Resource
    PageService pageService;
    @Override
    @GetMapping("/getPage")
    public Result<?> getPage(
            @RequestParam int pageNum,
            @RequestParam int pageSize,
            @RequestParam String pageName) {
        return new Result<>().success().put(pageService.findObjectByPage(pageNum, pageSize, pageName));
    }
}
