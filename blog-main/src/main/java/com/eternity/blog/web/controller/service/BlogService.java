package com.eternity.blog.web.controller.service;

import com.eternity.blog.common.random.RandomDevice;
import com.eternity.blog.common.utils.BooleanUtils;
import com.eternity.blog.common.utils.DateUtils;
import com.eternity.blog.common.utils.JsonUtils;
import com.eternity.blog.common.utils.StringUtils;
import com.eternity.blog.system.domain.blog.LabelModel;
import com.eternity.blog.web.dto.BlogInfoDTO;
import com.eternity.blog.web.dto.TableDTO;
import com.eternity.blog.framework.utill.ShiroUtils;
import com.eternity.blog.system.domain.blog.ArticleBasicModel;
import com.eternity.blog.system.domain.blog.SortModel;
import com.eternity.blog.system.domain.user.UserModel;
import com.eternity.blog.system.service.IBlogService;
import com.eternity.blog.system.service.ICommentsService;
import com.eternity.blog.system.service.ILabelService;
import com.eternity.blog.system.service.ISortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import java.util.List;

/**
 * @Description
 * @Author eternity
 * @Date 2020/4/28 14:36
 */
@Component
public class BlogService {

    @Autowired
    IBlogService blogService;

    @Autowired
    ILabelService labelService;

    @Autowired
    ISortService sortService;

    @Autowired
    ICommentsService commentsService;

    public String getBlogList(int limit, int page) {
        List<ArticleBasicModel> basicModelList = blogService.selectManagementShowInfo((page - 1) * limit, limit);
        Long size = blogService.selectAllCount();
        TableDTO tableDTO = new TableDTO();
        tableDTO.setCode(0);
        tableDTO.setMsg("");
        tableDTO.setCount(size);
        tableDTO.setData(basicModelList);
        return JsonUtils.toJsonString(tableDTO);
    }

    public String insertBlog(BlogInfoDTO blogInfoDto) {
        UserModel user = ShiroUtils.getUserModel();
        blogInfoDto.getBlog().setAbUid(49263L);
        if (StringUtils.isNotNull(user)) {
            blogInfoDto.getBlog().setAbUid(user.getuId());
        }
        blogInfoDto.getBlog().setAbAid(getAid());
        blogInfoDto.getBlog().getDetailedInfo().setAdAid(blogInfoDto.getBlog().getAbAid());
        blogInfoDto.getBlog().setAbUpdateDate(DateUtils.getDateTime());
        blogInfoDto.getBlog().setAbPublishDate(blogInfoDto.getBlog().getAbUpdateDate());
        blogInfoDto.getBlog().getDetailedInfo().setAdLikeCount(0L);
        blogInfoDto.getBlog().getDetailedInfo().setAdViews(0L);
//        blogInfoDto.getBlog().getDetailedInfo().setAdCommentCount(0L);
        blogInfoDto.getBlog().getDetailedInfo().setAdTop(0);
        if (!BooleanUtils.toBoolean(blogService.insertBlogBasicInfo(blogInfoDto.getBlog()))) {
            return "发布失败,请重试.";
        }
        blogService.insertBlogDetailedInfo(blogInfoDto.getBlog().getDetailedInfo());

        insertSortInfo(blogInfoDto.getBlog().getAbAid(), blogInfoDto.getInsertSorts());
        insertLabelInfo(blogInfoDto.getBlog().getAbAid(), blogInfoDto.getInsertLabels());
        return null;
    }

