package com.eternity.blog.web.dto;

import java.util.List;

/**
 * @Description
 * @Author eternity
 * @Date 2020/5/6 13:32
 */
public class CommentsInfoDTO {
    /**
     * 父评论
     */
    private CommentUser fatherComment;
    /**
     * 子评论列表
     */
    private List<CommentUser> childComments;

    public CommentUser getFatherComment() {
        return fatherComment;
    }

    public void setFatherComment(CommentUser fatherComment) {
        this.fatherComment = fatherComment;
    }

    public List<CommentUser> getChildComments() {
        return childComments;
    }

    public void setChildComments(List<CommentUser> childComments) {
        this.childComments = childComments;
    }

    @Override
    public String toString() {
        return "CommentsInfoDto{" +
                "fatherComment=" + fatherComment +
                ", childComments=" + childComments +
                '}';
    }
}
