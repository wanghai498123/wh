package cn.com.tass.chsmc.interceptor;

import java.util.Map;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import cn.com.tass.chsmc.constant.CommonConst;
import cn.com.tass.chsmc.constant.SessionConst;
import cn.com.tass.chsmc.exception.NoAuthException;
import cn.com.tass.chsmc.interceptor.annotation.IgnoreLogin;
import cn.com.tass.chsmc.interceptor.annotation.MenuAnnotaion;

/**
 * 标题: 授权验证拦截器
 * <p>
 * 描述: 授权验证拦截器
 * <p>
 * 版权: Copyright (c) 2016
 * <p>
 * 公司:
 * <p>
 * 
 * @author 
 * @created 2016-2-24 上午11:09:32
 * @version 1.0
 */
@Configuration
public class AuthenticCheckInterceptor extends BaseInterceptor {

	Logger logger = LoggerFactory.getLogger(LoginCheckInterceptor.class);

	@SuppressWarnings("unchecked")
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		HandlerMethod handlerMethod = (HandlerMethod) handler;

		// 1. ------------------认证登陆-------------------------------
		// 忽略认证的Controller
		IgnoreLogin ignoreLogin = handlerMethod.getMethod().getAnnotation(IgnoreLogin.class);
		if (ignoreLogin != null) {
			return true;
		}

		Object userInfoObj = request.getSession().getAttribute(SessionConst.UserInfo);
		if (userInfoObj == null) {
			throw new LoginException();
		}

		// 2. --------------------权限控制------------------------------
		MenuAnnotaion menuKey = handlerMethod.getMethod().getAnnotation(MenuAnnotaion.class);
		if (menuKey != null) {
			Object userMenuMapObj = request.getSession().getAttribute(SessionConst.UserMenuMapInfo);
			Map<String, Long> userMenuMap = (Map<String, Long>) userMenuMapObj;
			if (userMenuMap != null) {
				if (userMenuMap.containsKey(menuKey.menuKey())) {
					return true;
				} else {
					// 授权认证失败
					throw new NoAuthException();
				}
			} else {
				// 登陆出现问题 或session过期
				throw new LoginException();
			}
		}

		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		HandlerMethod handlerMethod = (HandlerMethod) handler;
		// 忽略认证的Controller
		IgnoreLogin ignoreLogin = handlerMethod.getMethod().getAnnotation(IgnoreLogin.class);
		if (ignoreLogin != null) {
			return ;
		}
		
		MenuAnnotaion menuKey = handlerMethod.getMethod().getAnnotation(MenuAnnotaion.class);
		Object userMenuMapObj = request.getSession().getAttribute(SessionConst.UserMenuMapInfo);

		if (menuKey!=null&&userMenuMapObj != null && modelAndView != null && modelAndView.getModel() != null) {
			// 设置当前的Action的MenuId
			Map<String, Long> userMenuMap = (Map<String, Long>) userMenuMapObj;

			if (userMenuMap.containsKey(menuKey.menuKey()))
				modelAndView.getModel().put(CommonConst.MenuId, userMenuMap.get(menuKey.menuKey()));
			else {
				modelAndView.getModel().put(CommonConst.MenuId, 0);
			}
		}else {
			if (!(isAjaxRequest(request) || (request instanceof MultipartHttpServletRequest))) {
				if(modelAndView!=null&&modelAndView.getModel()!=null)
					modelAndView.getModel().put(CommonConst.MenuId, 0);
			}
		}
	}
}
