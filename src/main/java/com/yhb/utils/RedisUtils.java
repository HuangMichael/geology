package com.yhb.utils;

import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;



/**
 * Created by huangbin on 2017/10/12.
 */

/**
 *
 * <p>
 *  Redis客户端访问
 * </p>
 *
 * @author 卓轩
 * @创建时间：2014年7月11日
 * @version： V1.0
 */
public class RedisUtils {

    public  static  JedisPool jedisPool; // 池化管理jedis链接池

    static {
        JedisPoolConfig config = new JedisPoolConfig();
        //设置最大连接数
        config.setMaxTotal(8);
        //设置最大空闲数
        config.setMaxIdle(8);
        //设置超时时间
        config.setMaxWaitMillis(100000);
        //初始化连接池
        jedisPool = new JedisPool(config, "192.168.0.145", 6379);
    }

    /**
     * 向缓存中设置字符串内容
     * @param key key
     * @param value value
     * @return
     * @throws Exception
     */
    public static boolean  set(String key,String value) throws Exception{
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally{
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * 向缓存中设置对象
     * @param key
     * @param value
     * @return
     */
    public static boolean  set(String key,Object value){
        Jedis jedis = null;
        try {
            String objectJson = JSON.toJSONString(value);
            jedis = jedisPool.getResource();
            jedis.set(key, objectJson);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally{
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * 删除缓存中得对象，根据key
     * @param key
     * @return
     */
    public static boolean del(String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(key);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally{
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * 根据key 获取内容
     * @param key
     * @return
     */
    public static Object get(String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Object value = jedis.get(key);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally{
            jedisPool.returnResource(jedis);
        }
    }


    /**
     * 根据key 获取对象
     * @param key
     * @return
     */
    public static <T> T get(String key,Class<T> clazz){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String value = jedis.get(key);
            return JSON.parseObject(value, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally{
            jedisPool.returnResource(jedis);
        }
    }


}