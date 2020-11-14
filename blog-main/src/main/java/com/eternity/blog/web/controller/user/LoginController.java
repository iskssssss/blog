package com.eternity.blog.web.controller.user;

import com.eternity.blog.common.core.controller.BaseController;
import com.eternity.blog.common.core.domain.web.BaseAjaxResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description 登录请求控制器
 * @Author eternity
 * @Date 2020/4/10 18:30
 */
@Controller
public class LoginController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);


//    @ResponseBody
//    @GetMapping(value = "/system/admin")
//    public String admin(){
//        log.warn("请求");
//        return "system/admin";
//    }

    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        return "login";
    }

//    @GetMapping("/logout")
//    public String logout(){
//        Subject subject = SecurityUtils.getSubject();
//        subject.logout();
//        return "/main";
//    }


    @PostMapping("/login")
    @ResponseBody
    public BaseAjaxResult ajaxLogin(@RequestParam("username") String username,
                                    @RequestParam("password") String password,
                                    @RequestParam("rememberMe") boolean rememberMe) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        token.setRememberMe(rememberMe);
        Subject subject = SecurityUtils.getSubject();
        String msg;
        try {
            subject.login(token);
            return success("user.login.success");
        } catch (AuthenticationException e) {
            msg = e.getMessage();
        }
        return error(msg);
    }
}
