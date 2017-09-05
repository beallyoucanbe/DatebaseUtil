package com.shuoyi.user.cache;

import com.shuoyi.common.cache.AbstractBaseCacheUtil;
import com.shuoyi.user.po.User;

import java.util.HashMap;

/**
 * Created by zhaosy-c on 2017/8/31.
 */
public class UserCacheUtil extends AbstractBaseCacheUtil {
    /**
     * 向缓存中添加User
     *
     * @param user
     */
    public static void setUser(User user) {
        if (null == user) {
            return;
        }
        r_cacheService.set(UserCacheKeyFactory.getUserKey(user.getUserId()), user);
        setGldUserId(user.getGLDUserID(), user.getUserId());
    }

    /**
     * 通过userId获取User
     *
     * @param userId
     * @return
     */
    public static User getUserByUserId(Integer userId) {
        User oUser = null;
        String s_key = UserCacheKeyFactory.getUserKey(userId);
        try {
            oUser = r_cacheService.get(s_key);
        } catch (Exception e) {
            r_cacheService.delete(s_key);
        }
        return oUser;
    }

    /**
     * 通过userId删除缓存中的数据
     *
     * @param user
     */
    public static void delUserFromCache(User user) {
        if (null == user) {
            return;
        }
        r_cacheService.delete(UserCacheKeyFactory.getUserKey(user.getUserId()));
        r_cacheService.delete(UserCacheKeyFactory.getGldUserIdKey(user.getGLDUserID()));
    }

    /**
     * 向缓存中添加userId
     *
     * @param sGldUserId
     * @param nUserId
     */
    public static void setGldUserId(String sGldUserId, Integer nUserId) {
        r_cacheService.set(UserCacheKeyFactory.getGldUserIdKey(sGldUserId), nUserId);
    }

    public static void main(String[] args) {
        setGldUserId("123", 456);
    }
    /**
     * 根据gldUserId获取User
     *
     * @param gldUserId
     * @return
     */
    public static User getUserByGldUserId(String gldUserId) {
        Integer nUserId = r_cacheService.get(UserCacheKeyFactory.getGldUserIdKey(gldUserId));
        User oUser = null;
        String s_key = UserCacheKeyFactory.getUserKey(nUserId);
        try {
            oUser = r_cacheService.get(s_key);
        } catch (Exception e) {
            r_cacheService.delete(s_key);
        }
        return oUser;
    }

    /**
     * 向缓存中添加companyId,用于关联查询
     *
     * @param authcompanyId
     * @param companyId
     */
    public static void setAuthCompanyId(String authcompanyId, Integer companyId) {
        r_cacheService.set(UserCacheKeyFactory.getAuthCompanyIdKey(authcompanyId), companyId);
    }

    /**
     * 设置缓存公司下用户map add by zhenzq
     *
     * @param nCompanyID
     * @param companyUsersMap
     */
    public static void setCompanyUsersByCompanyId(Integer nCompanyID, HashMap<String, User> companyUsersMap) {
        String key = UserCacheKeyFactory.getCompanyUsersMapKey(nCompanyID);
        r_cacheService.set(key, companyUsersMap);
    }

    /**
     * 获取公司下用户map add by zhenzq
     *
     * @param companyID
     * @return
     */
    public static HashMap<String, User> getCompanyUsersByCompanyId(Integer companyID) {
        String sKey = UserCacheKeyFactory.getCompanyUsersMapKey(companyID);
        HashMap<String, User> oHashMap = null;
        try {
            oHashMap = r_cacheService.get(sKey);
        } catch (Exception e) {
            r_cacheService.delete(sKey);
        }
        return oHashMap;
    }

    public static HashMap<String, User> getCompanyUsersByAuthCompanyId(String authCompanyId) {
        Integer nCompanyId = r_cacheService.get(UserCacheKeyFactory.getAuthCompanyIdKey(authCompanyId));
        String sKey = UserCacheKeyFactory.getCompanyUsersMapKey(nCompanyId);
        HashMap<String, User> oHashMap = null;
        try {
            oHashMap = r_cacheService.get(sKey);
        } catch (Exception e) {
            r_cacheService.delete(sKey);
        }
        return oHashMap;
    }

    /**
     * 删除公司下用户map add by zhenzq
     *
     * @param companyID
     */
    public static void delCompanyUsersFromCache(Integer companyID) {
        String key = UserCacheKeyFactory.getCompanyUsersMapKey(companyID);
        r_cacheService.delete(key);
    }

}
