package com.eternity.blog.web.controller.blog;

import com.eternity.blog.common.constants.RedisKeyFormatConstants;
import com.eternity.blog.common.core.controller.BaseController;
import com.eternity.blog.common.core.domain.web.BaseAjaxResult;
import com.eternity.blog.common.random.RandomDevice;
import com.eternity.blog.common.utils.DateUtils;
import com.eternity.blog.common.utils.JsonUtils;
import com.eternity.blog.common.utils.RedisUtils;
import com.eternity.blog.common.utils.StringUtils;
import com.eternity.blog.system.domain.blog.ArticleBasicModel;
import com.eternity.blog.web.dto.CommentUser;
import com.eternity.blog.web.dto.CommentsInfoDTO;
import com.eternity.blog.web.dto.TableDTO;
import com.eternity.blog.system.domain.blog.CommentsModel;
import com.eternity.blog.system.service.ICommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 评论请求控制器
 * @Author eternity
 * @Date 2020/5/10 15:36
 */
@Controller
public class CommentController extends BaseController {
    @Autowired
    ICommentsService iCommentsService;

    @Autowired
    private RedisUtils redisUtil;

    @ResponseBody
    @GetMapping("/blog/getCommentList/{aid}")
    public String getCommentList(@PathVariable("aid") Long aid, @RequestParam int limit, @RequestParam int page) {
        TableDTO tableDTO = new TableDTO();
        tableDTO.setCode(0);
        tableDTO.setMsg("");
        tableDTO.setCount(iCommentsService.selectCommentCount(aid));
        tableDTO.setData(iCommentsService.selectCommentLimitByAid(aid, (page - 1) * limit, limit));
        return JsonUtils.toJsonString(tableDTO);
    }

    @ResponseBody
    @PostMapping("/blog/addComment")
    public BaseAjaxResult addComment(@RequestBody CommentUser commentUser) {
        Long cUid = RandomDevice.randomUid();
        commentUser.getCommentInfo().setcDate(DateUtils.getDateTime());
        commentUser.getCommentInfo().setcUid(cUid);
        commentUser.getSendUser().setCuUid(cUid);
//        iCommentsService.insertCommentUser(commentUser.getSendUser());
//        iCommentsService.insertComment(commentUser.getCommentInfo());

        ModelMap t = (ModelMap) redisUtil.hashGet(BlogController.blogListKey, commentUser.getCommentInfo().getcAid().toString());
        if (StringUtils.isNotNull(t)) {
            List<CommentsInfoDTO> blog = (List<CommentsInfoDTO>) t.get("comments");
            if (StringUtils.isNotNull(commentUser.getCommentInfo().getcFatherCid())) {
                for (CommentsInfoDTO commentsInfoDTO : blog) {
                    if (commentsInfoDTO.getFatherComment().getCommentInfo().getcCid().equals(commentUser.getCommentInfo().getcFatherCid())) {
                        commentsInfoDTO.getChildComments().add(commentUser);
                        break;
                    }
                }
            } else {
                CommentsInfoDTO commentsInfoDTO = new CommentsInfoDTO();
                commentsInfoDTO.setFatherComment(commentUser);
                commentsInfoDTO.setChildComments(new ArrayList<>());
                blog.add(commentsInfoDTO);
            }
            redisUtil.hashSet(BlogController.blogListKey, commentUser.getCommentInfo().getcAid().toString(), t, 3600);
        }
        return success("回复成功.");
    }

    @ResponseBody
    @PostMapping("/blog/delCommentList")
    public BaseAjaxResult delCommentList(@RequestBody List<CommentsModel> commentList) {
        commentList.forEach(comment -> {
            iCommentsService.deleteCommentByCid(comment.getcCid());
        });
        return success("");
    }
}
