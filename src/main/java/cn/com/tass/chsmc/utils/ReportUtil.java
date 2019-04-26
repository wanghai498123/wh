package cn.com.tass.chsmc.utils;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cn.com.tass.chsmc.utils.DateUtil.Week;

/**
 * 标题: 报表工具类
 * <p>
 * 描述: 报表工具类
 * <p>
 * 版权: Copyright (c) 2014
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 * 
 * @author jnta
 * @created 2014年8月29日 下午6:29:29
 * @version 1.0
 */
public class ReportUtil {
	/**
	 * 转换String到Map
	 * 
	 * @param content
	 *            待转换内容
	 * @return
	 */
	public static Map<String, Object> changeStringToMap(String content) {
		String[] keyValues = content.split(";");
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < keyValues.length; ++i) {
			String keyValue = keyValues[i];
			String[] split = keyValue.split(",");
			if ((split[0] != null) && (split[1] != null))
				map.put(split[0], split[1]);
		}
		return map;
	}

	/**
	 * 根据传入的map进行替换
	 * 
	 * @param content
	 * @param params
	 * @return
	 */
	public static String replaceParamByMap(String content, Map<String, String> params) {
		if ((params == null) || (content == null))
			return content;
		Set<String> keySet = params.keySet();
		for (String key : keySet) {

			if ("\\$displayField".equals(key)) {
				String replaceVar = (String) params.get(key);
				content = content.replaceAll(key, replaceVar);
				content = content.replaceAll("(BY|by)(.+),(.+?) as (.*?),", "by$2,$4,");
			} else if (("\\$orderField".equals(key)) || ("\\$orderRule".equals(key))) {
				if ("\\$orderField".equals(key)) {
					String oderRule = (String) params.get("\\$orderRule");
					String oderField = (String) params.get(key);
					if (CharUtil.isEmpty(oderRule)) {
						oderField = oderField.replaceAll("\\,", " desc, ");
						content = content.replaceAll("\\$orderRule", "desc");
					} else {
						oderField = oderField.replaceAll("\\,", " " + oderRule + ", ");
						content = content.replaceAll("\\$orderRule", oderRule);
					}
					content = content.replaceAll("stacnt desc", "").replaceAll(key, oderField.replaceAll("levelCNName", "levelValue"));
				}
			} else {
				content = content.replaceAll(key, (String) params.get(key));
			}
		}
		
		if ((content.indexOf("$orderField") != -1) || (content.indexOf("$orderRule") != -1))
			content = content.replaceAll("\\$orderRule", "").replaceAll("\\$orderField", "");
		return content;
	}

	/**
	 * 运行时替换时间
	 * 
	 * @param content
	 *            待替换内容
	 * @return
	 */
	public static String replaceTimeRange(String content) {
		// 获得当前时间
		String currentDate = "";
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		currentDate = simpleDateFormat.format(date);

		// 获得年月
		int month = DateUtil.getMonth(currentDate);
		int year = DateUtil.getYear(currentDate);
		if (month == 0) {
			month = 12;
			year = year - 1;
		} else {
			month = month + 1;
		}
		if (content.indexOf("$everyDay") != -1) {
			simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String tomorrow = simpleDateFormat.format(DateUtil.addDay(date, -1));
			content = content.replaceAll("\\$everyDay\\((.*?)\\) ", " and $1 >= '" + tomorrow + " 00:00:00' and $1 <= '" + tomorrow + " 23:59:59'");
		} else if (content.indexOf("$everyWeek") != -1) {
			// 时间范围 每周处理
			Week week = DateUtil.getWeek(currentDate);
			String startDate = DateUtil.addDay(currentDate, -week.getNumber() - 6);
			String endDate = DateUtil.addDay(currentDate, -week.getNumber());

			// 开始日期月份
			int startDateMonth = DateUtil.getMonth(startDate);
			// 开始日期年份
			int startDateYear = DateUtil.getYear(startDate);
			// 开始日期
			int startDateDay = DateUtil.getDay(startDate);
			if (startDateMonth == 0) {
				startDateMonth = 12;
				startDateYear -= 1;
			} else {
				startDateMonth += 1;
			}

			// 结束日期月份
			int endDateMonth = DateUtil.getMonth(endDate);
			// 结束日期月份
			int endDateYear = DateUtil.getYear(endDate);
			// 结束日期
			int endDateDay = DateUtil.getDay(endDate);
			if (endDateMonth == 0) {
				endDateMonth = 12;
				endDateYear -= 1;
			} else {
				endDateMonth += 1;
			}
			content = content.replaceAll("\\$everyWeek\\((.*?)\\) ", " and $1 >= '" + startDateYear + "-" + startDateMonth + "-" + startDateDay
					+ " 00:00:00' and $1 <= '" + endDateYear + "-" + endDateMonth + "-" + endDateDay + " 23:59:59'");
		} else if (content.indexOf("$everyMonth") != -1) {
			simpleDateFormat = new SimpleDateFormat("yyyy-MM");
			String lastMonth = simpleDateFormat.format(DateUtil.addDay(date, -31));
			content = content.replaceAll("\\$everyMonth\\((.*?)\\)", " and $1 >= '" + lastMonth + "-01 00:00:00" + "' and $1 <= '" + lastMonth
					+ "-31 23:59:59" + "'");
		}
		return content;
	}

	/**
	 * 运行时替换查询条件的时间
	 * 
	 * @param content
	 *            代替换内容
	 * @return
	 */
	public static String replaceSearchTimeRange(String content) {
		// 获得当前时间
		String currentDate = "";
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		currentDate = simpleDateFormat.format(date);

		// 获的年月
		int month = DateUtil.getMonth(currentDate);
		int year = DateUtil.getYear(currentDate);
		if (month == 0) {
			month = 12;
			year = year - 1;
		} else {
			month = month + 1;
		}
		if (content.indexOf("每日") != -1) {
			simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String tomorrow = simpleDateFormat.format(DateUtil.addDay(date, -1));
			content = content.replaceAll("每日", tomorrow + " 00:00:00 ~ " + tomorrow + " 23:59:59");
		} else if (content.indexOf("每周") != -1) {

			Week week = DateUtil.getWeek(currentDate);
			String startDate = DateUtil.addDay(currentDate, -week.getNumber() - 6);
			String endDate = DateUtil.addDay(currentDate, -week.getNumber());
			int startDateMonth = DateUtil.getMonth(startDate);
			int startDateYear = DateUtil.getYear(startDate);
			int startDateDay = DateUtil.getDay(startDate);
			if (startDateMonth == 0) {
				startDateMonth = 12;
				startDateYear = startDateYear - 1;
			} else {
				startDateMonth = startDateYear + 1;
			}

			int endDateMonth = DateUtil.getMonth(endDate);
			int endDateYear = DateUtil.getYear(endDate);
			int endDateDay = DateUtil.getDay(endDate);
			if (endDateMonth == 0) {
				endDateMonth = 12;
				endDateYear -= 1;
			} else {
				endDateMonth += 1;
			}
			content = content.replaceAll("每周", startDateYear + "-" + startDateMonth + "-" + startDateDay + " 00:00:00 ~ " + endDateYear + "-"
					+ endDateMonth + "-" + endDateDay + " 23:59:59");
		} else if (content.indexOf("每月") != -1) {
			simpleDateFormat = new SimpleDateFormat("yyyy-MM");
			String lastMonth = simpleDateFormat.format(DateUtil.addDay(date, -31));
			content = content.replaceAll("每月", lastMonth + "-01 00:00:00" + " ~ " + lastMonth + "-31 23:59:59");
		}
		return content;
	}

	/**
	 * 获的字节流
	 * 
	 * @param file
	 *            文件
	 * @return 字节流
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public static byte[] getByte(File file) throws Exception {
		byte[] bytes = null;
		if (file != null) {
			FileInputStream inputStream = new FileInputStream(file);
			int length = (int) file.length();
			if (length > Integer.MAX_VALUE)
				return null;
			bytes = new byte[length];
			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length) {
				if ((numRead = inputStream.read(bytes, offset, bytes.length - offset)) < 0)
					break;
				offset += numRead;
			}

			// 如果得到的字节长度和file实际的长度不一致就可能出错
			if (offset < bytes.length)
				return null;
			inputStream.close();
		}
		return bytes;
	}
}