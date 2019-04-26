package cn.com.tass.chsmc.interceptor.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 标题: 日志注解标签
 * <p>
 * 描述: 日志注解标签
 * <p>
 * 版权: Copyright (c) 2016
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 * 
 * @author 卢灿 [lucan@tass.com.cn]
 * @created 2016-3-7 上午10:39:35
 * @version 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.TYPE,java.lang.annotation.ElementType.METHOD})
public @interface LogAnnotation {

	/**
	 * 日志操作名
	 * @return
	 */
	public String logName() default("");
	
	/**
	 * 记录日志的模型名
	 * @return
	 */
	public String logModelName() default("");
	
	/**
	 * 记录日志的模型对象的属性名称
	 * @return
	 */
	public String logPropertyName() default("");
}
