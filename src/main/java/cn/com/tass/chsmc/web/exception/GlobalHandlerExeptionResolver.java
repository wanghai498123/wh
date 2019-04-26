package cn.com.tass.chsmc.web.exception;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.Date;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.Ordered;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import cn.com.tass.chsmc.constant.CommonConst;
import cn.com.tass.chsmc.exception.GenericBusinessException;
import cn.com.tass.chsmc.exception.NoAuthException;
import cn.com.tass.chsmc.exception.RecordNotFoundException;
import cn.com.tass.chsmc.exception.SqlException;
import cn.com.tass.chsmc.interceptor.annotation.LogAnnotation;
import cn.com.tass.chsmc.modules.system.model.LogExceptionInfo;
import cn.com.tass.chsmc.modules.system.model.LogExceptionInfoDetailInfo;
import cn.com.tass.chsmc.modules.system.service.LogExceptionInfoService;
import cn.com.tass.chsmc.utils.ControllerUtil;
import cn.com.tass.chsmc.utils.DataTypeUtils;
import cn.com.tass.chsmc.web.json.JsonResponse;

import com.alibaba.fastjson.JSON;

/**
 * 标题: 全局异常处理器
 * <p>
 * 描述: 全局异常处理器
 * <p>
 * 版权: Copyright (c) 2016
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 * 
 * @author 
 * @created 2016-2-26 下午04:54:51
 * @version 1.0
 */

@Configuration
public class GlobalHandlerExeptionResolver implements HandlerExceptionResolver, Ordered {

	Logger logger = LoggerFactory.getLogger(GlobalHandlerExeptionResolver.class);

	@Autowired
	MessageSource messageSource;

	@Autowired
	LogExceptionInfoService logExceptionInfoService;

	public int getOrder() {

		return Integer.MIN_VALUE;
	}

	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {

		ModelAndView modelView = new ModelAndView();

		// 记录日志
		// String errTitle=messageSource.getMessage("common.msg.error", null,
		// LocaleContextHolder.getLocale());
		logger.error("", ex);

		//异常日志暂时不保存到数据库中 by lwy in 20170221
		//persistException(request, handler, ex);

		String message = getExceptionMessage(ex);
		// 是否ajax请求
		if (ControllerUtil.isAjax(request)) {
			JsonResponse jsonResponse = new JsonResponse();
			if (ex instanceof LoginException)
				jsonResponse.setRespStatus(CommonConst.STATUS_LOGIN);
			else if (ex instanceof NoAuthException) {
				jsonResponse.setRespStatus(CommonConst.STATUS_NOAUTH);
			} else {
				jsonResponse.setRespStatus(CommonConst.STATUS_ERROR);
			}
			jsonResponse.setRespMessage(message);

			String jsonStr = JSON.toJSONString(jsonResponse);
			try {
				response.setHeader("Content-Type", "text/html;charset=utf-8");
				byte[] resBytes = jsonStr.getBytes("UTF-8");
				OutputStream resOutStream = response.getOutputStream();
				resOutStream.write(resBytes, 0, resBytes.length);
				resOutStream.flush();
				resOutStream.close();
			} catch (IOException e) {
				logger.error("写入应答JSON对象失败", e);
			}

			return null;

		} else {
			// 非异步ajax
			if (ex instanceof LoginException) {
				modelView.setViewName("redirectEx:/toLogin");
				return modelView;
			} else if (ex instanceof NoAuthException) {
				modelView.setViewName("redirectEx:/toNoAuth");
				return modelView;
			} else {
				modelView.getModel().put("errorMsg", message);
				modelView.setViewName("error");
			}
			return modelView;
		}
	}

	/**
	 * 根据异常信息获取异常描述
	 * 
	 * @param ex
	 * @return
	 */
	private String getExceptionMessage(Exception ex) {

		String message = "";
		if (ex instanceof RecordNotFoundException) {
			message = getResourceText("common.exception.RecordNotFoundException");
		} else if (ex instanceof SqlException) {
			message = getResourceText("common.exception.SqlExceprion") + ":" + ex.getLocalizedMessage();
		} else if (ex instanceof GenericBusinessException) {
			message = ((GenericBusinessException) ex).getErrMessage();
		} else {
			message = ex.getLocalizedMessage();
		}

		if (DataTypeUtils.isEmpty(message)) {
			message = getResourceText("common.exception.class") + ":" + ex.getClass().getName();
		}

		return message;
	}

	/**
	 * 获取本地化文本
	 * 
	 * @param textName
	 * @return
	 */
	protected String getResourceText(String textName) {

		return messageSource.getMessage(textName, null, LocaleContextHolder.getLocale());
	}

	private void persistException(HttpServletRequest request, Object handler, Exception ex) {

		// 登陆异常和未授权异常不记录日志
		if (ex instanceof LoginException || ex instanceof NoAuthException)
			return;

		try {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Timestamp exceptionTime = new Timestamp(new Date().getTime());

			// 获取日志的注解
			String operatName = "";
			LogAnnotation log = handlerMethod.getMethodAnnotation(LogAnnotation.class);
			if (log != null) {
				operatName = messageSource.getMessage(log.logName(), null, LocaleContextHolder.getLocale());
			}
			
			// 获取异常发生位置
			String exceptLoaction = handlerMethod.getBean().getClass().getName() + "."
					+ handlerMethod.getMethod().getName();
			if (!DataTypeUtils.isEmpty(operatName))
				exceptLoaction = "[" + operatName + "] " + exceptLoaction;

			StackTraceElement[] st = ex.getStackTrace();
			// 异常详情
			String exceptDetail = ex.getClass().getName() + "\r\n";
			if (st != null) {
				for (int i = 0; i < st.length; i++) {
					exceptDetail += String.format("     %s.%s(%s:%s)\r\n", st[i].getClassName(), st[i].getMethodName(),
							st[i].getFileName(), st[i].getLineNumber());
				}
			}

			LogExceptionInfo logExceptionInfo = new LogExceptionInfo();
			logExceptionInfo.setOperatName(operatName);
			logExceptionInfo.setExceptionTime(exceptionTime);
			logExceptionInfo.setExceptionLocation(exceptLoaction);

			LogExceptionInfoDetailInfo detailInfo = new LogExceptionInfoDetailInfo();
			detailInfo.setExceptDetail(exceptDetail);

			logExceptionInfo.setExceptionInfoDetailInfo(detailInfo);
			this.logExceptionInfoService.save(logExceptionInfo);
		} catch (Exception e) {
			logger.error("save log info failed", ex);
		}

	}

}
