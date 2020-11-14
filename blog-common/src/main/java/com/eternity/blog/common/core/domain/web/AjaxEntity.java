package com.eternity.blog.common.core.domain.web;

//import javax.validation.constraints.NotNull;

/**
 * @Description
 * @Author eternity
 * @Date 2020/4/13 21:42
 */
public class AjaxEntity {

//    @NotNull(message = "缺少状态信息")
    private int code;

    private String message;

    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AjaxDto{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
