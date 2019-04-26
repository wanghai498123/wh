package cn.com.tass.chsmc.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 标题: 日期辅助工具类
 * <p>
 * 描述: 日期辅助工具类
 * <p>
 * 版权: Copyright (c) 2014
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 * 
 * @author jnta
 * @created 2014年8月29日 上午10:04:56
 * @version 1.0
 */
public class DateUtil {

	/**
	 * 获取SimpleDateFormat对象
	 * 
	 * @param pattern
	 *            日期格式
	 * @return SimpleDateFormat对象
	 * @throws RuntimeException
	 *             异常：非法日期格式
	 */
	private static SimpleDateFormat getDateFormat(String pattern)
			throws RuntimeException {
		return new SimpleDateFormat(pattern);
	}

	/**
	 *日期比较大小
	 *@return boolean
	 * @throws ParseException 
	 */
	
	public static boolean compareTime(String startTime, String endTime)
			throws ParseException {
		boolean time = false;
		Date a = DateUtil.stringToDate(startTime);
		Date b = DateUtil.stringToDate(endTime);
		if (b.before(a)) {
			time = true;
		}
		return time;
	}



	/**
	 *获取字符串转换sql日期格式
	 * 
	 *@return
	 * @throws ParseException
	 */

	public static Date stringToDate(String time) throws ParseException {
		Date sqlDate;
		if(time==null||time.equals(""))
			return null;
		
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");	
			sqlDate = dateFormat.parse(time);
			return sqlDate;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取日期中的某数值。如获取月份
	 * 
	 * @param date
	 *            日期
	 * @param dateType
	 *            日期格式
	 * @return 数值
	 */
	private static int getInteger(Date date, int dateType) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(dateType);
	}

	/**
	 * 增加日期中某类型的某数值。如增加日期
	 * 
	 * @param date
	 *            日期
	 * @param dateType
	 *            类型
	 * @param amount
	 *            数值
	 * @return 计算后日期
	 */
	private static String addInteger(String date, int dateType, int amount) {
		String dateString = null;
		DateStyle dateStyle = getDateStyle(date);
		if (dateStyle != null) {
			Date myDate = StringToDate(date, dateStyle);
			myDate = addInteger(myDate, dateType, amount);
			dateString = DateToString(myDate, dateStyle);
		}
		return dateString;
	}

	/**
	 * 增加日期中某类型的某数值。如增加日期
	 * 
	 * @param date
	 *            日期
	 * @param dateType
	 *            类型
	 * @param amount
	 *            数值
	 * @return 计算后的日期
	 */
	private static Date addInteger(Date date, int dateType, int amount) {
		Date myDate = null;
		if (date != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(dateType, amount);
			myDate = calendar.getTime();
		}
		return myDate;
	}

