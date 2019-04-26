package cn.com.tass.chsmc.modules.common.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import cn.com.tass.chsmc.exception.SqlException;
import cn.com.tass.chsmc.modules.common.model.SelectInit;

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
 * @created 2016-2-23 下午05:56:44
 * @version 1.0
 */
@Transactional
public interface SelectService extends GenericService<SelectInit> {

	List<SelectInit> listSelectInit(String hql, Map<String, Object> param, int page, int rows) throws SqlException;

	List<SelectInit> listselData(String hql, Map<String, Object> param, Integer page, Integer rows) throws SqlException;

	SelectInit getSelectInitByKey(String key) throws SqlException;
	
	public Long count(String hql, Map<String, Object> param) throws SqlException;
}
