package cn.com.tass.chsmc.modules.test.dao.impl;

import org.springframework.stereotype.Component;

import cn.com.tass.chsmc.modules.common.dao.impl.GenericDaoImpl;
import cn.com.tass.chsmc.modules.test.dao.TestDao;
import cn.com.tass.chsmc.modules.test.model.Test;

/**
 * 标题: 
 * <p>
 * 描述: 
 * <p>
 * 版权: Copyright (c) 2015
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 * 
 * @author 卢灿 [lucan@tass.com.cn]
 * @created 2016-2-23 下午05:55:12
 * @version 1.0
 */

@Component("testDao")
public class TestDaoImpl extends GenericDaoImpl<Test> implements TestDao {

}
