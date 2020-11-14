package com.eternity.blog.system.domain.user;

import com.eternity.blog.common.core.domain.model.BaseEntity;

/**
 * @Description 注册日志实体
 * @Author eternity
 * @Date 2020/4/11 21:55
 */
public class RegisterModel extends BaseEntity {
    /**
     * 注册编号
     */
    private Long rlId;
    /**
     * 用户编号
     */
    private Long rlUid;
    /**
     * 注册时间
     */
    private String rlDate;
    /**
     * 注册地址
     */
    private String rlIp;

    public Long getRlId() {
        return rlId;
    }

    public void setRlId(Long rlId) {
        this.rlId = rlId;
    }

    public Long getRlUid() {
        return rlUid;
    }

    public void setRlUid(Long rlUid) {
        this.rlUid = rlUid;
    }

    public String getRlDate() {
        return rlDate;
    }

    public void setRlDate(String rlDate) {
        this.rlDate = rlDate;
    }

    public String getRlIp() {
        return rlIp;
    }

    public void setRlIp(String rlIp) {
        this.rlIp = rlIp;
    }

    @Override
    public String toString() {
        return "RegisterModel{" +
                "rlId=" + rlId +
                ", rlUid=" + rlUid +
                ", rlDate='" + rlDate + '\'' +
                ", rlIp='" + rlIp + '\'' +
                '}';
    }
}
