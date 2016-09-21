package com.taotao.redis.dao;

/**
 * jedis常用操作接口
 *  @author Administrator
 *
 */
public interface JedisClient {
	/**
	 * 设置String值
	 * @param key
	 * @param val
	 * @return
	 */
	String set(String key,String val);
	/**
	 * 获取String值
	 * @param key
	 * @return
	 */
	String get(String key);
	
	/**
	 * 设置hash值
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	Long hset( String key,  String field,  String value);
	/**
	 * 获取hash值
	 * @param key
	 * @param field
	 * @return
	 */
	String hget(final String key, final String field);
	
	/**
	 * 使值增加1
	 * @param key
	 * @return
	 */
	Long incr( String key);
	
	/**
	 * 设置键的有效期
	 * @param key
	 * @param seconds
	 * @return
	 */
	Long expire( String key,  int seconds);
	
	/**
	 * 获取键的有效期
	 * @param key
	 * @return
	 */
	Long ttl( String key);
	
	/**
	 * 删除hash key
	 * @param hkey
	 * @param key
	 * @return
	 */
	Long hdel(String hkey,String key);
	
	/**
	 * 删除 key
	 * @param hkey
	 * @param key
	 * @return
	 */
	Long del(String key);
}
