package cn.com.tass.chsmc.modules.common.controller;

import javax.naming.SizeLimitExceededException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.tass.chsmc.interceptor.annotation.IgnoreLogin;

/**
 * 标题: 通用的控制器
 * <p>
 * 描述: 错误页面等
 * <p>
 * 版权: Copyright (c) 2016
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 * 
 * @author 卢灿 [lucan@tass.com.cn]
 * @created 2016-2-26 下午02:57:41
 * @version 1.0
 */
@Controller
public class RootController {
	
	/**
	 * 跳转404页面
	 * @return
	 */
	@RequestMapping(value="/toNotFoundError")
	@IgnoreLogin
	public String toNotFoundError(){
		return "notFound";
	}
	
	@RequestMapping(value="/toNoAuth")
	@IgnoreLogin
	public String toNoAuth() throws Exception{
		return "noAuth";
	}
	
	/**
	 * 跳转登陆页面
	 * @return
	 * @throws SizeLimitExceededException 
	 */
	@RequestMapping(value="/toLogin")
	@IgnoreLogin
	public String toLogin() throws Exception{
		
		return "login";
	}
	
	@RequestMapping(value="/")
	@IgnoreLogin
	public String toWelcome() throws Exception{
		return "redirectEx:/toLogin";
	}
	
	
}
