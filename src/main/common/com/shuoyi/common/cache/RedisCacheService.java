package com.shuoyi.common.cache;

import org.apache.log4j.Logger;
import org.springframework.util.SerializationUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Transaction;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Created by zhaosy-c on 2017/8/31.
 */
public class RedisCacheService implements ICacheService {
    private static Logger log = Logger.getLogger(RedisCacheService.class);
    private JedisPool oPool = null;
    private String sHost = null;
    private String sPort = null;
    //默认过期时间
    private int default_expire;

    public RedisCacheService(CacheServiceConfig.CacheConfig config) {
        if(null == oPool){
            String sMaxTotalV = CacheServiceConfig.getValue(config.getName() + ".maxTotal");
            String sMaxIdleV = CacheServiceConfig.getValue(config.getName() + ".maxIdle");
            String sMaxWaitV = CacheServiceConfig.getValue(config.getName() + ".maxWait");
            String sExpiresV = CacheServiceConfig.getValue(config.getName() + ".expires");

            String sMaxTotal = (sMaxTotalV == null) ? "0" : sMaxTotalV;
            String sMaxIdle = (sMaxIdleV == null) ? "0" : sMaxIdleV;
            String sMaxWait = (sMaxWaitV == null) ? "0" : sMaxWaitV;
            //默认过期时间为一周
            default_expire = (sExpiresV == null) ? 7*24*60*60 : Integer.parseInt(sExpiresV);
            String serverAddress = config.getServer();
            sHost = serverAddress.split(":")[0];
            sPort = serverAddress.split(":")[1];
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMaxTotal(Integer.parseInt(sMaxTotal));
            jedisPoolConfig.setMaxIdle(Integer.parseInt(sMaxIdle));
            jedisPoolConfig.setMaxWaitMillis(Integer.parseInt(sMaxWait));
            jedisPoolConfig.setTestOnBorrow(true);
            jedisPoolConfig.setBlockWhenExhausted(true);

            oPool = new JedisPool(jedisPoolConfig, sHost,Integer.parseInt(sPort));
        }
    }

    private Jedis getJedis(){
        Jedis jedis = null;
        try {
            jedis = oPool.getResource();
        } catch (Exception e) {
            log.error("获取Jedis实例失败", e);
        }
        return jedis;

    }
    @Override
    public <T> boolean set(String key, T value) {
        return set(key, value, default_expire);
    }

    @Override
    public <T> boolean set(String key, T value, int expire) {
        Jedis jedis = getJedis();
        try {
            if(null != jedis){
                byte[] objArry = SerializationUtils.serialize((Serializable) value);
                String setex = jedis.setex(key.getBytes(), expire, objArry);
                if(setex.equalsIgnoreCase("ok"))
                    return true;
                else
                    return false;
            }
        } catch (Exception e) {
            log.error("[Redis] 调用 setn 方法失败 key->" + key, e);
        } finally {
            jedis.close();
        }
        return false;
    }

