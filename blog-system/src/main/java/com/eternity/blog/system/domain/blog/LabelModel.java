package com.eternity.blog.system.domain.blog;

import com.eternity.blog.common.core.domain.model.BaseEntity;

/**
 * @Description 标签实体
 * @Author eternity
 * @Date 2020/4/21 23:34
 */
public class LabelModel extends BaseEntity {
    /**
     * 标签编号
     */
    private Long lLid;
    /**
     * 标签名称
     */
    private String lName;
    /**
     * 标签别名
     */
    private String lAlias;
    /**
     * 标签说明
     */
    private String lDescription;
    /**
     * 添加时间
     */
    private String lAddDate;

    public Long getlLid() {
        return lLid;
    }

    public void setlLid(Long lLid) {
        this.lLid = lLid;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getlAlias() {
        return lAlias;
    }

    public void setlAlias(String lAlias) {
        this.lAlias = lAlias;
    }

    public String getlDescription() {
        return lDescription;
    }

    public void setlDescription(String lDescription) {
        this.lDescription = lDescription;
    }

    public String getlAddDate() {
        return lAddDate;
    }

    public void setlAddDate(String lAddDate) {
        this.lAddDate = lAddDate;
    }

    @Override
    public String toString() {
        return "LabelModel{" +
                "lLid=" + lLid +
                ", lName='" + lName + '\'' +
                ", lAlias='" + lAlias + '\'' +
                ", lDescription='" + lDescription + '\'' +
                ", lAddDate='" + lAddDate + '\'' +
                '}';
    }
}
