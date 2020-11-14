package com.eternity.blog.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Description 首页请求控制器
 * @Author eternity
 * @Date 2020/5/13 10:48
 */
@Controller
public class IndexController {
    private final String INDEX = "index";

    @GetMapping("/")
    public String index(){
        return INDEX;
    }
}
