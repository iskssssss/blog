package com.eternity.blog.system.domain.blog;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 归档日期模型
 * @Author eternity
 * @Date 2020/5/19 21:54
 */
public class ArchivesModel {
    /**
     * 日期
     */
    private String archivesDate;

    /**
     * 数量
     */
    private Integer archivesCount;

    /**
     * 博文列表
     */
    private List<ArticleBasicModel> articleBasicList;

//    public ArchivesModel() {
//        this.articleBasicList = new ArrayList<>();
//    }

    public List<ArticleBasicModel> getArticleBasicList() {
        return articleBasicList;
    }

    public void setArticleBasicList(List<ArticleBasicModel> articleBasicList) {
        this.articleBasicList = articleBasicList;
    }

    public String getArchivesDate() {
        return archivesDate;
    }

    public void setArchivesDate(String archivesDate) {
        this.archivesDate = archivesDate;
    }

    public Integer getArchivesCount() {
        return archivesCount;
    }

    public void setArchivesCount(Integer archivesCount) {
        this.archivesCount = archivesCount;
    }

    @Override
    public String toString() {
        return "ArchivesModel{" +
                "archivesDate='" + archivesDate + '\'' +
                ", archivesCount=" + archivesCount +
                ", articleBasicList=" + articleBasicList +
                '}';
    }
}
