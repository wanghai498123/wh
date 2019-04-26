//package cn.com.tass.chsmc.interceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//
///**
// * 标题: 
// * <p>
// * 描述: 
// * <p>
// * 版权: Copyright (c) 2016
// * <p>
// * 公司: 江南天安 [www.tass.com.cn]
// * <p>
// * 
// * @author 卢灿 [lucan@tass.com.cn]
// * @created 2016-2-26 上午11:49:35
// * @version 1.0
// */
//public class ExecuteTimeInterceptor extends HandlerInterceptorAdapter{
//	
//	private static final Logger logger = Logger.getLogger(ExecuteTimeInterceptor.class);
//
//	//before the actual handler will be executed
//	public boolean preHandle(HttpServletRequest request, 
//			HttpServletResponse response, Object handler)
//	    throws Exception {
//		
//		long startTime = System.currentTimeMillis();
//		request.setAttribute("startTime", startTime);
//		
//		return true;
//	}
//
//	//after the handler is executed
//	public void postHandle(
//			HttpServletRequest request, HttpServletResponse response, 
//			Object handler, ModelAndView modelAndView)
//			throws Exception {
//		
//		long startTime = (Long)request.getAttribute("startTime");
//		
//		long endTime = System.currentTimeMillis();
//
//		long executeTime = endTime - startTime;
//		
//		//modified the exisitng modelAndView
//		modelAndView.addObject("executeTime",executeTime);
//		
//		//log it
//		if(logger.isDebugEnabled()){
//			logger.debug("[" + handler + "] executeTime : " + executeTime + "ms");
//		}
//	}
//}
