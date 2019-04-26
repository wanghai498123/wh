package cn.com.tass.chsmc.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * 
 * 标题: 字符串处理类
 * <p>
 * 描述: 字符串辅助工具类
 * <p>
 * 版权: Copyright (c) 2014
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 * 
 * @author jnta
 * @created 2014年8月29日 上午9:40:34
 * @version 1.0
 */
public class CharUtil {

	/**
	 * 判断对象是否为空
	 * 
	 * @param obj
	 * @return 是否为空 true：为空, false :不为空
	 */
	public static boolean isEmpty(Object obj) {
		if ((obj != null) && (obj instanceof String))
			obj = obj.toString().trim();

		if (obj != null && !"".equals(obj) && !"null".equals(obj)) {
			return false;
		}

		return true;
	}

	/**
	 * 对象转字符串
	 * 
	 * @param obj
	 *            对象
	 * @return 字符串
	 */
	public static String objectToString(Object obj) {
		if (isEmpty(obj)) {
			return "";
		} else {
			return obj.toString();
		}
	}

	/**
	 * 对象null庄空字符串
	 * 
	 * @param obj
	 * @return 返回转换后的字符串和对象
	 */
	public static Object nullToString(Object obj) {
		if (isEmpty(obj))
			return "";
		return obj;
	}

	/**
	 * 将数组用指定的连接符拼接成字符串
	 * 
	 * @param array
	 * @param separator
	 * @return
	 */
	public static String concatString(Object[] array, String separator) {
		String result = "";
		for (Object obj : array) {
			if (isEmpty(result)) {
				result = obj.toString();
			} else {
				result += "," + obj.toString();
			}
		}
		return result;
	}

	/**
	 * 字符串转换为日期格式
	 * 
	 * @param s
	 * @param s1
	 *            日期格式 yy-mm-dd等
	 * @return 转换后的日期
	 * @throws ParseException
	 */
	public static Date stringToDate(String s, String s1) throws ParseException {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(s1);
		try {
			return simpleDateFormat.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new ParseException("日期操作错误", 0);
		}
	}

	/**
	 * 日期转换为字符串
	 * 
	 * @param date
	 * @param format
	 *            日期格式 yy-mm-dd等
	 * @return
	 */
	public static String dateToString(Date date, String format) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		return simpleDateFormat.format(date);
	}

	/**
	 * 判断指定字符串是否存在另一个字符串内
	 * 
	 * @param s
	 * @param array
	 * @return
	 */
	public static boolean isInString(String s, String array) {

		boolean flag = false;
		if ((isEmpty(array)) || (isEmpty(s))) {
			flag = false;
		} else {
			String[] data = array.split(",");
			for (String temp : data) {
				if (s.trim().equals(temp.trim())) {
					flag = true;
					break;
				}
			}
		}

		return flag;
	}

	/**
	 * 资产价值格式验证
	 * 
	 * @param str
	 * @return
	 */
	public static boolean validAssetValue(String str) {
		if ((str == null) || ("".equals(str)))
			return false;

		Pattern pattern = Pattern.compile("^[1-5]{1}$|^5.0|^[1-4]{1}(.[0-9]{1})?$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 判断最多连续字个数
	 * 
	 * @param str
	 *            字符串
	 * @return 连续字个数
	 */
	public static int continuousCount(String str) {
		int count = 1;
		int tempCount = 1;
		for (int i = 0; i < str.length(); ++i)
			for (int j = i + 1; j < str.length(); ++j)
				if (str.charAt(j) == str.charAt(i)) {
					++tempCount;
				} else {
					if (tempCount > count) {
						count = tempCount;
						tempCount = 0;
					}
					i = j - 1;
					break;
				}
		return count;
	}

	/**
	 * 格式化字符串为多行
	 * 
	 * @param str
	 *            待格式化的字符串
	 * @param lineLen
	 *            单行字符串长度
	 * @return 格式化后的字符串
	 */
	public static String formatStrToRows(String str, int lineLen) {
		String res = "";
		if (!(isEmpty(str))) {
			char[] cs = str.replaceAll("\\s{2,}", " ").toCharArray();
			for (int i = 0; i < cs.length; ++i)
				if ((i != 0) && (i != cs.length - 1) && (i % lineLen == 0))
					res = res + "\n" + cs[i];
				else
					res = res + cs[i];
		}
		return res;
	}

	/**
	 * 拼接下拉框初始化sql
	 * 
	 * @param fieldName
	 *            字段名称
	 * @param sID
	 *            编号或者编号组
	 * @return 返回拼接后的sql语句
	 */
	public static String getInitSelectSql(String fieldName, String sID) {
		String res = "";
		if (!(isEmpty(sID)))
			if (sID.indexOf(",") != -1) {
				sID = sID.replaceAll(",", "','");
				res = res + " and " + fieldName + " in('" + sID + "') ";
			} else {
				res = res + " and " + fieldName + " = '" + sID + "' ";
			}
		return res;
	}

	/**
	 * 文件国际化支持
	 * 
	 * @param key
	 * @return 国际化的字符串
	 */
//	public static String getText(String key) {
//		if (isEmpty(key))
//			return "";
//		String locale = PropertyReader.readPropertiesByKey("/struts.properties", "struts.locale");
//		if ((locale != null) && ("en_us".equals(locale.toLowerCase())))
//			return PropertyReader.readPropertiesByKey("/messageResource_en_US.properties", key);
//		return PropertyReader.readPropertiesByKey("/messageResource_zh_CN.properties", key);
//	}
}