    /**
     * 如果该key已经存在，则不进行任何操作 如果该key不存在，则不进行新增操作 返回false
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    @Override
    public <T> boolean setn(String key, T value) {
        Jedis jedis = getJedis();
        try {
            if(null != jedis){
                byte[] objArry = SerializationUtils.serialize((Serializable) value);
                Long returnInfo = jedis.setnx(key.getBytes(), objArry);
                if(returnInfo == 1)
                    return true;
                else
                    return false;
            }
        } catch (Exception e) {
            log.error("[Redis] 调用 setn 方法失败 key->" + key, e);
        } finally {
            jedis.close();
        }
        return false;
    }

    @Override
    public <T> boolean setn(String key, T value, int expire) {
        Jedis jedis = getJedis();
        try {
            if(null != jedis){
                byte[] objArry = SerializationUtils.serialize((Serializable) value);
                Long returnInfo = jedis.setnx(key.getBytes(), objArry);
                if(returnInfo == 1){
                    jedis.expire(key, expire);
                    return true;
                } else
                    return false;
            }
        } catch (Exception e) {
            log.error("[Redis] 调用 setn 方法失败 key->" + key, e);
        } finally {
            jedis.close();
        }
        return false;
    }

    @Override
    public boolean setString(String key, String value) {
        return set(key, value, default_expire);
    }

    @Override
    public boolean setString(String key, String value, int expire) {
        Jedis jedis = getJedis();
        try {
            if(null != jedis){
                String sResult = jedis.setex(key, expire,value);
                if(sResult.equalsIgnoreCase("ok")){
                    return true;
                } else
                    return false;
            }
        } catch (Exception e) {
            log.error("[Redis] 调用 setString 方法失败 key->" + key, e);
        } finally {
            jedis.close();
        }
        return false;
    }

    @Override
    public boolean setnString(String key, String value) {
        return setnString(key, value, default_expire);
    }

    @Override
    public boolean setnString(String key, String value, int expire) {
        Jedis jedis = getJedis();
        try {
            if(null != jedis){
                Long sResult = jedis.setnx(key, value);
                if(sResult > 0){
                    jedis.expire(key, expire);
                    return true;
                } else
                    return false;
            }
        } catch (Exception e) {
            log.error("[Redis] 调用 setnString 方法失败 key->" + key, e);
        } finally {
            jedis.close();
        }
        return false;
    }

    @Override
    public String getString(String key) {
        Jedis jedis = getJedis();
        try {
            if (null != jedis) {
                return jedis.get(key);
            }
        } catch (Exception e) {

            log.error("[Redis] 调用 get 方法失败 key->" + key, e);
        } finally {
           jedis.close();
        }
        return null;
    }

    /**
     * 使用redis的事务进行cas操作: MULTI 执行之后，客户端可以继续向服务器发送任意多条命令，这些命令不会立即被执行，而是被放到一个队列中
     * EXEC 命令被调用时，所有队列中的命令才会被执行。 如果调用exec时，该key对应的bValue发生改变，前面操作一律失效
     */
    @Override
    public <T> boolean cas(String key, T value, int expire) {
        Jedis jedis = getJedis();
        try {
            if(null != jedis){
                byte[] objArry = SerializationUtils.serialize((Serializable) value);
                jedis.watch(key); // 监听该key
                Transaction t = jedis.multi(); //开启对应的事务
                t.setex(key.getBytes(), expire, objArry);
                List<Object> result = t.exec(); //执行事务操作队列
                if(null == result || result.isEmpty()){
                    log.error(" Redis 事务执行失败" + key);
                    return false;
                }
                return true;
            }
        } catch (Exception e) {
            log.error("[Redis] 调用 cas 方法失败 key->" + key, e);
        } finally {
            jedis.close();
        }
        return false;
    }

    @Override
    public <T> T get(String key) {
        Jedis jedis = getJedis();
        byte[] bValue = null;
        try {
            if (null != jedis) {
                 bValue = jedis.get(key.getBytes());
                 if(null == bValue || bValue.length == 0)
                     return null;
                 return (T) SerializationUtils.deserialize(bValue);
            }
        } catch (Exception e) {
            log.error("[Redis] 调用 get 方法失败 key->" + key, e);
        } finally {
            jedis.close();
        }
        return null;
    }

    @Override
    public boolean delete(String key) {
        Jedis jedis = getJedis();
        try {
            if(null != jedis){
                Long result = jedis.del(key.getBytes());
                if(result == 1)
                    return true;
                else
                    return false;
            }
        } catch (Exception e) {
            log.error("[Redis] 调用 delete 方法失败 key->" + key, e);
        } finally {
            jedis.close();
        }
        return false;
    }

    @Override
    public boolean exist(String key) {
        boolean exist = false;
        Jedis jedis = getJedis();
        try {
            if(null != jedis){
                exist = jedis.exists(key.getBytes());
            }
        } catch (Exception e) {
            log.error("[Redis] 调用 delete 方法失败 key->" + key, e);
        } finally {
            jedis.close();
        }
        return exist;
    }

    @Override
    public boolean isShutdown() {
        return false;
    }

    @Override
    public long getVersion(String key) {
        return 0;
    }

    @Override
    public <T> T getByKey(String key) {
        return null;
    }

    @Override
    public boolean addList(String key, String... value) {
        return false;
    }

    @Override
    public long countList(String key) {
        return 0;
    }

    @Override
    public List<String> getList(String key) {
        return null;
    }

    @Override
    public List<String> rangeList(String key, long start, long end) {
        return null;
    }

    @Override
    public void lTrimList(String key, long start, long end) {

    }

    @Override
    public Long incr(String sKey) {
        return null;
    }

    @Override
    public boolean sadd(String sKey, String sValue) {
        return false;
    }

    @Override
    public boolean sadd(String sKey, String sValue, int nExpire) {
        return false;
    }

    @Override
    public Set<String> getSetValue(String sKey) {
        return null;
    }

    @Override
    public boolean isInSet(String sKey, String sValue) {
        return false;
    }

}
