package com.eternity.blog.system.domain.blog;

import com.eternity.blog.common.core.domain.model.BaseEntity;

/**
 * @Description 博文详细实体
 * @Author eternity
 * @Date 2020/4/23 23:22
 */
public class ArticleDetailedModel extends BaseEntity {
    /**
     * 博文编号
     */
    private Long adAid;
    /**
     * 博文内容
     */
    private String adContent;
    /**
     * 文章类型
     */
    private Integer adType;
    /**
     * 是否开启评论
     */
    private Integer adOpenComment;
    /**
     * 是否推荐
     */
    private Integer adOpenRecommend;
    /**
     * 点赞数
     */
    private Long adLikeCount;
    /**
     * 浏览数
     */
    private Long adViews;
    /**
     * 是否置顶
     */
    private Integer adTop;

    /**
     * 评论数
     */
    private Long adCommentCount;

    @Override
    public String toString() {
        return "ArticleDetailedModel{" +
                "adAid=" + adAid +
                ", adContent='" + adContent + '\'' +
                ", adType=" + adType +
                ", adOpenComment=" + adOpenComment +
                ", adOpenRecommend=" + adOpenRecommend +
                ", adLikeCount=" + adLikeCount +
                ", adViews=" + adViews +
                ", adCommentCount=" + adCommentCount +
                ", adTop=" + adTop +
                '}';
    }

    public Long getAdAid() {
        return adAid;
    }

    public void setAdAid(Long adAid) {
        this.adAid = adAid;
    }

    public String getAdContent() {
        return adContent;
    }

    public void setAdContent(String adContent) {
        this.adContent = adContent;
    }

    public Integer getAdType() {
        return adType;
    }

    public void setAdType(Integer adType) {
        this.adType = adType;
    }

    public Integer getAdOpenComment() {
        return adOpenComment;
    }

    public void setAdOpenComment(Integer adOpenComment) {
        this.adOpenComment = adOpenComment;
    }

    public Integer getAdOpenRecommend() {
        return adOpenRecommend;
    }

    public void setAdOpenRecommend(Integer adOpenRecommend) {
        this.adOpenRecommend = adOpenRecommend;
    }

    public Long getAdLikeCount() {
        return adLikeCount;
    }

    public void setAdLikeCount(Long adLikeCount) {
        this.adLikeCount = adLikeCount;
    }

    public Long getAdViews() {
        return adViews;
    }

    public void setAdViews(Long adViews) {
        this.adViews = adViews;
    }

    public Long getAdCommentCount() {
        return adCommentCount;
    }

    public void setAdCommentCount(Long adCommentCount) {
        this.adCommentCount = adCommentCount;
    }

    public Integer getAdTop() {
        return adTop;
    }

    public void setAdTop(Integer adTop) {
        this.adTop = adTop;
    }
}
