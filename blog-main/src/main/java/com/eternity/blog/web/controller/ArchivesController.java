package com.eternity.blog.web.controller;

import com.eternity.blog.common.utils.DateUtils;
import com.eternity.blog.system.domain.blog.ArchivesModel;
import com.eternity.blog.system.domain.blog.ArticleBasicModel;
import com.eternity.blog.system.service.IArchivesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 归档页请求控制器
 * @Author eternity
 * @Date 2020/5/20 13:15
 */
@Controller
public class ArchivesController {
    @Autowired
    IArchivesService archivesService;


    /**
     * 将博文按照日期分类
     *
     * @param selectDate 日期
     * @return 结果
     */
    public List<ArchivesModel> blogArchivesByDate(String selectDate) {
        List<ArchivesModel> archivesDateList = new ArrayList<>();
        List<ArticleBasicModel> articleBasicList = archivesService.selectBlogInfoListByDate(selectDate + "%");
        while (!articleBasicList.isEmpty()) {
            ArchivesModel archivesModel = new ArchivesModel();
            String date = DateUtils.getDate(articleBasicList.get(articleBasicList.size() - 1).getAbPublishDate());
            archivesModel.setArchivesDate(date);
            archivesModel.setArticleBasicList(new ArrayList<>());
            for (int i = articleBasicList.size() - 1; i >= 0; i--) {
                date = DateUtils.getDate(articleBasicList.get(i).getAbPublishDate());
                if (!archivesModel.getArchivesDate().equals(date)) {
                    break;
                }
                archivesModel.getArticleBasicList().add(articleBasicList.get(i));
                articleBasicList.remove(i);
            }
            archivesModel.setArchivesCount(archivesModel.getArticleBasicList().size());
            archivesDateList.add(archivesModel);
        }
        return archivesDateList;
    }

    @GetMapping("/archives")
    public String archives(ModelMap modelMap) {
        List<ArchivesModel> archivesDateList = archivesService.selectBlogArchivesByDate();
        modelMap.put("archivesDateList", archivesDateList);
        modelMap.put("blogInfoList", blogArchivesByDate(DateUtils.getDate().split("-")[0]));
        return "archives";
    }

    @GetMapping("/archives/{date}")
    public String archivesLimit(@PathVariable("date") String date, ModelMap modelMap) {
        List<ArchivesModel> archivesDateList = archivesService.selectBlogArchivesByDate();
        modelMap.put("archivesDateList", archivesDateList);
        modelMap.put("blogInfoList", blogArchivesByDate(
                date.replace("年", "-")
                        .replace("月", "-")
        ));
        return "archives";
    }
}
