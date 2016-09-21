package com.taotao.redis.service.impl;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import com.taotao.common.utils.ExceptionUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.redis.annotation.RedisHandle;
import com.taotao.redis.constant.RedisConstant;
import com.taotao.redis.dao.JedisClient;
import com.taotao.redis.service.RedisService;
/**
 * redis服务实现类
 * 
 * @author Administrator
 * 
 */
@SuppressWarnings("restriction")
@Service
@Aspect
// 将该类声明为aop切面
public class RedisServiceImpl implements RedisService {

	@Autowired
	private JedisClient jedisClient;
	
	@Override
	public TaotaoResult syncContent(Long contentCatId) {
		try {
			jedisClient.hdel(RedisConstant.CONTENT_REDIS_HASH_KEY, contentCatId+ "");
		} catch (Exception e) {
			TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return TaotaoResult.ok();
	}

	/**
	 * 对方法进行代理
	 * 
	 * @param point
	 */
	@Around("@annotation(com.taotao.redis.annotation.RedisHandle)")
	public Object getData(ProceedingJoinPoint point) {
		//从redis中查询数据
		Object data = getDataForRedis(point);
		if (data != null) 
			return data;
		
		//查询数据库数据
		try {
			data = point.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		//数据同步到redis
		saveDataToRedis(point, data);
		return data;
	}

	/**
	 * 同步数据到Redis
	 * @param key
	 * @param data
	 */
	private void saveDataToRedis(ProceedingJoinPoint point, Object data) {
		//把数据保存到redis中
		try {
			//获取方法的相关信息
			Method method = getPointMethod(point);
			RedisHandle redisHandle = method.getAnnotation(RedisHandle.class);//获取该方法的redis注解
		
			Object key = getKey(point,redisHandle);
			if (key != null) {
				key = (key+"").replace("?", point.getArgs()[0]+"");
				//从redis中查询数据
				if (StringUtils.isNotEmpty(redisHandle.hashKey())) {
					jedisClient.hset(redisHandle.hashKey(), key+"", JsonUtils.objectToJson(data));
				}else{
					jedisClient.set( key+"", JsonUtils.objectToJson(data));
				}
				//如果有效期不为空，就为键值设置有效期限
				if (StringUtils.isNoneEmpty(redisHandle.expiryDate())) {
					jedisClient.expire(key+"", Integer.parseInt(redisHandle.expiryDate()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 从redis中查询数据
	 * @param point
	 * @param key
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Object getDataForRedis(ProceedingJoinPoint point) {
		// 从redis中获取数据
		try {
			
			//获取方法的相关信息
			Method method = getPointMethod(point);
			RedisHandle redisHandle = method.getAnnotation(RedisHandle.class);//获取该方法的redis注解
			
			//获取key值
			Object key = getKey(point,redisHandle);
			if (key == null) {
				return null;
			}
			//替换键值的占位符
			key = (key+"").replace("?", point.getArgs()[0]+"");
			
			Class returnType = method.getReturnType();
			Class returnGenType = getMethodGenType(method);
			
			String result = null;
			//从redis中查询数据
			if (StringUtils.isNotEmpty(redisHandle.hashKey())) {
				result = jedisClient.hget(redisHandle.hashKey(),key+"");
			}else{
				result = jedisClient.get(key+"");
			}
			if (StringUtils.isNotEmpty(result)) {
				//判断返回值类型是否是List
				if (returnType.isInterface() && "List".equals(returnType.getSimpleName())) {
					return JsonUtils.jsonToList(result, returnGenType);
				}else if(returnType.getPackage().getName().startsWith("com.taotao")){ //判断是否是项目中的实体类
					return JsonUtils.jsonToPojo(result, returnType);
				}else{
					return result;
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取查询的key
	 * @param point
	 * @param redisHandle 
	 * @return
	 */
	private Object getKey(ProceedingJoinPoint point, RedisHandle redisHandle) {
		String key = redisHandle != null ? redisHandle.key() : null;
		//获取查询的key
		Object[] args = point.getArgs();
		return StringUtils.isEmpty(key) ? (args.length == 0 ? null : args[0]) : key;
	}

	/**
	 * 获取切点方法
	 * @param point
	 * @return
	 */
	private Method getPointMethod(JoinPoint point){
		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();
		
		//不使用代理的方法。使用真实的目标方法
		try {
			method = point.getTarget().getClass().getMethod(method.getName(), method.getParameterTypes());
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return method;
	}
	
	/**
	 * 获取方法返回值泛型值
	 * @param method
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "restriction" })
	private Class getMethodGenType(Method method) {
		//获取当前方法
		Type type = method.getGenericReturnType();
		
		Class returnGenType = null;
		if (type != null && type instanceof ParameterizedTypeImpl) {
			ParameterizedTypeImpl param= (ParameterizedTypeImpl)type;
			returnGenType = (Class) param.getActualTypeArguments()[0];
		}
		return returnGenType;
	}
}
