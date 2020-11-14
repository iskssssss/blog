package com.eternity.blog.system.service;

import com.eternity.blog.system.domain.blog.ArchivesModel;
import com.eternity.blog.system.domain.blog.ArticleBasicModel;

import java.util.List;

/**
 * @Description
 * @Author eternity
 * @Date 2020/5/19 22:06
 */
public interface IArchivesService {
    /**
     * 获取博客归档 日期
     *
     * @return 归档日期列表
     */
    List<ArchivesModel> selectBlogArchivesByDate();

    /**
     * 获取博客信息 日期
     *
     * @param date 日期
     * @return 博文信息列表
     */
    List<ArticleBasicModel> selectBlogInfoListByDate(String date);
}
