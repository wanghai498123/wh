package cn.com.tass.chsmc.web.view;

import java.util.Locale;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

/**
 * 标题: 自定义ViewResolver
 * <p>
 * 描述: 增加redirectEx命令，该命令重定向不添加jsessionId参数
 * <p>
 * 版权: Copyright (c) 2016
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 * 
 * @author 
 * @created 2016-3-1 上午10:11:57
 * @version 1.0
 */
@Configuration
public class ExtendResouceViewResolver extends org.springframework.web.servlet.view.InternalResourceViewResolver {

	private static final String EX_REDIRECT_URL_PREFIX = "redirectEx:";
	
	@Override
	protected View createView(String viewName, Locale locale) throws Exception {
		
		if (viewName.startsWith(EX_REDIRECT_URL_PREFIX)) {
			String redirectUrl = viewName.substring(EX_REDIRECT_URL_PREFIX.length());
			RedirectView view = new ExtendRedirectView(redirectUrl, isRedirectContextRelative());
			return view;
		}
		else
			return super.createView(viewName, locale);
	}
}
