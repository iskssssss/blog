package com.eternity.blog.system.domain.session;

import com.eternity.blog.common.core.domain.model.BaseEntity;

/**
 * @Description 在线用户会话实体(DB)
 * @Author eternity
 * @Date 2020/4/16 11:44
 */
public class OnlineSessionModel extends BaseEntity {
    /**
     * 会话id
     */
    private String uoSessionId;
    /**
     * 用户ID
     */
    private Long uoUid;
    /**
     * 登陆地址
     */
    private String uoIp;
    /**
     * 浏览器类型
     */
    private String uoBrowser;
    /**
     * 操作系统
     */
    private String uoOs;
    /**
     * 创建时间
     */
    private String uoStartDate;
    /**
     * 最后访问时间
     */
    private String uoLastDate;
    /**
     * 过期时间
     */
    private Long uoExpireTime;
    /**
     * 帐号状态
     */
    private Integer uoStatus;

    public String getUoSessionId() {
        return uoSessionId;
    }

    public void setUoSessionId(String uoSessionId) {
        this.uoSessionId = uoSessionId;
    }

    public Long getUoUid() {
        return uoUid;
    }

    public void setUoUid(Long uoUid) {
        this.uoUid = uoUid;
    }

    public String getUoIp() {
        return uoIp;
    }

    public void setUoIp(String uoIp) {
        this.uoIp = uoIp;
    }

    public String getUoBrowser() {
        return uoBrowser;
    }

    public void setUoBrowser(String uoBrowser) {
        this.uoBrowser = uoBrowser;
    }

    public String getUoOs() {
        return uoOs;
    }

    public void setUoOs(String uoOs) {
        this.uoOs = uoOs;
    }

    public String getUoStartDate() {
        return uoStartDate;
    }

    public void setUoStartDate(String uoStartDate) {
        this.uoStartDate = uoStartDate;
    }

    public String getUoLastDate() {
        return uoLastDate;
    }

    public void setUoLastDate(String uoLastDate) {
        this.uoLastDate = uoLastDate;
    }

    public Long getUoExpireTime() {
        return uoExpireTime;
    }

    public void setUoExpireTime(Long uoExpireTime) {
        this.uoExpireTime = uoExpireTime;
    }

    public Integer getUoStatus() {
        return uoStatus;
    }

    public void setUoStatus(Integer uoStatus) {
        this.uoStatus = uoStatus;
    }

    @Override
    public String toString() {
        return "\nUserOnlineModel{" +
                "uoId='" + uoSessionId + '\'' +
                ", uoUid='" + uoUid + '\'' +
                ", uoIp='" + uoIp + '\'' +
                ", uoBrowser='" + uoBrowser + '\'' +
                ", uoOs='" + uoOs + '\'' +
                ", uoCreateDate='" + uoStartDate + '\'' +
                ", uoLastDate='" + uoLastDate + '\'' +
                ", uoExpireTime=" + uoExpireTime +
                ", uoStatus=" + uoStatus +
                '}';
    }
}