    public String modifyBlog(BlogInfoDTO blogInfoDto) {
        UserModel user = ShiroUtils.getUserModel();
        blogInfoDto.getBlog().setAbUid(49263L);
        if (StringUtils.isNotNull(user)) {
            blogInfoDto.getBlog().setAbUid(user.getuId());
        }
        blogInfoDto.getBlog().setAbUpdateDate(DateUtils.getDateTime());
        if (!BooleanUtils.toBoolean(blogService.updateBlogBasicInfo(blogInfoDto.getBlog()))) {
            return "保存失败.";
        }
        if (StringUtils.isNotNull(blogInfoDto.getBlog().getDetailedInfo())) {
            blogInfoDto.getBlog().getDetailedInfo().setAdAid(blogInfoDto.getBlog().getAbAid());
            blogService.updateBlogDetailedInfo(blogInfoDto.getBlog().getDetailedInfo());
        }
        insertSortInfo(blogInfoDto.getBlog().getAbAid(), blogInfoDto.getInsertSorts());
        deleteSortInfo(blogInfoDto.getBlog().getAbAid(), blogInfoDto.getDeleteSorts());

        insertLabelInfo(blogInfoDto.getBlog().getAbAid(), blogInfoDto.getInsertLabels());
        deleteLabelInfo(blogInfoDto.getBlog().getAbAid(), blogInfoDto.getDeleteLabels());
        return null;
    }

    /**
     * 添加博文分类
     *
     * @param aId   博文编号
     * @param sorts 分类列表
     */
    public void insertSortInfo(Long aId, List<SortModel> sorts) {
        if (StringUtils.isNull(sorts)) {
            return;
        }
        sorts.forEach(sort -> blogService.insertBlogSortInfo(aId, sort.getsSid()));
    }

    /**
     * 删除博文分类
     *
     * @param aId   博文编号
     * @param sorts 分类列表
     */
    public void deleteSortInfo(Long aId, List<SortModel> sorts) {
        if (StringUtils.isNull(sorts)) {
            return;
        }
        sorts.forEach(sort -> blogService.deleteBlogSortInfo(aId, sort.getsSid()));
    }

    /**
     * 添加博文标签
     *
     * @param aId    博文编号
     * @param labelList 标签列表
     */
    public void insertLabelInfo(Long aId, List<LabelModel> labelList) {
        if (StringUtils.isNull(labelList)) {
            return;
        }
        labelList.forEach(label -> blogService.insertBlogLabelInfo(aId, label.getlLid()));
    }

    /**
     * 删除博文标签
     *
     * @param aId    博文编号
     * @param labelList 标签列表
     */
    public void deleteLabelInfo(Long aId, List<LabelModel> labelList) {
        if (StringUtils.isNull(labelList)) {
            return;
        }
        labelList.forEach(label -> blogService.deleteBlogLabelInfo(aId, label.getlLid()));
    }

    public String deleteBlog(Long abAid) {
        if (!BooleanUtils.toBoolean(blogService.checkBlogByAid(abAid))) {
            return "删除失败,该博客不存在.";
        }
        commentsService.deleteCommentByAid(abAid);
        blogService.deleteAllBlogSortInfo(abAid);
        blogService.deleteAllBlogLabelInfo(abAid);
        blogService.deleteBlogDetailedInfoByAid(abAid);
        blogService.deleteBlogBasicInfoByAid(abAid);
        return "删除成功.";
    }

    /**
     * 修改博客返回数据
     */
    public void modifyBlog(Long aId, ModelMap modelMap) {
        ArticleBasicModel blog = blogService.selectBlogInfoByAid(aId);
        List<SortModel> blogSortList = blogService.selectBlogSortInfo(aId);
        List<LabelModel> blogLabelModelList = blogService.selectBlogLabelInfo(aId);
        modelMap.put("blog", blog);
        modelMap.put("blogSortInfo", blogSortList.get(0));
        modelMap.put("blogLabelInfoList", blogLabelModelList);
        setLabelAndSortNameList(modelMap);
    }

    /**
     * 新增博客返回数据
     */
    public void getLabelNameList(ModelMap modelMap) {
        setLabelAndSortNameList(modelMap);
    }

    private void setLabelAndSortNameList(ModelMap modelMap) {
        modelMap.put("labelList", labelService.selectAll());
        modelMap.put("sortList", sortService.selectAll());
    }

    /**
     * 获取bId
     *
     * @return bId
     */
    private Long getAid() {
        long aId = RandomDevice.randomId();
        if (BooleanUtils.toBoolean(blogService.checkBlogByAid(aId))) {
            getAid();
        }
        return aId;
    }
}
