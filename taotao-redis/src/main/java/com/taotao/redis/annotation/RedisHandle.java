package com.taotao.redis.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Redis注解
 * @author Administrator
 *
 */
@Target(ElementType.METHOD)//该注解使用在方法上
@Retention(RetentionPolicy.RUNTIME) //运行时使用
@Documented
public @interface RedisHandle {
	/**
	 * hash的key值(使用hash表时使用)
	 * @return
	 */
	String hashKey() default "";
	/**
	 * key值，默认使用方法的第一个参数
	 * @return
	 */
	String key() default "";
	String expiryDate() default "";
}
