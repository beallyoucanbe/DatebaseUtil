package com.shuoyi.user.cache;

/**
 * 对User模块缓存key的构造规则进行统一管理
 * Created by zhaosy-c on 2017/8/31.
 */
public class UserCacheKeyFactory {
    // user对象
    private static final String USER = "USER_%s";
    // glduserid和userid关联关系
    private static final String GLDUSERID_ID = "GLDUSERID_ID_%s";
    // company对象
    private static final String COMPANY = "COMPANY_%s";
    // authcompanyid和companyid关联关系
    private static final String AUTHCOMPANYID_ID = "AUTHCOMPANYID_ID_%s";
    // 公司所有用户map
    private static final String COMPANY_USERS_MAP = "COMPANY_USERS_MAP_%s";

    /**
     * 构造user对象缓存key
     *
     * @param gldUserID
     * @return
     */
    public static String getUserKey(Integer gldUserID) {
        return String.format(USER, gldUserID);
    }

    public static void main(String[] args) {
        System.out.println(getUserKey(123));
    }
    /**
     * 构造company对象缓存key
     *
     * @return
     */
    public static String getCompanyKey(Integer companyID) {
        return String.format(COMPANY, companyID);
    }

    /**
     * 用于将authCompanyID和CompanyID关联
     *
     * @param authCompanyID
     * @return
     */
    public static String getAuthCompanyIdKey(String authCompanyID) {
        return String.format(AUTHCOMPANYID_ID, authCompanyID);
    }

    /**
     * 用于将glduserid和userid关联
     *
     * @return
     */
    public static String getGldUserIdKey(String gldUserId) {
        return String.format(GLDUSERID_ID, gldUserId);
    }

    /**
     * 用户获取公司下所有用户map的key add by zhenzq
     *
     * @return
     */
    public static String getCompanyUsersMapKey(Integer companyID) {
        return String.format(COMPANY_USERS_MAP, companyID);
    }


}
