package cn.com.tass.chsmc.interceptor;

import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.method.HandlerMethod;

import cn.com.tass.chsmc.constant.CommonConst;
import cn.com.tass.chsmc.constant.SessionConst;
import cn.com.tass.chsmc.interceptor.annotation.LogAnnotation;
import cn.com.tass.chsmc.modules.system.model.LogOperationInfo;
import cn.com.tass.chsmc.modules.system.model.User;
import cn.com.tass.chsmc.modules.system.service.LogOperationInfoService;
import cn.com.tass.chsmc.utils.ControllerUtil;

/**
 * 标题: 日志拦截器
 * <p>
 * 描述: 日志拦截器
 * <p>
 * 版权: Copyright (c) 2016
 * <p>
 * 公司:
 * <p>
 * 
 * @author
 * @created 2016-3-7 下午03:21:39
 * @version 1.0
 */
public class LoggingInterceptor extends BaseInterceptor {

	Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

	@Autowired
	MessageSource messageSource;

	@Autowired
	private LogOperationInfoService logService;

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Timestamp opDateTime = new Timestamp(new Date().getTime());
		LogOperationInfo info = new LogOperationInfo();

		// 获取日志的注解
		LogAnnotation log = handlerMethod.getMethodAnnotation(LogAnnotation.class);
		if (log != null) {

			HttpSession session = request.getSession();

			// 获取客户端IP
			String ipAddress = ControllerUtil.getClientIP(request);
			User loginUser = (User) session.getAttribute(SessionConst.UserInfo);
			String operatName = messageSource.getMessage(log.logName(), null, LocaleContextHolder.getLocale());
			if (ex != null) {
				info.setOperatStatus(CommonConst.STATUS_ERROR);
			} else {
				info.setOperatStatus(CommonConst.STATUS_SUCCESS);
			}

			info.setOperatName(operatName);
			info.setIpAddress(ipAddress);
			if (loginUser != null) {
				info.setOperatUser(loginUser.getUserName());
			} else {
				info.setOperatUser("");
			}
			info.setOperatTime(opDateTime);

			logService.save(info);
		}
	}
}
