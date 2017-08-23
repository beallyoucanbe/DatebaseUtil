package com.shuoyi.user.po;

import javax.persistence.*;

/**
 * Created by zhaosy-c on 2017/8/23.
 */
@Entity
@Table(name = "user")
public class User {
    private Integer userId;
    private Integer companyId; // 企业ID

    private String GLDUserID;// 用户在广联云库中的ID，只是为了方便以后查找
    private String account; // 用户名
    private String gccpDisplayName; // 云计价平台显示用户名
    private String userName;// 姓名
    private String fullUserName;// 全名
    private String phoneNum;// 手机
    private String passwordMobile;//密保手机
    private String email;// 邮箱
    private Boolean deleted;// 删除标识
    private String passWord;// 密码
    private Integer userType;// 类型，1：管理员;2普通用户;1000：测试用户
    private String workspaceKey;// 工作空间key
    private String remark;// 备注

    public User() {
        deleted = false;
    }

    public User(Integer userId, Integer companyId, String GLDUserID, String account, String gccpDisplayName, String userName, String fullUserName, String phoneNum, String passwordMobile, String email, Boolean deleted, Integer userType, String remark) {
        this.userId = userId;
        this.companyId = companyId;
        this.GLDUserID = GLDUserID;
        this.account = account;
        this.gccpDisplayName = gccpDisplayName;
        this.userName = userName;
        this.fullUserName = fullUserName;
        this.phoneNum = phoneNum;
        this.passwordMobile = passwordMobile;
        this.email = email;
        this.deleted = deleted;
        this.userType = userType;
        this.remark = remark;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID", unique = true, nullable = false)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Column(name = "CompanyID")
    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    @Column(name = "GLDUserID")
    public String getGLDUserID() {
        return GLDUserID;
    }

    public void setGLDUserID(String GLDUserID) {
        this.GLDUserID = GLDUserID;
    }

    @Column(name = "Account")
    public String getAccount() {
        return account;
    }

    @Column(name = "Account")
    public void setAccount(String account) {
        this.account = account;
    }

    @Column(name = "GCCPDisplayName")
    public String getGccpDisplayName() {
        return gccpDisplayName;
    }

    public void setGccpDisplayName(String gccpDisplayName) {
        this.gccpDisplayName = gccpDisplayName;
    }

    @Column(name = "UserName")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "FullUserName")
    public String getFullUserName() {
        return fullUserName;
    }

    public void setFullUserName(String fullUserName) {
        this.fullUserName = fullUserName;
    }

    @Column(name = "PhoneNum")
    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPasswordMobile() {
        return passwordMobile;
    }

    public void setPasswordMobile(String passwordMobile) {
        this.passwordMobile = passwordMobile;
    }

    @Column(name = "Email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "Deleted", nullable = false)
    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Column(name = "PassWord")
    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Column(name = "UserType")
    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    @Column(name = "WorkspaceKey")
    public String getWorkspaceKey() {
        return workspaceKey;
    }

    public void setWorkspaceKey(String workspaceKey) {
        this.workspaceKey = workspaceKey;
    }

    @Column(name = "Remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


}
