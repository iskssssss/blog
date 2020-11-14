package com.eternity.blog.system.domain.user;

import com.eternity.blog.common.core.domain.model.BaseEntity;

/**
 * @Description 用户身份验证实体
 * @Author eternity
 * @Date 2020/4/10 17:58
 */
public class UserAuthsModel extends BaseEntity {

    /**
     * 身份凭证编号
     */
    private Long aId;
    /**
     * 用户编号
     */
    private Long aUid;
    /**
     * 凭证类型
     */
    private Integer aType;
    /**
     * 标识
     */
    private String aIdentifier;
    /**
     * 凭证
     */
    private String aCredential;
    /**
     * 是否验证
     */
    private Integer aVerification;
    /**
     * 加密盐
     */
    private String aSalt;

    public UserAuthsModel() {

    }

    public UserAuthsModel(Long uid, String pwd, String salt, Integer verification) {
        this.aUid = uid;
        this.aCredential = pwd;
        this.aSalt = salt;
        this.aVerification = verification;
    }

    @Override
    public String toString() {
        return "UserAuthsModel{" +
                "aId=" + aId +
                ", aUid=" + aUid +
                ", aType=" + aType +
                ", aIdentifier='" + aIdentifier + '\'' +
                ", aCredential='" + aCredential + '\'' +
                ", aVerification=" + aVerification +
                ", aSalt='" + aSalt + '\'' +
                '}';
    }

    public Long getaId() {
        return aId;
    }

    public void setaId(Long aId) {
        this.aId = aId;
    }

    public Long getaUid() {
        return aUid;
    }

    public void setaUid(Long aUid) {
        this.aUid = aUid;
    }

    public Integer getaType() {
        return aType;
    }

    public void setaType(Integer aType) {
        this.aType = aType;
    }

    public String getaIdentifier() {
        return aIdentifier;
    }

    public void setaIdentifier(String aIdentifier) {
        this.aIdentifier = aIdentifier;
    }

    public String getaCredential() {
        return aCredential;
    }

    public void setaCredential(String aCredential) {
        this.aCredential = aCredential;
    }

    public Integer getaVerification() {
        return aVerification;
    }

    public void setaVerification(Integer aVerification) {
        this.aVerification = aVerification;
    }

    public String getaSalt() {
        return aSalt;
    }

    public void setaSalt(String aSalt) {
        this.aSalt = aSalt;
    }
}
