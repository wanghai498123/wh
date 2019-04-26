package cn.com.tass.chsmc.modules.common.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.tass.chsmc.exception.SqlException;
import cn.com.tass.chsmc.modules.common.dao.GenericDao;
import cn.com.tass.chsmc.modules.common.dao.SelectDao;
import cn.com.tass.chsmc.modules.common.model.SelectInit;
import cn.com.tass.chsmc.modules.common.service.SelectService;


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
 * @created 2016-2-23 下午05:57:32
 * @version 1.0
 */
@Service
@Transactional
public class SelectServiceImpl  extends GenericServiceImpl<SelectInit> implements SelectService{
	
	@Autowired
	private SelectDao selectInitDao;

	@Override
	public GenericDao<SelectInit> getGenericDao() {
		return this.selectInitDao;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SelectInit getSelectInitByKey(String key) throws SqlException {
		try {
			List param = new ArrayList();
			param.add(key);
			return selectInitDao.get(" from SelectInit where selKey = ?", param);
		} catch (Exception e) {
			throw new SqlException(e);
		}
	}

	@Override
	public List<SelectInit> listSelectInit(String hql,
			Map<String, Object> param, int page, int rows) throws SqlException {
		try {
			if (page > 0 && rows > 0) {
				return selectInitDao.find(hql, param, page, rows);
			}
			return selectInitDao.find(hql, param);

		} catch (Exception e) {
			throw new SqlException(e);
		}
	}

	@Override
	public List<SelectInit> listselData(String hql, Map<String, Object> param,
			Integer page, Integer rows) throws SqlException {
		try {
			if (page > 0 && rows > 0) {
				return selectInitDao.find(hql, param, page, rows);
			}
			return selectInitDao.find(hql, param);
		} catch (Exception e) {
			throw new SqlException(e);
		}
	}

	@Override
	public Long count(String hql, Map<String, Object> param)
			throws SqlException {
		return selectInitDao.count(hql, param);
	}




}
