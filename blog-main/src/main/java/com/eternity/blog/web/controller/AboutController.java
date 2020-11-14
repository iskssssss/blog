package com.eternity.blog.web.controller;

import com.eternity.blog.common.utils.StringUtils;
import com.eternity.blog.system.domain.user.UserModel;
import com.eternity.blog.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Description 关于页请求控制器
 * @Author eternity
 * @Date 2020/4/14 17:37
 */
@Controller
public class AboutController {
    @Autowired
    IUserService userService;

    @GetMapping(value = "/about")
    public String about(ModelMap modelMap) {
        UserModel user = userService.selectUserByUid(49263L);
        if (StringUtils.isNotNull(user)) {
            user.setUserAuthsModel(null);
        }
        modelMap.put("user", user);
        return "about";
    }
}
