package com.eternity.blog.system.service;

import com.eternity.blog.system.domain.blog.CommentsModel;
import com.eternity.blog.system.domain.blog.CommentsUserModel;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author eternity
 * @Date 2020/5/6 12:53
 */
public interface ICommentsService  {

    /**
     * 添加评论信息
     *
     * @param comment 评论信息
     * @return 结果
     */
   int insertComment(CommentsModel comment);

    /**
     * 添加评论用户信息
     *
     * @param commentUser 评论用户信息
     * @return 结果
     */
   int insertCommentUser(CommentsUserModel commentUser);

    /**
     * 删除评论(博客编号)
     * @param aId  博客编号
     * @return 结果
     */
   int deleteCommentByAid(Long aId);

    /**
     * 删除评论(评论编号)
     * @param cId  评论编号
     * @return 结果
     */
   int deleteCommentByCid(Long cId);

    /**
     * 查询父评论信息
     *
     * @param aId 博文编号
     * @return 评论列表
     */
   List<CommentsModel> selectFatherCommentsByAid(Long aId);

    /**
     * 查询子评论信息
     *
     * @param aId 博文编号
     * @param cId 评论编号
     * @return 评论列表
     */
   List<CommentsModel> selectChildCommentsByAid(Long aId, Long cId);


    /**
     * 查询评论用户信息
     *
     * @param uId 用户编号
     * @return 用户信息
     */
   CommentsUserModel selectCommentsUserByUid(Long uId);

    /**
     * 查询评论数量
     *
     * @param aId 博文编号
     * @return 评论数量
     */
   Long selectCommentCount(Long aId);

    /**
     * 查询评论信息(按博文编号分页查询)
     *
     * @param aId   博文编号
     * @param page  页数
     * @param limit 限制
     * @return 评论列表
     */
   List<CommentsModel> selectCommentLimitByAid(Long aId, int page, int limit);
}