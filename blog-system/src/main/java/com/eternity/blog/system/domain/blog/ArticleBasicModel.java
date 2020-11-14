package com.eternity.blog.system.domain.blog;

import com.eternity.blog.common.core.domain.model.BaseEntity;
import com.eternity.blog.system.domain.user.UserModel;

/**
 * @Description 博文基本模型
 * @Author eternity
 * @Date 2020/4/23 23:15
 */
public class ArticleBasicModel extends BaseEntity {
    /** 博文编号 */
    private Long abAid;
    /** 博文标题 */
    private String abTitle;
    /** 简介 */
    private String abSynopsis;
    /** 封面图 */
    private String abCoverImage;
    /** 发表用户 */
    private Long abUid;
    /** 是否发布 */
    private Integer abPublish;
    /** 发表时间 */
    private String abPublishDate;
    /** 更新时间 */
    private String abUpdateDate;
    /** 博文详细信息 */
    private ArticleDetailedModel detailedInfo;

    /** 用户信息 */
    private UserModel userInfo;

    public Long getAbAid() {
        return abAid;
    }

    public void setAbAid(Long abAid) {
        this.abAid = abAid;
    }

    public String getAbTitle() {
        return abTitle;
    }

    public void setAbTitle(String abTitle) {
        this.abTitle = abTitle;
    }

    public String getAbSynopsis() {
        return abSynopsis;
    }

    public void setAbSynopsis(String abSynopsis) {
        this.abSynopsis = abSynopsis;
    }

    public String getAbCoverImage() {
        return abCoverImage;
    }

    public void setAbCoverImage(String abCoverImage) {
        this.abCoverImage = abCoverImage;
    }

    public Long getAbUid() {
        return abUid;
    }

    public void setAbUid(Long abUid) {
        this.abUid = abUid;
    }

    public Integer getAbPublish() {
        return abPublish;
    }

    public void setAbPublish(Integer abPublish) {
        this.abPublish = abPublish;
    }

    public String getAbPublishDate() {
        return abPublishDate;
    }

    public void setAbPublishDate(String abPublishDate) {
        this.abPublishDate = abPublishDate;
    }

    public String getAbUpdateDate() {
        return abUpdateDate;
    }

    public void setAbUpdateDate(String abUpdateDate) {
        this.abUpdateDate = abUpdateDate;
    }

    public ArticleDetailedModel getDetailedInfo() {
        return detailedInfo;
    }

    public void setDetailedInfo(ArticleDetailedModel detailedInfo) {
        this.detailedInfo = detailedInfo;
    }

    public UserModel getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserModel userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public String toString() {
        return "ArticleBasicModel{" +
                "abAid=" + abAid +
                ", abTitle='" + abTitle + '\'' +
                ", abSynopsis='" + abSynopsis + '\'' +
                ", abCoverImage='" + abCoverImage + '\'' +
                ", abUid=" + abUid +
                ", abPublish=" + abPublish +
                ", abPublishDate='" + abPublishDate + '\'' +
                ", abUpdateDate='" + abUpdateDate + '\'' +
                ", detailedInfo=" + detailedInfo +
                ", userInfo=" + userInfo +
                '}';
    }
}
