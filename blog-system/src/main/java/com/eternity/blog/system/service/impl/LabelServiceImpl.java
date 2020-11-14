package com.eternity.blog.system.service.impl;

import com.eternity.blog.system.domain.blog.LabelModel;
import com.eternity.blog.system.mapper.ILabelMapper;
import com.eternity.blog.system.service.ILabelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description 标签管理 业务层实现
 * @Author eternity
 * @Date 2020/4/21 23:50
 */
@Service
public class LabelServiceImpl implements ILabelService {
    private static final Logger log = LoggerFactory.getLogger(LabelServiceImpl.class);

    @Autowired
    ILabelMapper labelMapper;


    @Override
    public int insertLabel(LabelModel labelModel) {
        log.info(String.format("添加标签. 标签名：%s", labelModel.getlName()));
        return labelMapper.insertLabel(labelModel);
    }

    @Override
    public int deleteLabelByLid(Long lId) {
        log.info(String.format("删除标签. 标签ID：%d", lId));
        return labelMapper.deleteLabelByLid(lId);
    }

    @Override
    public int updateLabel(LabelModel labelModel) {
        log.info(String.format("修改标签. 标签：%s", labelModel.toString()));
        return labelMapper.updateLabel(labelModel);
    }

    @Override
    public int checkLabelById(Long lId) {
        log.info(String.format("校验标签是否唯一. 标签ID：%d", lId));
        return labelMapper.checkLabelById(lId);
    }

    @Override
    public int checkLabelByName(String lName) {
        log.info(String.format("校验标签是否唯一. 标签名：%s", lName));
        return labelMapper.checkLabelByName(lName);
    }

    @Override
    public int checkLabelUse(Long lId) {
        log.info(String.format("校验标签是否被使用. 标签ID：%d", lId));
        return labelMapper.checkLabelUse(lId);
    }

    @Override
    public List<LabelModel> selectAll() {
        log.info("查询所有标签.");
        return labelMapper.selectAll();
    }

    @Override
    public List<LabelModel> selectLimit(int page, int limit) {
        log.info(String.format("分页查询标签. page：%d limit：%d", page, limit));
        return labelMapper.selectLimit(page, limit);
    }

    @Override
    public Long selectAllCount() {
        log.info("获取标签总数.");
        return labelMapper.selectAllCount();
    }
}
