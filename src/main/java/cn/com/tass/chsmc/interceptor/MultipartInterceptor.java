package cn.com.tass.chsmc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * 标题: Multipart请求拦截器
 * <p>
 * 描述:解决当form表单的enctype类型为”multipart/form-data”通过RequestContextHolder
 * 无法正确获的MultipartHttpServletRequest对象
 * <p>
 * 版权: Copyright (c) 2015
 * <p>
 * 公司
 * <p>
 * 
 * @author
 * @created 2016-2-18 上午11:06:23
 * @version 1.0
 */
public class MultipartInterceptor extends BaseInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		if (request instanceof MultipartHttpServletRequest) {
			RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		}
		
		return true;
	}
}