	/**
	 * 获取精确地日期
	 * 
	 * @param timestamps
	 *            时间long集合
	 * @return 日期
	 */
	private static Date getAccurateDate(List<Long> timestamps) {
		Date date = null;
		long timestamp = 0;
		Map<Long, long[]> map = new HashMap<Long, long[]>();
		List<Long> absoluteValues = new ArrayList<Long>();

		if ((timestamps != null) && (timestamps.size() > 0))
			if (timestamps.size() > 1) {
				for (int i = 0; i < timestamps.size(); ++i)
					for (int j = i + 1; j < timestamps.size(); ++j) {
						long absoluteValue = Math.abs(((Long) timestamps.get(i)).longValue() - ((Long) timestamps.get(j)).longValue());
						absoluteValues.add(Long.valueOf(absoluteValue));
						long[] timestampTmp = { ((Long) timestamps.get(i)).longValue(), ((Long) timestamps.get(j)).longValue() };
						map.put(Long.valueOf(absoluteValue), timestampTmp);
					}

				// 有可能相等的情况。 如2012-11和2012-11-01。时间戳是相等的
				long minAbsoluteValue = -1L;
				if (!(absoluteValues.isEmpty())) {
					// 如果timestamps的size为2，这是差值只有一个，因此要给默认值
					minAbsoluteValue = absoluteValues.get(0);
				}
				for (int i = 0; i < absoluteValues.size(); ++i) {
					for (int j = i + 1; j < absoluteValues.size(); ++j) {
						if (absoluteValues.get(i) > absoluteValues.get(j))
							minAbsoluteValue = absoluteValues.get(j);
						else
							minAbsoluteValue = absoluteValues.get(i);
					}
				}

				if (minAbsoluteValue != -1L) {
					long[] timestampsLastTmp = map.get(minAbsoluteValue);
					if (absoluteValues.size() > 1) {
						timestamp = Math.max(timestampsLastTmp[0], timestampsLastTmp[1]);
					} else if (absoluteValues.size() == 1) {
						// 当timestamps的size为2，需要与当前时间作为参照
						long dateOne = timestampsLastTmp[0];
						long dateTwo = timestampsLastTmp[1];
						if (Math.abs(dateOne - dateTwo) < 100000000000L) {
							timestamp = Math.max(timestampsLastTmp[0], timestampsLastTmp[1]);
						} else {
							long now = new Date().getTime();
							if (Math.abs(dateOne - now) <= Math.abs(dateTwo - now))
								timestamp = dateOne;
							else
								timestamp = dateTwo;
						}
					}
				}
			} else {
				timestamp = timestamps.get(0);
			}

		if (timestamp != 0)
			date = new Date(timestamp);
		return date;
	}

	/**
	 * 判断字符串是否为日期字符串
	 * 
	 * @param date
	 *            日期字符串
	 * @return
	 */
	public static boolean isDate(String date) {
		boolean isDate = false;
		if ((date != null) && (StringToDate(date) != null))
			isDate = true;
		return isDate;
	}

	/**
	 * 获取日期字符串的日期风格，失败返回null
	 * 
	 * @param date
	 *            日期字符串
	 * @return 日期风格
	 */
	public static DateStyle getDateStyle(String date) {
		DateStyle dateStyle = null;
		Map<Long, DateStyle> map = new HashMap<Long, DateStyle>();
		List<Long> timestamps = new ArrayList<Long>();

		for (DateStyle style : DateStyle.values()) {
			Date dateTemp = StringToDate(date, style.getValue());
			if (dateTemp != null) {
				timestamps.add(dateTemp.getTime());
				map.put(Long.valueOf(dateTemp.getTime()), style);
			}
		}
		dateStyle = map.get(getAccurateDate(timestamps).getTime());
		return dateStyle;
	}

	/**
	 * 将日期字符串转化为日期。失败返回null
	 * 
	 * @param date
	 *            日期字符串
	 * @return 日期
	 */
	public static Date StringToDate(String date) {
		DateStyle dateStyle = null;
		return StringToDate(date, dateStyle);
	}

	/**
	 * 将日期字符串转换为日期。失败返回null
	 * 
	 * @param date
	 *            日期字符串
	 * @param pattern
	 *            日期
	 * @return
	 */
	public static Date StringToDate(String date, String pattern) {
		Date myDate = null;
		if (date != null)
			try {
				myDate = getDateFormat(pattern).parse(date);
			} catch (Exception e) {
			}
		return myDate;
	}

	/**
	 * 将日期字符串转化为日期，失败返回null
	 * 
	 * @param date
	 *            日期字符串
	 * @param dateStyle
	 *            日期风格
	 * @return
	 */
	public static Date StringToDate(String date, DateStyle dateStyle) {
		Date myDate = null;
		if (dateStyle == null) {
			List<Long> timestamps = new ArrayList<Long>();

			for (DateStyle style : DateStyle.values()) {
				Date dateTemp = StringToDate(date, style.getValue());
				if (dateTemp != null)
					timestamps.add(dateTemp.getTime());
			}
			myDate = getAccurateDate(timestamps);
		} else {
			myDate = StringToDate(date, dateStyle.getValue());
		}
		return myDate;
	}
	


