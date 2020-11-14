package com.eternity.blog.system.service.impl;

import com.eternity.blog.system.domain.blog.CommentsModel;
import com.eternity.blog.system.domain.blog.CommentsUserModel;
import com.eternity.blog.system.mapper.ICommentsMapper;
import com.eternity.blog.system.service.ICommentsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author eternity
 * @Date 2020/5/6 12:54
 */
@Service
public class CommentsServiceImpl implements ICommentsService {
    private static final Logger log = LoggerFactory.getLogger(CommentsServiceImpl.class);

    @Autowired
    ICommentsMapper iCommentsMapper;

    @Override
    public int insertComment(CommentsModel comment) {
        log.info(String.format("添加评论：%s", comment.toString()));
        return iCommentsMapper.insertComment(comment);
    }

    @Override
    public int insertCommentUser(CommentsUserModel commentUser) {
        log.info(String.format("添加评论用户：%s", commentUser.toString()));
        return iCommentsMapper.insertCommentUser(commentUser);
    }

    @Override
    public int deleteCommentByAid(Long aId) {
        log.info(String.format("删除全部评论. 博文编号：%d", aId));
        return iCommentsMapper.deleteCommentByAid(aId);
    }

    @Override
    public int deleteCommentByCid(Long cId) {
        log.info(String.format("删除评论. 评论编号：%d", cId));
        return iCommentsMapper.deleteCommentByCid(cId);
    }

    @Override
    public List<CommentsModel> selectFatherCommentsByAid(Long aId) {
        log.info(String.format("查询夫评论. 博文编号：%d", aId));
        return iCommentsMapper.selectFatherCommentsByAid(aId);
    }

    @Override
    public List<CommentsModel> selectChildCommentsByAid(Long aId, Long cId) {
        log.info(String.format("查询子评论. 博文编号：%d 父评论编号：%d", aId, cId));
        return iCommentsMapper.selectChildCommentsByAid(aId, cId);
    }

    @Override
    public CommentsUserModel selectCommentsUserByUid(Long uId) {
        log.info(String.format("查询评论用户. 用户编号：%d", uId));
        return iCommentsMapper.selectCommentsUserByUid(uId);
    }

    @Override
    public Long selectCommentCount(Long aId) {
        log.info(String.format("查询评论数量. 博文编号：%d", aId));
        return iCommentsMapper.selectCommentCount(aId);
    }

    @Override
    public List<CommentsModel> selectCommentLimitByAid(Long aId, int page, int limit) {
        log.info(String.format("分页查询评论. 博文编号：%d 当前页数：%d 查询数量：%d", aId, page, limit));
        return iCommentsMapper.selectCommentLimitByAid(aId, page, limit);
    }
}
