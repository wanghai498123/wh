package cn.com.tass.chsmc.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 标题: 数据类型辅助类
 * <p>
 * 描述: 数据类型辅助函数，类型转换等
 * <p>
 * 版权: Copyright (c) 2015
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 * 
 * @author 卢灿 [lucan@tass.com.cn]
 * @created 2016-2-23 下午09:34:44
 * @version 1.0
 */
public class DataTypeUtils {

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return true 空字符串 false 非空字符串
	 */
	public static boolean isEmpty(String str) {
		if (str == null || (str != null && str.trim().length() == 0)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 获取字符串（如果字符串为空，则返回一个默认字符串）
	 * @param str
	 * @param defStr
	 * @return
	 */
	public static String getEmptyDefStr(String str,String defStr){
		
		if(isEmpty(str))
			return defStr;
		else {
			return str;
		}
	}
	
	/**
	 * 字符串转换成Boolean类型
	 * @param str 待转换字符串
	 * @return  转换后的boolean值
	 */
	public static boolean str2Boolean(String str){
		if(isEmpty(str))
			return false;
		else {
			if(str.trim().toLowerCase().equals("true"))
				return true;
			else {
				return false;
			}
		}
	}
	
	/**
	 * 字符串转换成整形
	 * @param str 待转化字符串
	 * @return 转换后的整形，如果转换失败，默认返回 0
	 */
	public static int str2Int(String str){
		
		return str2Int(str, 0);
	}
	
	/**
	 * 字符串转换成整形
	 * @param str 待转换的字符串
	 * @param defVal 如果转换失败，默认的返回值
	 * @return 转换后的整形值，如果转换失败，默认返回defVal参数
	 */
	public static int str2Int(String str,int defVal){
		
		if(isEmpty(str))
			return defVal;
		else {
			try {
				int val=Integer.valueOf(str);
				return val;
			} catch (Exception e) {
				return defVal;
			}
		}
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
	 * 增加日期的天数。失败返回null
	 * 
	 * @param date
	 *            日期字符串
	 * @param paramInt
	 *            增加数量。可为负数
	 * @return 增加天数后的日期字符串
	 * @throws ParseException 
	 */
	@SuppressWarnings("static-access")
	public static Date addDay(String strDate, int dayAmount) throws ParseException {
		Date date=stringToDate(strDate,"yyyy-MM-dd");
		
		if (date != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(calendar.DATE, dayAmount);
			return calendar.getTime();
		}
		return null;
	}

}