	/**
	 * 将日期转化为日期字符串。失败返回null
	 * 
	 * @param date
	 *            日期
	 * @param pattern
	 *            日期格式
	 * @return 日期字符串
	 */
	public static String DateToString(Date date, String pattern) {
		String dateStr = null;
		if (date != null)
			try {
				dateStr = getDateFormat(pattern).format(date);
			} catch (Exception e) {
			}
		return dateStr;
	}

	/**
	 * 将日期转换为日期字符串，失败返回null
	 * 
	 * @param date
	 *            日期
	 * @param dateStyle
	 *            日期风格
	 * @return 日期字符串
	 */
	public static String DateToString(Date date, DateStyle dateStyle) {
		String dateStr = null;
		if (dateStyle != null)
			dateStr = DateToString(date, dateStyle.getValue());
		return dateStr;
	}

	/**
	 * 将日期字符串转化为另一日期字符串，失败返回null
	 * 
	 * @param date
	 *            旧日期字符串
	 * @param pattern
	 *            新日期格式
	 * @return 新日期字符串
	 */
	public static String StringToString(String date, String pattern) {
		return StringToString(date, null, pattern);
	}

	/**
	 * 将日期字符串转化为另一日期字符串，失败返回null
	 * 
	 * @param date
	 *            旧日期字符串
	 * @param dateStyle
	 *            新日期风格
	 * @return 新日期字符串
	 */
	public static String StringToString(String date, DateStyle dateStyle) {
		return StringToString(date, null, dateStyle);
	}

	/**
	 * 将日期字符串转化为另一日期字符串。失败返回null
	 * 
	 * @param date
	 *            旧日期字符串
	 * @param oldPattern
	 *            旧日期格式
	 * @param newPattern
	 *            新日期格式
	 * @return 新日期字符串
	 */
	public static String StringToString(String date, String oldPattern, String newPattern) {

		String dateString = null;
		if (oldPattern == null) {
			DateStyle style = getDateStyle(date);
			if (style != null) {
				Date myDate = StringToDate(date, style.getValue());
				dateString = DateToString(myDate, newPattern);
			}
		} else {
			Date myDate = StringToDate(date, oldPattern);
			dateString = DateToString(myDate, newPattern);
		}
		return dateString;
	}

	
	/**
	* 字符串转换成日期
	* @param str
	* @return date
	*/
	public static Date StrToDate(String str) {
	  
	   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   Date date = null;
	   try {
	    date = format.parse(str);
	   } catch (ParseException e) {
	    e.printStackTrace();
	   }
	   return date;
	}

	/**
	 * 将日期字符串转化为另一日期字符串。失败返回null
	 * 
	 * @param date
	 *            旧日期字符串
	 * @param oldDateStyle
	 *            旧日期风格
	 * @param newDateStyle
	 *            新日期风格
	 * @return 新日期字符串
	 */
	public static String StringToString(String date, DateStyle oldDateStyle, DateStyle newDateStyle) {
		String dateString = null;
		if (oldDateStyle == null) {
			DateStyle style = getDateStyle(date);
			dateString = StringToString(date, style.getValue(), newDateStyle.getValue());
		} else {
			dateString = StringToString(date, oldDateStyle.getValue(), newDateStyle.getValue());
		}
		return dateString;
	}

	/**
	 * 增加日期的年份，失败返回null
	 * 
	 * @param date
	 *            日期
	 * @param yearAmount
	 *            增加数量。可为负数
	 * @return 增加年份后的日期字符串
	 */
	public static String addYear(String date, int yearAmount) {
		return addInteger(date, Calendar.YEAR, yearAmount);
	}

	/**
	 * 增加日期的年份。失败返回null
	 * 
	 * @param date
	 *            日期
	 * @param yearAmount
	 *            增加数量。可为负数
	 * @return 增加年份后的日期字符串
	 */
	public static Date addYear(Date date, int yearAmount) {
		return addInteger(date, Calendar.YEAR, yearAmount);
	}

	/**
	 * 增加日期的月份。失败返回null
	 * 
	 * @param date
	 *            日期
	 * @param monthAmount
	 *            增加数量。可为负数
	 * @return 增加月份后的日期
	 */
	public static String addMonth(String date, int monthAmount) {
		return addInteger(date, Calendar.MONTH, monthAmount);
	}

