package cn.com.tass.chsmc.interceptor.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 描述: 菜单注解类
 * <p>
 * 版权: Copyright (c) 2016
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 * 
 * @author 卢灿 [lucan@tass.com.cn]
 * @created 2016-4-12 下午04:46:55
 * @version 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.TYPE,java.lang.annotation.ElementType.METHOD})
public @interface  MenuAnnotaion {

	/**
	 * 对应菜单键值
	 * @return
	 */
	String menuKey() default("");
}
