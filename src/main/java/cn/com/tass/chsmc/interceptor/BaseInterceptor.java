package cn.com.tass.chsmc.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.com.tass.chsmc.utils.ControllerUtil;

/**
 * 标题: 拦截器基类
 * <p>
 * 描述: 拦截器基类
 * <p>
 * 版权: Copyright (c) 2016
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 * 
 * @author 卢灿 [lucan@tass.com.cn]
 * @created 2016-2-24 上午11:36:54
 * @version 1.0
 */
public class BaseInterceptor extends HandlerInterceptorAdapter {

	/**
	 * 判断是否ajax登陆请求
	 * 
	 * @param request
	 * @return
	 */
	protected boolean isAjaxRequest(HttpServletRequest request) {

		return ControllerUtil.isAjax(request);
	}
}
