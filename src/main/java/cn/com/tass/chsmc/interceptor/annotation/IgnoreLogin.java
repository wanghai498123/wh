package cn.com.tass.chsmc.interceptor.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标题: 忽略登陆验证注解
 * <p>
 * 描述: 忽略登陆验证注解
 * <p>
 * 版权: Copyright (c) 2016
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 * 
 * @author 卢灿 [lucan@tass.com.cn]
 * @created 2016-2-24 下午03:36:21
 * @version 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.TYPE,java.lang.annotation.ElementType.METHOD})
public  @interface IgnoreLogin {

}
