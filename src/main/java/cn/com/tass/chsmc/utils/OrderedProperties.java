package cn.com.tass.chsmc.utils;

import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Properties;

/**
 * 标题: 按顺序输出的Properties类
 * <p>
 * 描述: 按顺序输出的Properties类
 * <p>
 * 版权: Copyright (c) 2016
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 * 
 * @author 卢灿 [lucan@tass.com.cn]
 * @created 2016-3-7 下午02:58:32
 * @version 1.0
 */
public class OrderedProperties extends Properties{
	
	private static final long serialVersionUID = 1L;
	
	private final LinkedHashSet<Object> keys = new LinkedHashSet<Object>();

    @Override
    public Enumeration<?> propertyNames() {
        return Collections.enumeration(keys);
    }

    @Override
    public synchronized Enumeration<Object> elements() {
        return Collections.enumeration(keys);
    }

    public Enumeration<Object> keys() {
        return Collections.enumeration(keys);
    }

    public Object put(Object key, Object value) {
        keys.add(key);
        return super.put(key, value);
    }

    @Override
    public synchronized Object remove(Object key) {
        keys.remove(key);
        return super.remove(key);
    }

    @Override
    public synchronized void clear() {
        keys.clear();
        super.clear();
    }
}
