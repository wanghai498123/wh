package cn.com.tass.chsmc.modules.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.tass.chsmc.modules.common.dao.GenericDao;
import cn.com.tass.chsmc.modules.common.service.impl.GenericServiceImpl;
import cn.com.tass.chsmc.modules.system.dao.LogOperationInfoDao;
import cn.com.tass.chsmc.modules.system.model.LogOperationInfo;
import cn.com.tass.chsmc.modules.system.service.LogOperationInfoService;
import cn.com.tass.chsmc.utils.DataTypeUtils;
import cn.com.tass.chsmc.web.pagination.DtPageable;
import cn.com.tass.chsmc.web.pagination.Page;

@Service
public class LogOperationInfoServiceImpl extends GenericServiceImpl<LogOperationInfo> implements
		LogOperationInfoService {

	@Autowired
	private LogOperationInfoDao logOperationInfoDao;

	@Override
	public GenericDao<LogOperationInfo> getGenericDao() {
		return logOperationInfoDao;
	}

	public Page<LogOperationInfo> listLogOperationInfo(DtPageable dtPageable) throws Exception {

		Map<String, Object> param = new HashMap<String, Object>();
		StringBuilder condition = new StringBuilder();

		condition.append(buildSqlByString("ipAddress", dtPageable, param));
		condition.append(buildSqlByString("operatName", dtPageable, param));
		condition.append(buildSqlByDate("operatTime", "startTime", "endTime", dtPageable, param));
		condition.append(buildSqlByString("operatUser", dtPageable, param));
		condition.append(buildSqlByString("operatStatus", dtPageable, param));

		if (!DataTypeUtils.isEmpty(dtPageable.getOderColsStr())) {
			condition.append(" " + dtPageable.getOderColsStr());
		}

		long count = this.logOperationInfoDao.count("select count(*) from LogOperationInfo where 1=1 " + condition,
				param);
		List<LogOperationInfo> logOperationInfoList = this.logOperationInfoDao.find("from LogOperationInfo where 1=1"
				+ condition, param, dtPageable.getPage(), dtPageable.getRows());

		Page<LogOperationInfo> page = new Page<LogOperationInfo>();
		page.setiTotalRecords(logOperationInfoList.size());
		page.setiTotalDisplayRecords(count);
		page.setAaData(logOperationInfoList);

		return page;
	}
}
