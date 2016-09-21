package com.taotao.redis.constant;


/**
 * 管理Redis常用键
 * @author Administrator
 *
 */
public class RedisConstant {
	/**
	 * 内容管理hash key
	 */
	public static final String CONTENT_REDIS_HASH_KEY= "CONTENT_REDIS_HASH_KEY";
	
	/**
	 * 商品分类hash key
	 */
	public static final String ITEMCAT_REDIS_KEY= "ITEMCAT_REDIS_KEY";
	/**
	 * 商品key
	 */
	public static final String ITEM_REDIS_KEY= "ITEM_REDIS_KEY";
	/**
	 * 商品信息保存的有效期
	 */
	public static final String ITEM_REDIS_EXPIRY = "86400";
	
	/**
	 * redis切入点管理
	 */
//	public static final String REDIS_POINT_CUT= "execution(* com.taotao.rest.service.impl.ContentServiceImpl.getContentList(..)) " +
//			"|| execution(* com.taotao.rest.service.impl.ItemCatServiceImpl.getItemCatList(..))";
	
}
