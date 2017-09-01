package com.shuoyi.common.cache;

import java.util.List;
import java.util.Set;

/**
 * Created by zhaosy-c on 2017/8/31.
 */
public interface ICacheService {

    public <T> boolean set(String key, T value);

    public <T> boolean set(String key, T value, int expire);

    /**
     * 如果key已经存在 则返回false
     *
     * @param key
     * @param value
     * @return
     */
    public <T> boolean setn(String key, T value);

    /**
     * 如果key已经存在 则返回false
     *
     * @param key
     * @param value
     * @param expire 过期时间（单位：秒）
     * @return
     */
    public <T> boolean setn(String key, T value, int expire);

    public boolean setString(String key, String value);

    public boolean setString(String key, String value, int expire);

    public boolean setnString(String key, String value);

    public boolean setnString(String key, String value, int expire);

    public String getString(String key);

    /**
     * 原子性设置key-value
     *
     * @param key
     * @param value
     * @param expire
     * @return
     */
    public <T> boolean cas(String key, T value, int expire);

    /**
     * 根据key获取对象
     *
     * @param key
     * @return
     */
    public <T> T get(String key);

    /**
     * 删除指定缓存
     *
     * @param key
     * @return
     */
    public boolean delete(String key);

    /**
     * 判断指定缓存是否存在
     *
     * @param key
     * @return
     */
    public boolean exist(String key);

    /**
     * 是否shutdown
     *
     * @return
     */
    public boolean isShutdown();

    /**
     * 获取key的版本号
     *
     * @param key
     * @return
     */
    public long getVersion(String key);

    /**
     * @param key
     * @return <T>
     * @description 根据key获取存储的值(抛异常时返回null)
     * @author tony
     * @created 2016-05-09
     */
    public <T> T getByKey(String key);

    /**
     * 添加到List
     *
     * @param key
     * @param value
     * @return
     */
    public boolean addList(String key, String... value);

    /**
     * 检查List长度
     *
     * @param key
     * @return
     */
    public long countList(String key);


    /**
     * 获取List
     *
     * @param key
     * @return
     */
    public List<String> getList(String key);

    /**
     * 截取List
     *
     * @param key
     * @param start 起始位置
     * @param end   结束位置
     * @return
     */
    public List<String> rangeList(String key, long start, long end);

    /**
     * 修剪List内存
     *
     * @param key
     * @param start
     * @param end
     */
    public void lTrimList(String key, long start, long end);

    /**
     * 对value值进行+1处理
     * INCR命令用于由一个递增key的整数值。如果该key不存在，它被设置为0执行操作之前。
     * 如果key包含了错误类型的值或包含不能被表示为整数，字符串，则返回错误。该操作被限制为64位带符号整数。
     *
     * @param sKey
     * @return
     */
    public Long incr(String sKey);

    /**
     * 往set集合中加入值
     *
     * @param sKey
     * @param sValue
     * @return
     */
    public boolean sadd(String sKey, String sValue);

    /**
     * 往set集合中加入值，并设置缓存过期时间
     *
     * @param sKey
     * @param sValue
     * @param nExpire 单位：秒
     * @return
     */
    public boolean sadd(String sKey, String sValue, int nExpire);

    /**
     * 获取set集合
     *
     * @param sKey
     * @return
     */
    public Set<String> getSetValue(String sKey);

    /**
     * 判断value是否在sKey对应的set集合中
     *
     * @param sKey
     * @param sValue
     * @return
     */
    public boolean isInSet(String sKey, String sValue);


}
