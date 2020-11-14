package com.eternity.blog.system.service;

import com.eternity.blog.system.domain.blog.ArticleBasicModel;
import com.eternity.blog.system.domain.blog.ArticleDetailedModel;
import com.eternity.blog.system.domain.blog.LabelModel;
import com.eternity.blog.system.domain.blog.SortModel;

import java.util.List;

/**
 * @Description 博客管理 业务层
 * @Author eternity
 * @Date 2020/4/24 16:41
 */
public interface IBlogService {

    /**
     * 添加博文分类
     *
     * @param aId 博文编号
     * @param sId 分类编号
     * @return 结果
     */
   int insertBlogSortInfo(Long aId, Long sId);

    /**
     * 添加博文标签
     *
     * @param aId 博文编号
     * @param lId 标签编号
     * @return 结果
     */
   int insertBlogLabelInfo(Long aId, Long lId);

    /**
     * 添加博客基本信息
     *
     * @param basicModel 博客基本模型
     * @return 结果
     */
   int insertBlogBasicInfo(ArticleBasicModel basicModel);

    /**
     * 添加博客详细信息
     *
     * @param detailedModel 博客详细模型
     * @return 结果
     */
   int insertBlogDetailedInfo(ArticleDetailedModel detailedModel);

    /**
     * 删除博客基本信息(博客编号)
     *
     * @param aid 博客编号
     * @return 结果
     */
   int deleteBlogBasicInfoByAid(Long aid);

    /**
     * 删除博客分类信息(博客编号)
     *
     * @param aid 博客编号
     * @return 结果
     */
   int deleteAllBlogSortInfo(Long aid);

    /**
     * 删除博客标签信息(博客编号)
     *
     * @param aid 博客编号
     * @return 结果
     */
   int deleteAllBlogLabelInfo(Long aid);

    /**
     * 删除博客分类信息(博客编号)
     *
     * @param aId 博客编号
     * @param sId 分类编号
     * @return 结果
     */
   int deleteBlogSortInfo(Long aId, Long sId);

    /**
     * 删除博客标签信息(博客编号)
     *
     * @param aId 博客编号
     * @param lId 标签编号
     * @return 结果
     */
   int deleteBlogLabelInfo(Long aId, Long lId);

    /**
     * 删除博客详细信息(博客编号)
     *
     * @param aid 博客编号
     * @return 结果
     */
   int deleteBlogDetailedInfoByAid(Long aid);

    /**
     * 修改博客基本信息
     *
     * @param basicModel 博客基本模型
     * @return 结果
     */
   int updateBlogBasicInfo(ArticleBasicModel basicModel);

    /**
     * 修改博客详细信息
     *
     * @param detailedModel 博客详细模型
     * @return 结果
     */
   int updateBlogDetailedInfo(ArticleDetailedModel detailedModel);

    /**
     * 增加博文浏览量
     * @param aid 博文编号
     * @return 结果
     */
   int increaseBlogViewsByAid(Long aid);

    /**
     * 删除博客分类信息(博客编号)
     *
     * @param aid 博客编号
     * @return 分类列表
     */
   List<SortModel> selectBlogSortInfo(Long aid);

    /**
     * 删除博客标签信息(博客编号)
     *
     * @param aid 博客编号
     * @return 标签列表
     */
   List<LabelModel> selectBlogLabelInfo(Long aid);

    /**
     * 查询博客信息(博客编号)
     *
     * @param aid 博客编号
     * @return 博客信息
     */
   ArticleBasicModel selectBlogInfoByAid(Long aid);

    /**
     * 查询管理界面展示信息
     *
     * @param page  页数
     * @param limit 每页数量
     * @return 分类列表
     */
   List<ArticleBasicModel> selectManagementShowInfo(int page, int limit);

    /**
     * 查询公共界面展示信息
     *
     * @param page  页数
     * @param limit 每页数量
     * @return 分类列表
     */
   List<ArticleBasicModel> selectPublicShowInfo(int page, int limit);

    /**
     * 获取博客数量
     *
     * @return 博客数量
     */
   Long selectAllCount();

    /**
     * 校验博客编号是否唯一
     *
     * @param aid 博客编号
     * @return 结果
     */
   int checkBlogByAid(Long aid);
}