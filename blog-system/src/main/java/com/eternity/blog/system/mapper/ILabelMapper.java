package com.eternity.blog.system.mapper;

import com.eternity.blog.system.domain.blog.LabelModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description
 * @Author eternity
 * @Date 2020/4/21 22:13
 */
@Mapper
@Repository
public interface ILabelMapper {
    /**
     * 增加标签
     *
     * @param labelModel 标签
     * @return 结果
     */
   int insertLabel(LabelModel labelModel);

    /**
     * 删除标签
     *
     * @param lId 标签编号
     * @return 结果
     */
   int deleteLabelByLid(Long lId);

    /**
     * 修改标签
     *
     * @param labelModel 标签
     * @return 结果
     */
   int updateLabel(LabelModel labelModel);

    /**
     * 校验标签编号是否唯一
     *
     * @param lId 标签编号
     * @return 结果
     */
   int checkLabelById(Long lId);

    /**
     * 校验标签名称是否唯一
     *
     * @param lName 标签名称
     * @return 结果
     */
   int checkLabelByName(String lName);

    /**
     * 校验标签是否被使用
     *
     * @param lId 标签编号
     * @return 结果
     */
   int checkLabelUse(Long lId);

    /**
     * 查询所有标签
     *
     * @return 标签列表
     */
   List<LabelModel> selectAll();

    /**
     * 分页查询标签
     *
     * @param page  页数
     * @param limit 每页数量
     * @return 标签列表
     */
   List<LabelModel> selectLimit(@Param("page") int page, @Param("limit") int limit);

    /**
     * 获取标签数量
     *
     * @return 标签数量
     */
   Long selectAllCount();
}
