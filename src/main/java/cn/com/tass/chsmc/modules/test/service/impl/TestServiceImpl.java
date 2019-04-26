package cn.com.tass.chsmc.modules.test.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.tass.chsmc.modules.common.dao.GenericDao;
import cn.com.tass.chsmc.modules.common.service.impl.GenericServiceImpl;
import cn.com.tass.chsmc.modules.test.dao.TestDao;
import cn.com.tass.chsmc.modules.test.model.Test;
import cn.com.tass.chsmc.modules.test.service.TestService;
import cn.com.tass.chsmc.utils.DataTypeUtils;
import cn.com.tass.chsmc.web.pagination.DtPageable;
import cn.com.tass.chsmc.web.pagination.Page;

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
public class TestServiceImpl extends GenericServiceImpl<Test> implements TestService {

	@Autowired
	private TestDao testDao;

	@Override
	public GenericDao<Test> getGenericDao() {
		return this.testDao;
	}

	public Page<Test> listTest(DtPageable dtPageable) {

		try {
			Map<String, Object> param = new HashMap<String, Object>();
			StringBuilder condition = new StringBuilder();

			condition.append(buildSqlByString("testName", dtPageable, param));
			
			//String colValue = dtPageable.getParamMap().get("status");
			//OperateStatus operateStatus = OperateStatus.valueOf(Integer.valueOf(colValue));
			condition.append(" and status=:status");
			//param.put("status", operateStatus);

			if (!DataTypeUtils.isEmpty(dtPageable.getOderColsStr())) {
				condition.append(" " + dtPageable.getOderColsStr());
			}

			long count = this.testDao.count("select count(*) from Test where 1=1 " + condition, param);
			List<Test> listTest = this.testDao.find("from Test where 1=1 " + condition, param, dtPageable.getPage(), dtPageable.getRows());
			Page<Test> test = new Page<Test>();
			test.setiTotalRecords(listTest.size());
			test.setiTotalDisplayRecords(count);
			test.setAaData(listTest);

			return test;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// @Override
	// public Class<?> getEntiyType() {
	// // TODO Auto-generated method stub
	// return Test.class;
	// }

}
