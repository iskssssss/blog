package com.eternity.blog.web.dto;

import com.eternity.blog.system.domain.blog.ArticleBasicModel;
import com.eternity.blog.system.domain.blog.LabelModel;
import com.eternity.blog.system.domain.blog.SortModel;

import java.util.List;

/**
 * @Description
 * @Author eternity
 * @Date 2020/4/27 10:47
 */
public class BlogInfoDTO {
    /**
     * 博文信息
     */
    private ArticleBasicModel blog;

    /**
     * 待添加的分类信息
     */
    private List<SortModel> insertSorts;
    /**
     * 待删除的分类信息
     */
    private List<SortModel> deleteSorts;

    /**
     * 待添加的标签信息
     */
    private List<LabelModel> insertLabels;
    /**
     * 待删除的标签信息
     */
    private List<LabelModel> deleteLabels;

    public ArticleBasicModel getBlog() {
        return blog;
    }

    public void setBlog(ArticleBasicModel blog) {
        this.blog = blog;
    }

    public List<SortModel> getInsertSorts() {
        return insertSorts;
    }

    public void setInsertSorts(List<SortModel> insertSorts) {
        this.insertSorts = insertSorts;
    }

    public List<SortModel> getDeleteSorts() {
        return deleteSorts;
    }

    public void setDeleteSorts(List<SortModel> deleteSorts) {
        this.deleteSorts = deleteSorts;
    }

    public List<LabelModel> getInsertLabels() {
        return insertLabels;
    }

    public void setInsertLabels(List<LabelModel> insertLabels) {
        this.insertLabels = insertLabels;
    }

    public List<LabelModel> getDeleteLabels() {
        return deleteLabels;
    }

    public void setDeleteLabels(List<LabelModel> deleteLabels) {
        this.deleteLabels = deleteLabels;
    }

    @Override
    public String toString() {
        return "BlogInfoDto{" +
                "blog=" + blog +
                ", insertSorts=" + insertSorts +
                ", deleteSorts=" + deleteSorts +
                ", insertLabels=" + insertLabels +
                ", deleteLabels=" + deleteLabels +
                '}';
    }
}
