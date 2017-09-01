package com.shuoyi.common.cache;

/**
 * Created by zhaosy-c on 2017/8/31.
 */
public class AbstractBaseCacheUtil {

    protected static ICacheService r_cacheService = CacheServiceManager.getCacheServiceByName("StorageCache");

}