	/**
	 * 增加日期的月份。失败返回null
	 * 
	 * @param date
	 *            日期
	 * @param monthAmount
	 *            增加数量。可为负数
	 * @return 增加月份后的日期
	 */
	public static Date addMonth(Date date, int monthAmount) {
		return addInteger(date, Calendar.MONTH, monthAmount);
	}

	/**
	 * 增加日期的天数。失败返回null
	 * 
	 * @param date
	 *            日期字符串
	 * @param paramInt
	 *            增加数量。可为负数
	 * @return 增加天数后的日期字符串
	 */
	public static String addDay(String date, int dayAmount) {
		return addInteger(date, Calendar.DATE, dayAmount);
	}

	public static Date addDay(Date date, int dayAmount) {
		return addInteger(date, Calendar.DATE, dayAmount);
	}

	/**
	 * 增加日期的小时。失败返回null
	 * 
	 * @param date
	 *            日期字符串
	 * @param hourAmount
	 *            增加数量。可为负数
	 * @return 增加小时后的日期字符串
	 */
	public static String addHour(String date, int hourAmount) {
		return addInteger(date, Calendar.HOUR_OF_DAY, hourAmount);
	}

	/**
	 * 增加日期的小时。失败返回null
	 * 
	 * @param date
	 *            日期字符串
	 * @param hourAmount
	 *            增加数量。可为负数
	 * @return 增加小时后的日期字符串
	 */
	public static Date addHour(Date date, int hourAmount) {
		return addInteger(date, Calendar.HOUR_OF_DAY, hourAmount);
	}

	/**
	 * 增加日期的分钟。失败返回null
	 * 
	 * @param date
	 *            日期字符串
	 * @param minuteAmount
	 *            增加数量。可为负数
	 * @return 增加分钟后的日期字符串
	 */
	public static String addMinute(String date, int minuteAmount) {
		return addInteger(date, Calendar.MINUTE, minuteAmount);
	}

	/**
	 * 增加日期的分钟。失败返回null
	 * 
	 * @param date
	 *            日期字符串
	 * @param minuteAmount
	 *            增加数量。可为负数
	 * @return 增加分钟的日期
	 */
	public static Date addMinute(Date date, int minuteAmount) {
		return addInteger(date, Calendar.MINUTE, minuteAmount);
	}

	/**
	 * 增加日期的秒数。失败返回null
	 * 
	 * @param date
	 *            日期字符串
	 * @param secondAmount
	 *            增加数量。可为负数
	 * @return 增加秒数后的日期字符串
	 */
	public static String addSecond(String date, int secondAmount) {
		return addInteger(date, Calendar.SECOND, secondAmount);
	}

	/**
	 * 增加日期的秒数。失败返回null
	 * 
	 * @param date
	 *            日期
	 * @param secondAmount
	 *            增加数量。可为负数
	 * @return 增加秒数后的日期
	 */
	public static Date addSecond(Date date, int secondAmount) {
		return addInteger(date, Calendar.SECOND, secondAmount);
	}

	/**
	 * 获取日期的年份。失败返回0
	 * 
	 * @param date
	 *            日期
	 * @return 年份
	 */
	public static int getYear(String date) {
		return getYear(StringToDate(date));
	}

	/**
	 * 获取日期的年份。失败返回0
	 * 
	 * @param date
	 * @return
	 */
	public static int getYear(Date date) {
		return getInteger(date, Calendar.YEAR);
	}

	/**
	 * 获取日期的月份。失败返回0
	 * 
	 * @param date
	 *            日期
	 * @return 月份
	 */
	public static int getMonth(String date) {
		return getMonth(StringToDate(date));
	}

	/**
	 * 获取日期的月份。失败返回0
	 * 
	 * @param date
	 *            日期
	 * @return 月份
	 */
	public static int getMonth(Date date) {
		return getInteger(date, 2);
	}

	/**
	 * 获取日期的天数。失败返回0
	 * 
	 * @param date
	 *            日期
	 * @return 天
	 */
	public static int getDay(String date) {
		return getDay(StringToDate(date));
	}

