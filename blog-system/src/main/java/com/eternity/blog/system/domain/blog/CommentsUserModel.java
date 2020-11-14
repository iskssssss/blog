package com.eternity.blog.system.domain.blog;

import com.eternity.blog.common.core.domain.model.BaseEntity;

/**
 * @Description 评论用户实体
 * @Author eternity
 * @Date 2020/5/6 11:40
 */
public class CommentsUserModel extends BaseEntity {
    /**
     * 访客用户编号
     */
    private Long cuUid;
    /**
     * 访客昵称
     */
    private String cuUsername;
    /**
     * 访客邮箱
     */
    private String cuEmail;
    /**
     * 访客网址
     */
    private String cuWebsite;

    public Long getCuUid() {
        return cuUid;
    }

    public void setCuUid(Long cuUid) {
        this.cuUid = cuUid;
    }

    public String getCuUsername() {
        return cuUsername;
    }

    public void setCuUsername(String cuUsername) {
        this.cuUsername = cuUsername;
    }

    public String getCuEmail() {
        return cuEmail;
    }

    public void setCuEmail(String cuEmail) {
        this.cuEmail = cuEmail;
    }

    public String getCuWebsite() {
        return cuWebsite;
    }

    public void setCuWebsite(String cuWebsite) {
        this.cuWebsite = cuWebsite;
    }

    @Override
    public String toString() {
        return "CommentsUserModel{" +
                "cuUid=" + cuUid +
                ", cuUsername='" + cuUsername + '\'' +
                ", cuEmail='" + cuEmail + '\'' +
                ", cuWebsite='" + cuWebsite + '\'' +
                '}';
    }
}
