package cn.com.tass.chsmc.modules.common.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import cn.com.tass.chsmc.exception.RecordNotFoundException;
import cn.com.tass.chsmc.exception.SqlException;
import cn.com.tass.chsmc.web.pagination.DtPageable;

/**
 * 标题: 服务基类接口
 * <p>
 * 描述: 服务通用操作抽象化基类接口
 * <p>
 * 版权: Copyright (c) 2015
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 * 
 * @author 卢灿 [lucan@tass.com.cn]
 * @created 2016-2-23 上午12:18:04
 * @version 1.0
 */
public interface GenericService<T> {

	T getById(int id) throws SqlException;

	void save(T obj) throws SqlException;

	void update(T obj) throws SqlException;

	void delete(T obj) throws SqlException;
	
	Integer executeHql(String hql, Map<String, Object> param)throws SqlException;
	
	Integer executeNativeSqlForUpdate(String sql,List<Object> param)throws SqlException;
	
	void deleteByIds(int[] ids) throws SqlException ,RecordNotFoundException; 
	
	String buildSqlByDate(String colName, String startName,String endName, DtPageable dtPageable, Map<String, Object> param) throws SqlException, ParseException;
	
	String buildSqlByInteger(String colName,DtPageable dtPageable, Map<String, Object> param) throws SqlException;
	
	String buildSqlByString(String colName,DtPageable dtPageable, Map<String, Object> param) throws SqlException;

	String buildSqlByFloat(String colName, DtPageable dtPageable,Map<String, Object> param) throws SqlException;

	String buildSqlByLong(String colName, DtPageable dtPageable, Map<String, Object> param) throws SqlException;
	
    String buildSqlByDate(String colName, String startName,String endName, Map<String, String> searchParam, Map<String, Object> param) throws SqlException, ParseException;
	
	String buildSqlByInteger(String colName,Map<String, String> searchParam, Map<String, Object> param) throws SqlException;
	
	String buildSqlByString(String colName,Map<String, String> searchParam, Map<String, Object> param) throws SqlException;

	String buildSqlByFloat(String colName,Map<String, String> searchParam,Map<String, Object> param) throws SqlException;

	String buildSqlByLong(String colName, Map<String, String> searchParam, Map<String, Object> param) throws SqlException;
}