	/**
	 * 获取日期的天数。失败返回0
	 * 
	 * @param date
	 *            日期
	 * @return 天
	 */
	public static int getDay(Date date) {
		return getInteger(date, Calendar.DATE);
	}

	/**
	 * 获取日期的小时。失败返回0
	 * 
	 * @param date
	 *            日期字符串
	 * @return 小时
	 */
	public static int getHour(String date) {
		return getHour(StringToDate(date));
	}

	/**
	 * 获取日期的小时。失败返回0
	 * 
	 * @param date
	 *            日期
	 * @return 小时
	 */
	public static int getHour(Date date) {
		return getInteger(date, Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取日期的分钟。失败返回0
	 * 
	 * @param date
	 *            日期字符串
	 * @return 分钟
	 */
	public static int getMinute(String date) {
		return getMinute(StringToDate(date));
	}

	/**
	 * 获取日期的分钟。失败返回0
	 * 
	 * @param date
	 *            日期
	 * @return 分钟
	 */
	public static int getMinute(Date date) {
		return getInteger(date, Calendar.MINUTE);
	}

	/**
	 * 获取日期的秒数。失败返回0
	 * 
	 * @param date
	 *            日期字符串
	 * @return 秒数
	 */
	public static int getSecond(String date) {
		return getSecond(StringToDate(date));
	}

	/**
	 * 获取日期的秒数。失败返回0
	 * 
	 * @param date
	 *            日期
	 * @return 秒数
	 */
	public static int getSecond(Date date) {
		return getInteger(date, Calendar.SECOND);
	}

	/**
	 * 获取日期。默认yyyy-MM-dd格式。失败返回null
	 * 
	 * @param date
	 *            日期字符串
	 * @return 格式化日期字符串
	 */
	public static String getDate(String date) {
		return StringToString(date, DateStyle.YYYY_MM_DD);
	}

	/**
	 * 获取日期。默认yyyy-MM-dd格式。失败返回null
	 * 
	 * @param date
	 *            日期
	 * @return 格式化日期字符串
	 */
	public static String getDate(Date date) {
		return DateToString(date, DateStyle.YYYY_MM_DD_HH_MM_SS);
	}

	/**
	 * 获取日期的时间。默认HH:mm:ss格式。失败返回null
	 * 
	 * @param date
	 *            日期字符串
	 * @return 时间
	 */
	public static String getTime(String date) {
		return StringToString(date, DateStyle.HH_MM_SS);
	}

	/**
	 * 获取日期的时间。默认HH:mm:ss格式。失败返回null
	 * 
	 * @param date
	 *            日期
	 * @return 时间
	 */
	public static String getTime(Date date) {
		return DateToString(date, DateStyle.HH_MM_SS);
	}

	/**
	 * 获取日期的星期。失败返回null
	 * 
	 * @param date
	 *            日期字符串
	 * @return 星期
	 */
	public static Week getWeek(String date) {
		Week week = null;
		DateStyle dateStyle = getDateStyle(date);
		if (dateStyle != null) {
			Date myDate = StringToDate(date, dateStyle);
			week = getWeek(myDate);
		}
		return week;
	}

	/**
	 * 获取日期的星期。失败返回null
	 * 
	 * @param date
	 *            日期
	 * @return 星期
	 */
	public static Week getWeek(Date date) {
		Week week = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int weekNumber = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		switch (weekNumber) {
		case 0:
			week = Week.SUNDAY;
			break;
		case 1:
			week = Week.MONDAY;
			break;
		case 2:
			week = Week.TUESDAY;
			break;
		case 3:
			week = Week.WEDNESDAY;
			break;
		case 4:
			week = Week.THURSDAY;
			break;
		case 5:
			week = Week.FRIDAY;
			break;
		case 6:
			week = Week.SATURDAY;
		}
		return week;
	}

	/**
	 * 获取两个日期相差的天数
	 * 
	 * @param date
	 *            日期字符串
	 * @param otherDate
	 *            另一个日期字符串
	 * @return 相差天数
	 */
	public static int getIntervalDays(String date, String otherDate) {
		return getIntervalDays(StringToDate(date), StringToDate(otherDate));
	}

	/**
	 * 获取两个日期相差的天数
	 * 
	 * @param date
	 *            日期
	 * @param otherDate
	 *            另一个日期
	 * @return 相差天数
	 */
	public static int getIntervalDays(Date date, Date otherDate) {
		date = StringToDate(getDate(date));
		long time = Math.abs(date.getTime() - otherDate.getTime());
		return ((int) time / (24 * 60 * 60 * 1000));
	}

	/**
	 * long型转为String类型
	 * 
	 * @param longTime
	 *            日期
	 * @param format
	 *            格式
	 * @return 转换后的字符串
	 */
	public static String longToString(long longTime, String format) {

		try {
			Timestamp timestamp = new Timestamp(longTime * 1000);
			SimpleDateFormat sDateFormat = new SimpleDateFormat(format);
			return sDateFormat.format(timestamp);
		} catch (Exception localException) {
			throw new RuntimeException("Can't format the time by format[" + format + "]!");
		}
	}


	public enum DateStyle {
		MM_DD("MM-dd"), 
		YYYY_MM("yyyy-MM"), 
		YYYY_MM_DD("yyyy-MM-dd"),
		MM_DD_HH_MM("MM-dd HH:mm"), 
		MM_DD_HH_MM_SS("MM-dd HH:mm:ss"), 
		YYYY_MM_DD_HH_MM("yyyy-MM-dd HH:mm"), 
		YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss"),

		MM_DD_EN("MM/dd"),
		YYYY_MM_EN("yyyy/MM"), 
		YYYY_MM_DD_EN("yyyy/MM/dd"), 
		MM_DD_HH_MM_EN("MM/dd HH:mm"),
		MM_DD_HH_MM_SS_EN("MM/dd HH:mm:ss"), 
		YYYY_MM_DD_HH_MM_EN("yyyy/MM/dd HH:mm"),
		YYYY_MM_DD_HH_MM_SS_EN("yyyy/MM/dd HH:mm:ss"),

		MM_DD_CN("MM月dd日"), 
		YYYY_MM_CN("yyyy年MM月"), 
		YYYY_MM_DD_CN("yyyy年MM月dd日"), 
		MM_DD_HH_MM_CN("MM月dd日 HH:mm"), 
		MM_DD_HH_MM_SS_CN("MM月dd日 HH:mm:ss"), 
		YYYY_MM_DD_HH_MM_CN("yyyy年MM月dd日 HH:mm"), 
		YYYY_MM_DD_HH_MM_SS_CN("yyyy年MM月dd日 HH:mm:ss"),

		HH_MM("HH:mm"),
		HH_MM_SS("HH:mm:ss");

		private String value;

		private DateStyle(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	public static enum Week {
		/**
		 * 星期一
		 */
		MONDAY("星期一", "Monday", "Mon.", 1),
		/**
		 * 星期二
		 */
		TUESDAY("星期二", "Tuesday", "Tues.", 2),
		/**
		 * 星期三
		 */
		WEDNESDAY("星期三", "Wednesday", "Wed.", 3),
		/**
		 * 星期四
		 */
		THURSDAY("星期四", "Thursday", "Thur.", 4),
		/**
		 * 星期五
		 */
		FRIDAY("星期五", "Friday", "Fri.", 5),
		/**
		 * 星期六
		 */
		SATURDAY("星期六", "Saturday", "Sat.", 6),
		/**
		 * 星期日
		 */
		SUNDAY("星期天", "Sunday", "Sun.", 7);

		String name_cn;
		String name_en;
		String name_enShort;
		int number;

		Week(String name_cn, String name_en, String name_enShort, int number) {

			this.name_cn = name_cn;
			this.name_en = name_en;
			this.name_enShort = name_enShort;

			this.number = number;
		}

		public String getChineseName() {
			return this.name_cn;
		}

		public String getName() {
			return this.name_en;
		}

		public String getShortName() {
			return this.name_enShort;
		}

		public int getNumber() {
			return this.number;
		}
	}
}