package com.eternity.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description
 * @Author eternity
 * @Date 2020/4/10 16:44
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("login").setViewName("login");
        registry.addViewController("register").setViewName("register");
        registry.addViewController("about").setViewName("about");
        registry.addViewController("system").setViewName("system/main");
        registry.addViewController("system/labels").setViewName("system/label/labels");
        registry.addViewController("system/label/add").setViewName("system/label/add");
        registry.addViewController("system/sorts").setViewName("system/sort/sorts");
        registry.addViewController("system/sort/add").setViewName("system/sort/add");
        registry.addViewController("system/blogs").setViewName("system/blog/blogs");
        registry.addViewController("system/blogs").setViewName("system/blog/blogs");
        registry.addViewController("system/comment").setViewName("system/blog/comment");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");

        /** 本地文件上传路径 */
        registry.addResourceHandler("image/**").addResourceLocations("file:/blog/image/");
    }
}
