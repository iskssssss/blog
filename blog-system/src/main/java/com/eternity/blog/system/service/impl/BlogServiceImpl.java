package com.eternity.blog.system.service.impl;

import com.eternity.blog.system.domain.blog.ArticleBasicModel;
import com.eternity.blog.system.domain.blog.ArticleDetailedModel;
import com.eternity.blog.system.domain.blog.LabelModel;
import com.eternity.blog.system.domain.blog.SortModel;
import com.eternity.blog.system.mapper.IBlogMapper;
import com.eternity.blog.system.service.IBlogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author eternity
 * @Date 2020/4/24 16:48
 */
@Service
public class BlogServiceImpl implements IBlogService {
    private static final Logger log = LoggerFactory.getLogger(BlogServiceImpl.class);
    @Autowired
    IBlogMapper blogMapper;

    @Override
    public int insertBlogSortInfo(Long aId, Long sId) {
        log.info(String.format("添加博文分类信息. aid：%d sId：%d", aId, sId));
        return blogMapper.insertBlogSortInfo(aId, sId);
    }

    @Override
    public int insertBlogLabelInfo(Long aId, Long lId) {
        log.info(String.format("添加博文标签信息. aid：%d sId：%d", aId, lId));
        return blogMapper.insertBlogLabelInfo(aId, lId);
    }

    @Override
    public int insertBlogBasicInfo(ArticleBasicModel basicModel) {
        log.info(String.format("添加博客基本信息. aid：%d", basicModel.getAbAid()));
        return blogMapper.insertBlogBasicInfo(basicModel);
    }

    @Override
    public int insertBlogDetailedInfo(ArticleDetailedModel detailedModel) {
        log.info(String.format("添加博客详细信息. aid：%d", detailedModel.getAdAid()));
        return blogMapper.insertBlogDetailedInfo(detailedModel);
    }

    @Override
    public int deleteBlogBasicInfoByAid(Long aid) {
        log.info(String.format("删除博客基本信息. aid：%d", aid));
        return blogMapper.deleteBlogBasicInfoByAid(aid);
    }

    @Override
    public int deleteAllBlogSortInfo(Long aid) {
        log.info(String.format("删除博客分类信息. aid：%d", aid));
        return blogMapper.deleteAllBlogSortInfo(aid);
    }

    @Override
    public int deleteAllBlogLabelInfo(Long aid) {
        log.info(String.format("删除博客标签信息. aid：%d", aid));
        return blogMapper.deleteAllBlogLabelInfo(aid);
    }

    @Override
    public int deleteBlogSortInfo(Long aId, Long sId) {
        log.info(String.format("删除博客分类信息. aid：%d", aId));
        return blogMapper.deleteBlogSortInfo(aId, sId);
    }

    @Override
    public int deleteBlogLabelInfo(Long aId, Long lId) {
        log.info(String.format("删除博客标签信息. aid：%d", aId));
        return blogMapper.deleteBlogLabelInfo(aId, lId);
    }

    @Override
    public int deleteBlogDetailedInfoByAid(Long aid) {
        log.info(String.format("删除博客详细信息. aid：%d", aid));
        return blogMapper.deleteBlogDetailedInfoByAid(aid);
    }

    @Override
    public int updateBlogBasicInfo(ArticleBasicModel basicModel) {
        log.info(String.format("修改博客基本信息. aid：%d", basicModel.getAbAid()));
        return blogMapper.updateBlogBasicInfo(basicModel);
    }

    @Override
    public int updateBlogDetailedInfo(ArticleDetailedModel detailedModel) {
        log.info(String.format("修改博客详细信息. aid：%d", detailedModel.getAdAid()));
        return blogMapper.updateBlogDetailedInfo(detailedModel);
    }

    @Override
    public int increaseBlogViewsByAid(Long aid) {
        return blogMapper.increaseBlogViewsByAid(aid);
    }

    @Override
    public List<SortModel> selectBlogSortInfo(Long aid) {
        log.info(String.format("查询博客分类信息. aid：%d", aid));
        return blogMapper.selectBlogSortInfo(aid);
    }

    @Override
    public List<LabelModel> selectBlogLabelInfo(Long aid) {
        log.info(String.format("查询博客标签信息. aid：%d", aid));
        return blogMapper.selectBlogLabelInfo(aid);
    }

    @Override
    public ArticleBasicModel selectBlogInfoByAid(Long aid) {
        log.info(String.format("查询博客信息. aid：%d", aid));
        return blogMapper.selectBlogInfoByAid(aid);
    }

    @Override
    public List<ArticleBasicModel> selectManagementShowInfo(int page, int limit) {
        log.info(String.format("分页查询管理界面博文信息. 当前页数：%d 查询数量：%d", page, limit));
        return blogMapper.selectManagementShowInfo(page, limit);
    }

    @Override
    public List<ArticleBasicModel> selectPublicShowInfo(int page, int limit) {
        log.info(String.format("分页查询公共界面博文信息. 当前页数：%d 查询数量：%d", page, limit));
        return blogMapper.selectPublicShowInfo(page, limit);
    }

    @Override
    public Long selectAllCount() {
        log.info("查询所有博客.");
        return blogMapper.selectAllCount();
    }

    @Override
    public int checkBlogByAid(Long aid) {
        log.info(String.format("校验博客编号是否唯一. aid：%d", aid));
        return blogMapper.checkBlogByAid(aid);
    }
}
