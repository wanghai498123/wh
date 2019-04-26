/**
 * <p>Title: </p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: Complete Solution(GD)Limited</p>
 * @author zhaofl
 * @date 3:36:32 PM , Aug 4, 2006
 * @version 1.0
 */
package cn.com.tass.chsmc.utils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

public class MapDataUtil {
    public static Long getLong(Map<String, Object> map, String key) {
        Object val = map.get(key);
        if (val != null)
            return new Long(val.toString());
        else
            return null;
    }

    public static String getString(Map<String, Object> map, String key) {
        Object val = map.get(key);
        if (val != null)
            return val.toString();
        else
            return "";
    }

    public static Integer getInteger(Map<String, Object> map, String key) {
        Object val = map.get(key);
        if (val != null)
            return new Integer(val.toString());
        else
            return null;
    }

    public static Double getDouble(Map<String, Object> map, String key) {
        Object val = map.get(key);
        if (val != null)
            return new Double(val.toString());
        else
            return null;
    }

    public static Timestamp getTimestamp(Map<String, Object> map, String key) {
        return (Timestamp) map.get(key);
    }

    public static Date getDate(Map<String, Object> map, String key) {
        return (Date) map.get(key);
    }
    
    /**
	 * 共通方法 获取map的value
	 * @param map 集合
	 * @param key 索引
	 * @param defaultValue 默认值
	 * @return 值
	 * @throws Exception
	 */
	public static String getString(Map<String, Object> map,String key,String defaultValue) throws Exception {
		
		Object val = map.get(key);
        if (val != null)
            return val.toString();
        else
            return defaultValue;
	}
}
