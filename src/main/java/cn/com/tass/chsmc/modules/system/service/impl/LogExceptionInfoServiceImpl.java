package cn.com.tass.chsmc.modules.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.tass.chsmc.modules.common.dao.GenericDao;
import cn.com.tass.chsmc.modules.common.service.impl.GenericServiceImpl;
import cn.com.tass.chsmc.modules.system.dao.LogExceptionInfoDao;
import cn.com.tass.chsmc.modules.system.model.LogExceptionInfo;
import cn.com.tass.chsmc.modules.system.service.LogExceptionInfoService;
import cn.com.tass.chsmc.utils.DataTypeUtils;
import cn.com.tass.chsmc.web.pagination.DtPageable;
import cn.com.tass.chsmc.web.pagination.Page;

@Service
public class LogExceptionInfoServiceImpl extends GenericServiceImpl<LogExceptionInfo> implements
		LogExceptionInfoService {
	@Autowired
	private LogExceptionInfoDao dao;

	@Override
	public GenericDao<LogExceptionInfo> getGenericDao() {

		return this.dao;
	}

	public Page<LogExceptionInfo> listLogExceptionInfo(DtPageable dtPageable) throws Exception {

		Map<String, Object> param = new HashMap<String, Object>();
		StringBuilder condition = new StringBuilder();

		condition.append(buildSqlByString("exceptionLocation", dtPageable, param));
		condition.append(buildSqlByDate("exceptionTime", "startTime", "endTime", dtPageable, param));

		if (!DataTypeUtils.isEmpty(dtPageable.getOderColsStr())) {
			condition.append(" " + dtPageable.getOderColsStr());
		}

		long count = this.dao.count("select count(*) from LogExceptionInfo where 1=1 " + condition, param);
		List<LogExceptionInfo> logExceptionInfoList = this.dao.find("from LogExceptionInfo where 1=1" + condition,
				param, dtPageable.getPage(), dtPageable.getRows());

		Page<LogExceptionInfo> page = new Page<LogExceptionInfo>();
		page.setiTotalRecords(logExceptionInfoList.size());
		page.setiTotalDisplayRecords(count);
		page.setAaData(logExceptionInfoList);

		return page;
	}

}
