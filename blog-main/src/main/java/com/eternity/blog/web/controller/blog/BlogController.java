package com.eternity.blog.web.controller.blog;

import com.eternity.blog.common.constants.RedisKeyFormatConstants;
import com.eternity.blog.common.core.controller.BaseController;
import com.eternity.blog.common.core.domain.web.BaseAjaxResult;
import com.eternity.blog.common.utils.*;
import com.eternity.blog.system.domain.blog.*;
import com.eternity.blog.web.dto.BlogInfoDTO;
import com.eternity.blog.web.dto.CommentUser;
import com.eternity.blog.web.dto.CommentsInfoDTO;
import com.eternity.blog.web.controller.service.BlogService;
import com.eternity.blog.system.service.IBlogService;
import com.eternity.blog.system.service.ICommentsService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 博客请求控制器
 * @Author eternity
 * @Date 2020/4/21 20:34
 */
@Controller
public class BlogController extends BaseController {
    private final String INPUT = "system/blog/input";


    public static String blogListKey = "BLOG-LIST";

    @Autowired
    BlogService blogService;

    @Autowired
    IBlogService iBlogService;

    @Autowired
    ICommentsService iCommentsService;

    @Autowired
    private RedisUtils redisUtil;

    @GetMapping("/category/{page}")
    public String categoryLimit(@PathVariable("page") int page, ModelMap modelMap) {
        getCategoryList((page - 1) * 9, 9, modelMap);
        return "category";
    }

    @GetMapping("/category")
    public String category(ModelMap modelMap) {
        getCategoryList(0, 9, modelMap);
        return "category";
    }

    public void getCategoryList(int page, int limit, ModelMap modelMap) {
        List<ArticleBasicModel> blogList = iBlogService.selectPublicShowInfo(page, limit);
        List<BlogInfoDTO> blogInfoDtos = new ArrayList<>();
        blogList.forEach(blog -> {
            blog.getDetailedInfo().setAdCommentCount(iCommentsService.selectCommentCount(blog.getAbAid()));
            BlogInfoDTO blogInfoDto = new BlogInfoDTO();
            blogInfoDto.setBlog(blog);
            List<SortModel> sortModels = iBlogService.selectBlogSortInfo(blog.getAbAid());
            blogInfoDto.setInsertSorts(sortModels);
            List<LabelModel> label = iBlogService.selectBlogLabelInfo(blog.getAbAid());
            blogInfoDto.setInsertLabels(label);
            blogInfoDtos.add(blogInfoDto);
        });
        Long blogSize = iBlogService.selectAllCount();
        modelMap.put("blogList", blogInfoDtos);
        modelMap.put("blogSize", blogSize);
    }

    /**
     * 修改博文
     *
     * @param aId      博文编号
     * @param modelMap 博文信息
     * @return 修改界面
     */
    @GetMapping("/system/blog/modify/{aId}")
    public String modifyBlog(@PathVariable("aId") Long aId, ModelMap modelMap) {
        blogService.modifyBlog(aId, modelMap);
        return INPUT;
    }

    @GetMapping("/blog/{aId}")
    public String blog(@PathVariable("aId") Long aId, ModelMap modelMap) {
        // Redis对访问过的博文进行缓存
        ModelMap t = (ModelMap) redisUtil.hashGet(blogListKey, aId.toString());
        if (StringUtils.isNotNull(t)) {
            ArticleBasicModel blog = (ArticleBasicModel) t.get("blog");
            blog.getDetailedInfo().setAdViews(blog.getDetailedInfo().getAdViews() + 1);
            modelMap.put("blog", blog);
            modelMap.put("comments", t.get("comments"));
            modelMap.put("commentCount", t.get("commentCount"));
            redisUtil.hashSet(blogListKey, blog.getAbAid().toString(), modelMap, 3600);
            return "article";
        }
        if (!BooleanUtils.toBoolean(iBlogService.checkBlogByAid(aId))) {
            // 博文不存在时返回404界面
            return "error/404";
        }

        // 增加博文浏览量
        iBlogService.increaseBlogViewsByAid(aId);
        ArticleBasicModel blog = iBlogService.selectBlogInfoByAid(aId);
        String html = MarkDownUtils.toHtml(blog.getDetailedInfo().getAdContent());
        blog.getDetailedInfo().setAdContent(html);


        List<CommentsInfoDTO> commentsInfoDtos = new ArrayList<>();
        List<CommentsModel> commentsModels = iCommentsService.selectFatherCommentsByAid(aId);
        commentsModels.forEach(fatherComment -> {
            CommentsInfoDTO commentsInfoDto = new CommentsInfoDTO();
            // 设置夫评论信息
            CommentUser commentUser = new CommentUser();
            commentUser.setCommentInfo(fatherComment);
            commentUser.setSendUser(iCommentsService.selectCommentsUserByUid(fatherComment.getcUid()));
            commentsInfoDto.setFatherComment(commentUser);
            // 设置子评论信息
            List<CommentsModel> childComments = iCommentsService.selectChildCommentsByAid(fatherComment.getcAid(), fatherComment.getcCid());
            List<CommentUser> childComments2 = new ArrayList<>();
            childComments.forEach(childComment -> {
                CommentUser commentUser2 = new CommentUser();
                commentUser2.setCommentInfo(childComment);
                commentUser2.setSendUser(iCommentsService.selectCommentsUserByUid(childComment.getcUid()));
                commentUser2.setReplyUser(iCommentsService.selectCommentsUserByUid(childComment.getcReplyUid()));
                childComments2.add(commentUser2);
            });
            commentsInfoDto.setChildComments(childComments2);
            commentsInfoDtos.add(commentsInfoDto);
        });

        modelMap.put("blog", blog);
        modelMap.put("comments", commentsInfoDtos);
        modelMap.put("commentCount", iCommentsService.selectCommentCount(aId));

        // 将博文添加至Redis中 并设置过期时间
        redisUtil.hashSet(blogListKey, blog.getAbAid().toString(), modelMap, 3600);
        return "article";
    }

