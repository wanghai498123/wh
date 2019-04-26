package cn.com.tass.chsmc.utils;

import java.net.InetAddress;

import javax.servlet.http.HttpServletRequest;

/**
 * 标题: 控制器工具类
 * <p>
 * 描述: 控制器工具类
 * <p>
 * 版权: Copyright (c) 2016
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 * @author 卢灿 [lucan@tass.com.cn]
 * @created 2016-2-25 下午06:09:03
 * @version 1.0
 */
public class ControllerUtil {

	/**
	 * 对Action Url请求进行统一化
	 * 
	 * @param url
	 * @return
	 */
	public static String uniformUrl(String url) {

		if (DataTypeUtils.isEmpty(url))
			return "";
		else {
			String uniformUrl = url.trim();

			// 去除前面/
			if (uniformUrl.startsWith("/")) {
				uniformUrl = uniformUrl.substring(1, uniformUrl.length());
			}

			// 去除.后面的所有后缀
			if (uniformUrl.indexOf(".") >= 0) {
				uniformUrl = uniformUrl.substring(0, uniformUrl.indexOf("."));
			}

			return uniformUrl;
		}
	}

	/**
	 * 判断是否为Ajax请求
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isAjax(HttpServletRequest request) {

		// ajax请求头包含"x-requested-with"
		if (!((request.getHeader("accept")!=null && 
			   request.getHeader("accept").indexOf("application/json") > -1) 
			 || (request.getHeader("X-Requested-With") != null 
			  && request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1)
			 )) 
		{
			// 处理Juery.form.js中ajaxSubmit的不提交头信息的问题
			String requestWithVal = request.getParameter("X_REQUESTED_WITH");
			if (requestWithVal != null&& requestWithVal.equals("XMLHttpRequest")) {
				return true;
			}
			return false;
		} else {

			return true;
		}
	}

	/**
	 * 获取请求IP
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String getClientIP(HttpServletRequest request)
			throws Exception {
		String ipAddress = null;
		ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress))
			ipAddress = request.getHeader("Proxy-Client-IP");
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress))
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
				InetAddress inetaddress = null;
				inetaddress = InetAddress.getLocalHost();
				ipAddress = inetaddress.getHostAddress();
			}
		}

		// 对于通过多个代理的情况，第一个IP为客户端真实IP地址，多个IP按照","分割
		if (ipAddress.split(",").length > 1)
			ipAddress = ipAddress.split(",")[0];
		return ipAddress;
	}
}
