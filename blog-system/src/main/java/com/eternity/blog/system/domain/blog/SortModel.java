package com.eternity.blog.system.domain.blog;

import com.eternity.blog.common.core.domain.model.BaseEntity;

/**
 * @Description 分类实体
 * @Author eternity
 * @Date 2020/4/23 10:26
 */
public class SortModel extends BaseEntity {
    /**
     * 分类编号
     */
    private Long sSid;
    /**
     * 分类名称
     */
    private String sName;
    /**
     * 分类别名
     */
    private String sAlias;
    /**
     * 分类说明
     */
    private String sDescription;
    /**
     * 添加时间
     */
    private String sAddDate;

    public Long getsSid() {
        return sSid;
    }

    public void setsSid(Long sSid) {
        this.sSid = sSid;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsAlias() {
        return sAlias;
    }

    public void setsAlias(String sAlias) {
        this.sAlias = sAlias;
    }

    public String getsDescription() {
        return sDescription;
    }

    public void setsDescription(String sDescription) {
        this.sDescription = sDescription;
    }

    public String getsAddDate() {
        return sAddDate;
    }

    public void setsAddDate(String sAddDate) {
        this.sAddDate = sAddDate;
    }

    @Override
    public String toString() {
        return "SortModel{" +
                "sSid=" + sSid +
                ", sName='" + sName + '\'' +
                ", sAlias='" + sAlias + '\'' +
                ", sDescription='" + sDescription + '\'' +
                ", sAddDate='" + sAddDate + '\'' +
                '}';
    }
}
