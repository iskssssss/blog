package com.eternity.blog.system.service;

import com.eternity.blog.system.domain.blog.SortModel;

import java.util.List;

/**
 * @Description 分类管理 业务层
 * @Author eternity
 * @Date 2020/4/23 10:39
 */
public interface ISortService {
    /**
     * 增加分类
     *
     * @param sortModel 分类
     * @return 结果
     */
   int insertSort(SortModel sortModel);

    /**
     * 删除分类
     *
     * @param lId 分类编号
     * @return 结果
     */
   int deleteSortByLid(Long lId);

    /**
     * 修改分类
     *
     * @param sortModel 分类
     * @return 结果
     */
   int updateSort(SortModel sortModel);

    /**
     * 校验分类编号是否唯一
     *
     * @param sId 分类编号
     * @return 结果
     */
   int checkSortById(Long sId);

    /**
     * 校验分类名称是否唯一
     *
     * @param sName 分类名称
     * @return 结果
     */
   int checkSortByName(String sName);

    /**
     * 校验分类是否被使用
     *
     * @param sId 分类编号
     * @return 结果
     */
   int checkSortUse(Long sId);

    /**
     * 查询所有分类
     *
     * @return 分类列表
     */
   List<SortModel> selectAll();

    /**
     * 分页查询分类
     *
     * @param page  页数
     * @param limit 每页数量
     * @return 分类列表
     */
   List<SortModel> selectLimit(int page, int limit);

    /**
     * 获取分类数量
     *
     * @return 分类数量
     */
   Long selectAllCount();
}

