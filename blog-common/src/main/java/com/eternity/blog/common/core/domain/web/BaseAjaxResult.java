package com.eternity.blog.common.core.domain.web;

import com.eternity.blog.common.enums.WebStatusCode;
import com.eternity.blog.common.utils.I18nUtils;
import com.eternity.blog.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.NoSuchMessageException;

/**
 * @Description 用户请求响应基类
 * @Author eternity
 * @Date 2020/4/13 21:01
 */
public class BaseAjaxResult extends AjaxEntity {
    private static final Logger log = LoggerFactory.getLogger(BaseAjaxResult.class);

    public BaseAjaxResult() {
    }

    public BaseAjaxResult(WebStatusCode code, String msg, Object data) {
        setCode(code.value());
        if (StringUtils.isNotNull(msg) && StringUtils.isNotEmpty(msg)) {
            try {
                setMessage(I18nUtils.message(msg));
            } catch (NoSuchMessageException e) {
                log.error(e.getMessage());
                setMessage(msg);
            }
        }
        if (StringUtils.isNull(data)) {
            return;
        }
        setData(data);
    }

    public static BaseAjaxResult error(String msg) {
        return error(msg, null);
    }

    public static BaseAjaxResult error(String msg, Object data) {
        return new BaseAjaxResult(WebStatusCode.ERROR, msg, data);
    }

    public static BaseAjaxResult success(String msg) {
        return success(msg, null);
    }

    public static BaseAjaxResult success(String msg, Object data) {
        return new BaseAjaxResult(WebStatusCode.SUCCESS, msg, data);
    }
}
