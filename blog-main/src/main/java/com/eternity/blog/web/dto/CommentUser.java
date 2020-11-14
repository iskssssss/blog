package com.eternity.blog.web.dto;

import com.eternity.blog.system.domain.blog.CommentsModel;
import com.eternity.blog.system.domain.blog.CommentsUserModel;

/**
 * @Description
 * @Author eternity
 * @Date 2020/5/6 14:46
 */
public class CommentUser {
    private CommentsModel commentInfo;

    private CommentsUserModel sendUser;
    private CommentsUserModel replyUser;

    public CommentsModel getCommentInfo() {
        return commentInfo;
    }

    public void setCommentInfo(CommentsModel commentInfo) {
        this.commentInfo = commentInfo;
    }

    public CommentsUserModel getSendUser() {
        return sendUser;
    }

    public void setSendUser(CommentsUserModel sendUser) {
        this.sendUser = sendUser;
    }

    public CommentsUserModel getReplyUser() {
        return replyUser;
    }

    public void setReplyUser(CommentsUserModel replyUser) {
        this.replyUser = replyUser;
    }

    @Override
    public String toString() {
        return "CommentUser{" +
                "commentsModel=" + commentInfo +
                ", sendUser=" + sendUser +
                ", replyUser=" + replyUser +
                '}';
    }
}
