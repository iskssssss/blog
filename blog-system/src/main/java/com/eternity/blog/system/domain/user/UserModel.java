package com.eternity.blog.system.domain.user;

import com.eternity.blog.common.core.domain.model.BaseEntity;
import com.eternity.blog.common.utils.StringUtils;

/**
 * @Description 用户信息实体
 * @Author eternity
 * @Date 2020/4/10 16:04
 */
public class UserModel  extends BaseEntity {

    /**
     * 用户编号
     */
    private Long uId;
    /**
     * 用户昵称
     */
    private String uNickname;
    /**
     * 性别
     */
    private Integer uGender;
    /**
     * 生日
     */
    private String uBirthday;
    /**
     * 手机号
     */
    private String uPhone;
    /**
     * 邮箱
     */
    private String uEmail;
    /**
     * 居住地
     */
    private String uLocation;
    /**
     * 籍贯
     */
    private String uHometown;
    /**
     * 公司
     */
    private String uCompany;
    /**
     * 学校
     */
    private String uSchool;
    /**
     * 职业
     */
    private String uOccupation;
    /**
     * 账号状态
     */
    private Integer uStatus;

    /**
     * 用户身份验证实体
     */
    private UserAuthsModel userAuthsModel;

    public UserModel() {

    }

    public UserModel(Long uid, String nickname, String phone) {
        this.uId = uid;
        this.uNickname = nickname;
        this.uGender = 1;
        this.uPhone = phone;
        this.uStatus = 1;
        this.userAuthsModel = null;
    }


    public UserAuthsModel getUserAuthsModel() {
        return userAuthsModel;
    }

    public void setUserAuthsModel(UserAuthsModel userAuthsModel) {
        this.userAuthsModel = userAuthsModel;
    }

    public Long getuId() {
        return uId;
    }

    public void setuId(Long uId) {
        this.uId = uId;
    }

    public String getuNickname() {
        return uNickname;
    }

    public void setuNickname(String uNickname) {
        this.uNickname = uNickname;
    }

    public Integer getuGender() {
        return uGender;
    }

    public void setuGender(Integer uGender) {
        this.uGender = uGender;
    }

    public String getuBirthday() {
        return uBirthday;
    }

    public void setuBirthday(String uBirthday) {
        this.uBirthday = uBirthday;
    }

    public String getuPhone() {
        return uPhone;
    }

    public void setuPhone(String uPhone) {
        this.uPhone = uPhone;
    }

    public String getuEmail() {
        return uEmail;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
    }

    public String getuLocation() {
        return uLocation;
    }

    public void setuLocation(String uLocation) {
        this.uLocation = uLocation;
    }

    public String getuHometown() {
        return uHometown;
    }

    public void setuHometown(String uHometown) {
        this.uHometown = uHometown;
    }

    public String getuCompany() {
        return uCompany;
    }

    public void setuCompany(String uCompany) {
        this.uCompany = uCompany;
    }

    public String getuSchool() {
        return uSchool;
    }

    public void setuSchool(String uSchool) {
        this.uSchool = uSchool;
    }

    public String getuOccupation() {
        return uOccupation;
    }

    public void setuOccupation(String uOccupation) {
        this.uOccupation = uOccupation;
    }

    public Integer getuStatus() {
        return uStatus;
    }

    public void setuStatus(Integer uStatus) {
        this.uStatus = uStatus;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "uId=" + uId +
                ", uNickname='" + uNickname + '\'' +
                ", uGender=" + uGender +
                ", uBirthday='" + uBirthday + '\'' +
                ", uPhone='" + uPhone + '\'' +
                ", uEmail='" + uEmail + '\'' +
                ", uLocation='" + uLocation + '\'' +
                ", uHometown='" + uHometown + '\'' +
                ", uCompany='" + uCompany + '\'' +
                ", uSchool='" + uSchool + '\'' +
                ", uOccupation='" + uOccupation + '\'' +
                ", uStatus=" + uStatus +
                ", userAuthsModel=" + (StringUtils.isNotNull(userAuthsModel) ? userAuthsModel.toString() : null) + '}';
    }
}