    @ResponseBody
    @GetMapping("/blog/getBlogList")
    public String getBlogList(@RequestParam int limit, @RequestParam int page) {
        return blogService.getBlogList(limit, page);
    }

    @ResponseBody
    @GetMapping("/blog/deleteBlog")
    public BaseAjaxResult deleteBlog(@RequestParam Long abAid) {
        return success(blogService.deleteBlog(abAid));
    }

    @ResponseBody
    @PostMapping("/blog/insertBlog")
    public BaseAjaxResult insertBlog(@RequestBody BlogInfoDTO blogInfoDto) {
        String message = blogService.insertBlog(blogInfoDto);
        if (StringUtils.isNotNull(message)) {
            error(message);
        }
        return success("发布成功.");
    }

    @ResponseBody
    @PostMapping("/blog/modifyBlog")
    public BaseAjaxResult modifyBlog(@RequestBody BlogInfoDTO blogInfoDto) {
        if (StringUtils.isNull(blogInfoDto.getBlog())) {
            return error("错误");
        }
        String message = blogService.modifyBlog(blogInfoDto);
        if (StringUtils.isNotNull(message)) {
            error(message);
        }
        return success("保存成功.");
    }

    /**
     * 增加博文页面请求
     *
     * @param modelMap 分类和标签信息
     * @return 增加界面
     */
    @GetMapping("/system/blog/input")
    public String getLabelNameList(ModelMap modelMap) {
        blogService.getLabelNameList(modelMap);
        return INPUT;
    }

    @ResponseBody
    @PostMapping("/common/upload")
    public BaseAjaxResult uploadFile(@RequestParam("avatarfile") MultipartFile file) throws Exception {
        String fileName = upload("/blog/image", file);
        return success("上传成功！", fileName);
    }

    @ResponseBody
    @PostMapping("/common/uploadEditormd")
    public BaseAjaxResult uploadFileEditormd(@RequestParam("editormd-image-file") MultipartFile file) throws Exception {
        return uploadFile(file);
    }

    private String upload(String baseDir, MultipartFile file) throws IOException {
        String extension = getExtension(file);
        String fileName = file.getOriginalFilename();
        fileName = String.format("%s.%s", extractFilename(fileName), extension);
        File desc = new File(baseDir + File.separator + fileName);
        if (!desc.getParentFile().exists()) {
            desc.getParentFile().mkdirs();
        }
        if (!desc.exists()) {
            desc.createNewFile();
        }
        file.transferTo(desc);
        return "/image/" + fileName;
    }


    /**
     * 编码文件名
     *
     * @param fileName 原始文件名
     * @return 编码后的文件名
     */
    private static final String extractFilename(String fileName) {
        fileName = EncryptionUtils.toMd5Hash(fileName, String.valueOf(System.nanoTime()));
        return fileName;
    }

    /**
     * 获取文件后缀
     *
     * @param file 文件
     * @return 后缀
     */
    String getExtension(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (StringUtils.isEmpty(extension)) {
            extension = FilenameUtils.getExtension(file.getContentType());
        }
        return extension;
    }
}
