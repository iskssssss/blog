package com.eternity.blog.system.domain.blog;

import com.eternity.blog.common.core.domain.model.BaseEntity;

/**
 * @Description 用户评论实体
 * @Author eternity
 * @Date 2020/5/6 11:40
 */
public class CommentsModel extends BaseEntity {
    /**
     * 评论编号
     */
    private Long cCid;
    /**
     * 用户编号
     */
    private Long cUid;
    /**
     * 博文编号
     */
    private Long cAid;
    /**
     * 点赞数量
     */
    private Long cLikeCount;
    /**
     * 评论日期
     */
    private String cDate;
    /**
     * 评论内容
     */
    private String cContent;
    /**
     * 夫评论编号
     */
    private Long cFatherCid;
    /**
     * 被回复用户编号
     */
    private Long cReplyUid;

    private CommentsUserModel commentsUser;

    public Long getcCid() {
        return cCid;
    }

    public void setcCid(Long cCid) {
        this.cCid = cCid;
    }

    public Long getcUid() {
        return cUid;
    }

    public void setcUid(Long cUid) {
        this.cUid = cUid;
    }

    public Long getcAid() {
        return cAid;
    }

    public void setcAid(Long cAid) {
        this.cAid = cAid;
    }

    public Long getcLikeCount() {
        return cLikeCount;
    }

    public void setcLikeCount(Long cLikeCount) {
        this.cLikeCount = cLikeCount;
    }

    public String getcDate() {
        return cDate;
    }

    public void setcDate(String cDate) {
        this.cDate = cDate;
    }

    public String getcContent() {
        return cContent;
    }

    public void setcContent(String cContent) {
        this.cContent = cContent;
    }

    public Long getcFatherCid() {
        return cFatherCid;
    }

    public void setcFatherCid(Long cFatherCid) {
        this.cFatherCid = cFatherCid;
    }

    public Long getcReplyUid() {
        return cReplyUid;
    }

    public void setcReplyUid(Long cReplyUid) {
        this.cReplyUid = cReplyUid;
    }

    public CommentsUserModel getCommentsUser() {
        return commentsUser;
    }

    public void setCommentsUser(CommentsUserModel commentsUser) {
        this.commentsUser = commentsUser;
    }

    @Override
    public String toString() {
        return "CommentsModel{" +
                "cCid=" + cCid +
                ", cUid=" + cUid +
                ", cAid=" + cAid +
                ", cLikeCount=" + cLikeCount +
                ", cDate='" + cDate + '\'' +
                ", cContent='" + cContent + '\'' +
                ", cFatherCid=" + cFatherCid +
                ", cReplyUid=" + cReplyUid +
                ", commentsUser=" + commentsUser +
                '}';
    }
}
