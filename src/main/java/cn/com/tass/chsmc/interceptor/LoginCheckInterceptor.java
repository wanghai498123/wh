package cn.com.tass.chsmc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;

import cn.com.tass.chsmc.constant.SessionConst;
import cn.com.tass.chsmc.exception.NoAuthException;
import cn.com.tass.chsmc.interceptor.annotation.IgnoreLogin;

/**
 * 标题: 登陆验证拦截器
 * <p>
 * 描述: 登陆验证拦截器
 * <p>
 * 版权: Copyright (c) 2016
 * <p>
 * 公司:
 * <p>
 * 
 * @author
 * @created 2016-2-24 上午10:58:10
 * @version 1.0
 */
public class LoginCheckInterceptor extends BaseInterceptor {
	
	Logger logger=LoggerFactory.getLogger(LoginCheckInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		HandlerMethod handlerMethod = (HandlerMethod) handler;

		// 忽略认证的Controller
		IgnoreLogin ignoreLogin = handlerMethod.getMethod().getAnnotation(IgnoreLogin.class);
		if (ignoreLogin != null) {
			return true;
		}
		
		Object userInfoObj = request.getSession().getAttribute(SessionConst.UserInfo);
		if (userInfoObj == null) {
			throw new NoAuthException();
		}
		return true;
	}
}
