package cn.com.tass.chsmc.modules.common.service.impl;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.tass.chsmc.exception.RecordNotFoundException;
import cn.com.tass.chsmc.exception.SqlException;
import cn.com.tass.chsmc.modules.common.dao.GenericDao;
import cn.com.tass.chsmc.modules.common.service.GenericService;
import cn.com.tass.chsmc.utils.DataTypeUtils;
import cn.com.tass.chsmc.web.pagination.DtPageable;

/**
 * 标题: 服务基类
 * <p>
 * 描述: 服务抽象基类，公共操作
 * <p>
 * 版权: Copyright (c) 2015
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 * 
 * @author 卢灿 [lucan@tass.com.cn]
 * @created 2016-2-23 上午12:19:05
 * @version 1.0
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public abstract class GenericServiceImpl<T> implements GenericService<T> {

	public void delete(T obj) throws SqlException {

		this.getGenericDao().delete(obj);
	}

	public T getById(int id) throws SqlException {

		return this.getGenericDao().get(id);
	}

	public void save(T obj) throws SqlException {

		this.getGenericDao().save(obj);

	}

	public void update(T obj) throws SqlException {
		this.getGenericDao().update(obj);
	}

	public void deleteByIds(int[] ids) throws SqlException, RecordNotFoundException {

		for (int i = 0; i < ids.length; i++) {

			T t = this.getById(ids[i]);
			if (t == null) {
				throw new RecordNotFoundException();
			}
			this.delete(t);
		}

	}

	@Override
	public Integer executeHql(String hql, Map<String, Object> param) throws SqlException {
		return this.getGenericDao().executeHql(hql, param);
	}
	
	@Override
	public Integer executeNativeSqlForUpdate(String sql,List<Object> param)throws SqlException{
		return this.getGenericDao().executeNativeSqlForUpdate(sql, param);
	}

	/**
	 * 组装查询条件
	 * 
	 * @param colName
	 *            查询的列名称可以带前缀
	 * @param op
	 *            SQL操作符
	 * @param dtPageable
	 * @param param
	 * @return
	 * @throws ParseException
	 */
	@Override
	public String buildSqlByDate(String colName, String startName, String endName, DtPageable dtPageable, Map<String, Object> param) throws SqlException, ParseException {
		
		return buildSqlByDate( colName,  startName,  endName,  dtPageable.getParamMap(), param);
	}
	
	public String buildSqlByDate(String colName, String startName, String endName, Map<String, String> searchParam, Map<String, Object> param) throws SqlException, ParseException {
		StringBuilder sBuilder = new StringBuilder();
		String colPrefix = "";

		int index = colName.indexOf(".");
		if (index != -1) {
			colPrefix = colName.substring(0, index);
			colName = colName.substring(index + 1);
		}

		String startTime = searchParam.get(startName);
		String endTime = searchParam.get(endName);

		if (!DataTypeUtils.isEmpty(startTime)) {
			sBuilder.append(" and ");
			if (index != -1) {
				sBuilder.append(colPrefix);
				sBuilder.append(".");
			}
			sBuilder.append(colName);
			sBuilder.append(" >=");
			sBuilder.append(" :");
			sBuilder.append(startName);
			param.put(startName, DataTypeUtils.stringToDate(startTime, "yyyy-MM-dd"));
		}

		if (!DataTypeUtils.isEmpty(endTime)) {
			sBuilder.append(" and ");
			if (index != -1) {
				sBuilder.append(colPrefix);
				sBuilder.append(".");
			}
			sBuilder.append(colName);
			sBuilder.append(" <");
			sBuilder.append(" :");
			sBuilder.append(endName);
			param.put(endName, DataTypeUtils.addDay(endTime, 1));
		}

		return sBuilder.toString();
	}

	/**
	 * 组装查询条件
	 * 
	 * @param colName
	 *            查询的列名称可以带前缀
	 * @param op
	 *            SQL操作符
	 * @param dtPageable
	 * @param param
	 * @return
	 */
	@Override
	public String buildSqlByInteger(String colName, DtPageable dtPageable, Map<String, Object> param) throws SqlException {
		
		return buildSqlByInteger( colName,  dtPageable.getParamMap(), param);
	}
	public String buildSqlByInteger(String colName, Map<String, String> searchParam, Map<String, Object> param) throws SqlException {
		StringBuilder sBuilder = new StringBuilder();
		String colValue = "";
		String colPrefix = "";

		int index = colName.indexOf(".");
		if (index != -1) {
			colPrefix = colName.substring(0, index);
			colName = colName.substring(index + 1);
		}

		colValue = searchParam.get(colName);
		if (!DataTypeUtils.isEmpty(colValue)) {
			sBuilder.append(" and ");
			if (index != -1) {
				sBuilder.append(colPrefix);
				sBuilder.append(".");
			}
			sBuilder.append(colName);
			sBuilder.append(" ");
			sBuilder.append("=");
			sBuilder.append(" :");
			sBuilder.append(colName);

			param.put(colName, Integer.valueOf(colValue));
		}

		return sBuilder.toString();
	}

	/**
	 * 组装查询条件
	 * 
	 * @param colName
	 *            查询的列名称可以带前缀
	 * @param op
	 *            SQL操作符
	 * @param dtPageable
	 * @param param
	 * @return
	 */
	@Override
	public String buildSqlByFloat(String colName, DtPageable dtPageable, Map<String, Object> param) throws SqlException {
		
		return buildSqlByFloat(colName,dtPageable.getParamMap(),param);
	}
	
	public String buildSqlByFloat(String colName, Map<String, String> searchParam, Map<String, Object> param) throws SqlException {
		StringBuilder sBuilder = new StringBuilder();
		String colValue = "";
		String colPrefix = "";

		int index = colName.indexOf(".");
		if (index != -1) {
			colPrefix = colName.substring(0, index);
			colName = colName.substring(index + 1);
		}

		colValue = searchParam.get(colName);
		if (!DataTypeUtils.isEmpty(colValue)) {
			sBuilder.append(" and ");
			if (index != -1) {
				sBuilder.append(colPrefix);
				sBuilder.append(".");
			}
			sBuilder.append(colName);
			sBuilder.append(" ");
			sBuilder.append("=");
			sBuilder.append(" :");
			sBuilder.append(colName);

			param.put(colName, Float.valueOf(colValue));
		}

		return sBuilder.toString();
	}

	/**
	 * 组装查询条件
	 * 
	 * @param colName
	 *            查询的列名称可以带前缀
	 * @param op
	 *            SQL操作符
	 * @param dtPageable
	 * @param param
	 * @return
	 */
	@Override
	public String buildSqlByLong(String colName, DtPageable dtPageable, Map<String, Object> param) throws SqlException {
		
		return buildSqlByLong(colName, dtPageable.getParamMap(),param);
	}
	
	public String buildSqlByLong(String colName, Map<String, String> searchParam, Map<String, Object> param) throws SqlException {
		StringBuilder sBuilder = new StringBuilder();
		String colValue = "";
		String colPrefix = "";

		int index = colName.indexOf(".");
		if (index != -1) {
			colPrefix = colName.substring(0, index);
			colName = colName.substring(index + 1);
		}

		colValue = searchParam.get(colName);
		if (!DataTypeUtils.isEmpty(colValue)) {
			sBuilder.append(" and ");
			if (index != -1) {
				sBuilder.append(colPrefix);
				sBuilder.append(".");
			}
			sBuilder.append(colName);
			sBuilder.append(" ");
			sBuilder.append("=");
			sBuilder.append(" :");
			sBuilder.append(colName);

			param.put(colName, Long.valueOf(colValue));
		}

		return sBuilder.toString();
	}

	/**
	 * 组装查询条件
	 * 
	 * @param colName
	 *            查询的列名称可以带前缀
	 * @param op
	 *            SQL操作符
	 * @param dtPageable
	 * @param param
	 * @return
	 */
	@Override
	public String buildSqlByString(String colName, DtPageable dtPageable, Map<String, Object> param) throws SqlException {
		return buildSqlByString(colName, dtPageable.getParamMap(), param);
	}
	
	public String buildSqlByString(String colName, Map<String, String> searchParam, Map<String, Object> param) throws SqlException {
		StringBuilder sBuilder = new StringBuilder();
		String colValue = "";
		String colPrefix = "";

		int index = colName.indexOf(".");
		if (index != -1) {
			colPrefix = colName.substring(0, index);
			colName = colName.substring(index + 1);
		}

		colValue =searchParam.get(colName);
		if (!DataTypeUtils.isEmpty(colValue)) {
			sBuilder.append(" and ");
			if (index != -1) {
				sBuilder.append(colPrefix);
				sBuilder.append(".");
			}
			sBuilder.append(colName);
			sBuilder.append(" like");
			sBuilder.append(" :");
			sBuilder.append(colName);

			param.put(colName, "%" + colValue + "%");
		}

		return sBuilder.toString();
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public abstract GenericDao<T> getGenericDao();

}
