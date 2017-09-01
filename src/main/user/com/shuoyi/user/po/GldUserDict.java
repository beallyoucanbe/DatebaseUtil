package com.shuoyi.user.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by zhaosy-c on 2017/8/30.
 */
@Entity
@Table(name = "glduserdict")
public class GldUserDict {

    private String GLDUserID;// 用户在广联云库中的ID，只是为了方便以后查找
    private String account; // 用户名
    private String userName;// 姓名
    private String fullUserName;// 全名
    private String phoneNum;// 手机
    private String passwordMobile;//密保手机
    private String email;// 邮箱

    @Id
    @Column(name = "GLDUserID")
    public String getGLDUserID() {
        return GLDUserID;
    }

    public void setGLDUserID(String gLDUserID) {
        GLDUserID = gLDUserID;
    }

    @Column(name = "Account", nullable = false)
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    @Column(name = "PasswordMobile")
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

}
