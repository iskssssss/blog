package com.eternity.blog.common.core.controller;

import com.eternity.blog.common.core.domain.web.BaseAjaxResult;

/**
 * @Description 基本控制器
 * @Author eternity
 * @Date 2020/4/13 20:59
 */
public class BaseController {

    public BaseAjaxResult error(String msg) {
        return error(msg, null);
    }

    public BaseAjaxResult error(String msg, Object data) {
        return BaseAjaxResult.error(msg, data);
    }

    public BaseAjaxResult success(String msg) {
        return success(msg, null);
    }

    public BaseAjaxResult success(Object data) {
        return success("request.success", data);
    }

    public BaseAjaxResult success(String msg, Object data) {
        return BaseAjaxResult.success(msg, data);
    }
}
