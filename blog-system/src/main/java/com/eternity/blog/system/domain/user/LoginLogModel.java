package com.eternity.blog.system.domain.user;

import com.eternity.blog.common.core.domain.model.BaseEntity;

/**
 * @Description 登陆记录实体
 * @Author eternity
 * @Date 2020/4/14 21:56
 */
public class LoginLogModel extends BaseEntity {
    /**
     * 日志编号
     */
    private Long llId;
    /**
     * 用户编号
     */
    private Long llUid;
    /**
     * 记录时间
     */
    private String llDate;
    /**
     * 登录地址
     */
    private String llIp;
    /**
     * 登录类型
     */
    private Integer llType;

    public Long getLlId() {
        return llId;
    }

    public void setLlId(Long llId) {
        this.llId = llId;
    }

    public Long getLlUid() {
        return llUid;
    }

    public void setLlUid(Long llUid) {
        this.llUid = llUid;
    }

    public String getLlDate() {
        return llDate;
    }

    public void setLlDate(String llDate) {
        this.llDate = llDate;
    }

    public String getLlIp() {
        return llIp;
    }

    public void setLlIp(String llIp) {
        this.llIp = llIp;
    }

    public Integer getLlType() {
        return llType;
    }

    public void setLlType(Integer llType) {
        this.llType = llType;
    }

    @Override
    public String toString() {
        return "LoginLogModel{" +
                "llId=" + llId +
                ", llUid=" + llUid +
                ", llDate='" + llDate + '\'' +
                ", llIp='" + llIp + '\'' +
                ", llType=" + llType +
                '}';
    }
}
