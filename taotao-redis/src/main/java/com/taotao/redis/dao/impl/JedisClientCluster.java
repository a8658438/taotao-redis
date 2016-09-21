package com.taotao.redis.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.taotao.redis.dao.JedisClient;

public class JedisClientCluster implements JedisClient {

	@Autowired
	private JedisClientCluster cluster;
	
	@Override
	public String set(String key, String val) {
		return cluster.set(key, val);
	}

	@Override
	public String get(String key) {
		return cluster.get(key);
	}

	@Override
	public Long hset(String key, String field, String value) {
		return cluster.hset(key, field, value);
	}

	@Override
	public String hget(String key, String field) {
		return cluster.hget(key, field);
	}

	@Override
	public Long incr(String key) {
		return cluster.incr(key);
	}

	@Override
	public Long expire(String key, int seconds) {
		return cluster.expire(key, seconds);
	}

	@Override
	public Long ttl(String key) {
		return cluster.ttl(key);
	}

	@Override
	public Long hdel(String hkey, String key) {
		return cluster.hdel(hkey, key);
	}

	@Override
	public Long del(String key) {
		return cluster.del(key);
	}

}
