package com.taotao.redis.annotation;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Redis自定义注解解析器
 * 说明：实现ApplicationListener 可以获得spring中拥有指定注解的bean
 * @author Administrator
 *
 */
@Component
public class RedisHandleParse implements ApplicationListener<ContextRefreshedEvent>{
	/**
	 * 初始化方法。容器启动的时候执行
	 * @throws Exception
	 */
//	@PostConstruct
//	public void init() throws Exception{
//		
//		Integer.parseInt("sad");
//	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent context) {
		  // 根容器为Spring容器  
//        if(context.getApplicationContext().getParent()==null){  
//        	//获取所有带指定注解的bean
//            Map<String,Object> beans = context.getApplicationContext().getBeansWithAnnotation(Service.class);
//            for(Object bean:beans.values()){  
//            	//获取代理对象的目标对象（因为通过代理对象无法获得注解）
//            	try {
//					bean = AopTargetUtils.getTarget(bean);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//            	//获取该类的所有带注解的方法
//                Method[] methods = bean.getClass().getDeclaredMethods();
//
//                for (Method method : methods) {
////                	Annotation[] annotations = method.getAnnotations();
////                	for (Annotation annotation : annotations) {
////						System.out.println(bean.getClass().getName() + "---" +method.getName()+ "---" + annotation.annotationType());
////					}
//                	RedisHandle redisAnno = method.getAnnotation(RedisHandle.class);
//                	if (redisAnno !=null) {
//                		System.out.println(bean.getClass().getName() + "." +method.getName()+"(..)");
//					}
//				}
//            }  
//        }  
	}
}
