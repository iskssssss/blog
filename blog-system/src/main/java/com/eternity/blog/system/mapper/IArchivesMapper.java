package com.eternity.blog.system.mapper;

import com.eternity.blog.system.domain.blog.ArchivesModel;
import com.eternity.blog.system.domain.blog.ArticleBasicModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description
 * @Author eternity
 * @Date 2020/5/19 21:53
 */
@Mapper
@Repository
public interface IArchivesMapper {
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
