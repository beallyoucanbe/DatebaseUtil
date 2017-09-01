package com.shuoyi.common.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by zhaosy-c on 2017/8/31.
 */
public class CacheServiceManager {
    private static ConcurrentMap<String, ICacheService> m_CacheServiceMap = new ConcurrentHashMap<String, ICacheService>();

    private static ICacheService createCacheService(String name){
        CacheServiceConfig.CacheConfig config = CacheServiceConfig.getCacheConfig(name);
        return getCacheService(config);
    }

    //暂时都用redis缓存代替
    private static ICacheService getCacheService(CacheServiceConfig.CacheConfig config){
        if(config != null)
            switch (config.getType()){
                case memcache:
                    return new RedisCacheService(config);
                case redis:
                    return new RedisCacheService(config);
                default:
                    return new RedisCacheService(config);
            }
        return new RedisCacheService(config);
    }

    public static ICacheService getCacheServiceByName(String name) {
        ICacheService iService = m_CacheServiceMap.get(name);
        if (iService == null) {
            synchronized (CacheServiceManager.class) {
                iService = m_CacheServiceMap.get(name);
                if (iService == null) {
                    iService = createCacheService(name);
                    m_CacheServiceMap.put(name, iService);
                }
            }
        }
        return iService;
    }
}
