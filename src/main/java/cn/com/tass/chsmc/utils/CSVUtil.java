package cn.com.tass.chsmc.utils;

import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 描述: CSV导出工具类
 * <p>
 * 版权: Copyright (c) 2016
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 * 
 * @author 卢灿 [lucan@tass.com.cn]
 * @created 2017-2-8 下午02:24:29
 * @version 1.0
 */
public class CSVUtil {

	@SuppressWarnings("unchecked")
	public static void export(String fileName, String[] title, String[] key, List<Map> values,
			HttpServletResponse response, HttpServletRequest request) throws Exception {

		setCSVResponseHeader(fileName, request, response);

		PrintWriter out = response.getWriter();

		String titleLine = "";
		for (String titleName : title) {
			titleLine += titleName + ",";
		}
		if (titleLine.endsWith(",")) {
			titleLine = titleLine.substring(0,titleLine.length() - 1);
		}

		// 写入列头
		out.println(titleLine);

		// 写入内容
		for (Map value : values) {
			String contentLine = "";
			for (String keyName : key) {
				String keyVal = value.get(keyName).toString();
				//如果文本内容中包含逗号， 将逗号转为句号add by lwy
				keyVal = keyVal.replaceAll(",", ".");
				contentLine += keyVal + ",";
			}
			if (titleLine.endsWith(",")) {
				contentLine = contentLine.substring(0,contentLine.length() - 1);
			}
			out.print(contentLine);
			out.println();
		}
		out.flush();
	}

	/**
	 * 设置CSV导出HTTP头设置
	 * @param fileName
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private static void setCSVResponseHeader(String fileName, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		response.reset();
		response.setContentType("application/csv;charset=gbk");
		String agent = request.getHeader("User-Agent");
		boolean isMSIE = (agent != null && agent.toLowerCase().matches(".*(msie\\s|trident.*rv:)([\\w.]+).*"));
		if (isMSIE) {
			response.setHeader("Content-disposition", "attachment;filename=\"" + URLEncoder.encode(fileName + ".CSV", "UTF8") + "\"");
		} else {
			response.setHeader("Content-disposition", "attachment;filename=" + new String((fileName + ".CSV").getBytes(), "ISO8859-1"));
		}
	}
}
