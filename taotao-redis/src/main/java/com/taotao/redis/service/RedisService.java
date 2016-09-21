package com.taotao.redis.service;

import com.taotao.common.utils.TaotaoResult;

/**
 * redis 提供接口供其他系统进行同步
 * @author Administrator
 *
 */
public interface RedisService {
	TaotaoResult syncContent(Long contentCatId);
}
