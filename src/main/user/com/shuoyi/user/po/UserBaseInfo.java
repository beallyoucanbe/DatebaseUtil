package com.shuoyi.user.po;

import javax.persistence.*;

/**
 * Created by zhaosy-c on 2017/8/22.
 */
@Entity
@Table(name = "userbaseinfo")
public class UserBaseInfo {
    private Integer userId;
    private Integer companyID;
    private String GLDUserID;
    private Integer userType;
    private Boolean deleted;
    private String remark;
    private String gccpDisplayName;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserId", unique = true, nullable = false)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Column(name = "CompanyID")
    public Integer getCompanyID() {
        return companyID;
    }

    public void setCompanyID(Integer companyID) {
        this.companyID = companyID;
    }

    @Column(name = "GLDUserID")
    public String getGLDUserID() {
        return GLDUserID;
    }

    public void setGLDUserID(String GLDUserID) {
        this.GLDUserID = GLDUserID;
    }

    @Column(name = "UserType")
    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    @Column(name = "Delete")
    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Column(name = "Remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "GCCPDisplayName")
    public String getGccpDisplayName() {
        return gccpDisplayName;
    }

    public void setGccpDisplayName(String gccpDisplayName) {
        this.gccpDisplayName = gccpDisplayName;
    }
}
