package com.eternity.blog.system.service.impl;

import com.eternity.blog.system.domain.blog.SortModel;
import com.eternity.blog.system.mapper.ISortMapper;
import com.eternity.blog.system.service.ISortService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description 分类管理 业务层实现
 * @Author eternity
 * @Date 2020/4/23 10:40
 */
@Service
public class SortMapperImpl implements ISortService {
    private static final Logger log = LoggerFactory.getLogger(SortMapperImpl.class);

    @Autowired
    ISortMapper sortMapper;

    @Override
    public int insertSort(SortModel sortModel) {
        log.info(String.format("添加分类. 分类名：%s", sortModel.getsName()));
        return sortMapper.insertSort(sortModel);
    }

    @Override
    public int deleteSortByLid(Long lId) {
        log.info(String.format("删除分类. 分类ID：%d", lId));
        return sortMapper.deleteSortByLid(lId);
    }

    @Override
    public int updateSort(SortModel sortModel) {
        log.info(String.format("修改分类. 分类：%s", sortModel.toString()));
        return sortMapper.updateSort(sortModel);
    }

    @Override
    public int checkSortById(Long sId) {
        log.info(String.format("校验分类是否唯一. 分类ID：%d", sId));
        return sortMapper.checkSortById(sId);
    }

    @Override
    public int checkSortByName(String sName) {
        log.info(String.format("校验分类是否唯一. 分类名：%s", sName));
        return sortMapper.checkSortByName(sName);
    }

    @Override
    public int checkSortUse(Long sId) {
        log.info(String.format("校验分类是否被使用. 分类ID：%d", sId));
        return sortMapper.checkSortUse(sId);
    }

    @Override
    public List<SortModel> selectAll() {
        log.info("查询所有分类.");
        return sortMapper.selectAll();
    }

    @Override
    public List<SortModel> selectLimit(int page, int limit) {
        log.info(String.format("分页查询分类. page：%d limit：%d", page, limit));
        return sortMapper.selectLimit(page, limit);
    }

    @Override
    public Long selectAllCount() {
        log.info("获取分类总数.");
        return sortMapper.selectAllCount();
    }
}
