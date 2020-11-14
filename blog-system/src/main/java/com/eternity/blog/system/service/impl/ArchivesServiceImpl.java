package com.eternity.blog.system.service.impl;

import com.eternity.blog.system.domain.blog.ArchivesModel;
import com.eternity.blog.system.domain.blog.ArticleBasicModel;
import com.eternity.blog.system.mapper.IArchivesMapper;
import com.eternity.blog.system.service.IArchivesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author eternity
 * @Date 2020/5/19 22:06
 */
@Service
public class ArchivesServiceImpl implements IArchivesService {
    private static final Logger log = LoggerFactory.getLogger(ArchivesServiceImpl.class);

    @Autowired
    private IArchivesMapper archivesMapper;

    @Override
    public List<ArchivesModel> selectBlogArchivesByDate() {
        log.info("按照日期查询博客归档信息%s");
        return archivesMapper.selectBlogArchivesByDate();
    }

    @Override
    public List<ArticleBasicModel> selectBlogInfoListByDate(String date) {
        log.info(String.format("按照日期(%s)查询博客归档信息", date));
        return archivesMapper.selectBlogInfoListByDate(date);
    }
}
