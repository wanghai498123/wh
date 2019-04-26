package cn.com.tass.chsmc.modules.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.tass.chsmc.modules.common.dao.GenericDao;
import cn.com.tass.chsmc.modules.common.service.impl.GenericServiceImpl;
import cn.com.tass.chsmc.web.pagination.DtPageable;
import cn.com.tass.chsmc.web.pagination.Page;

import cn.com.tass.chsmc.modules.system.dao.SysParamDao;
import cn.com.tass.chsmc.modules.system.model.SysParam;
import cn.com.tass.chsmc.modules.system.service.SysParamService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 标题: 系统参数 服务实现
 * <p>
 * 描述: 系统参数 服务实现
 * <p>
 * 版权: Copyright (c) 2016
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 * 
 * @author JNTA Codegenerator [lucan@tass.com.cn]
 * @created 2016-04-02 23:13:24
 * @version 1.0
 */
@Service
@Transactional(readOnly=false,propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SysParamServiceImpl extends GenericServiceImpl<SysParam> implements SysParamService{

	@Autowired
	private SysParamDao sysParamDao;

	@Override
	public GenericDao<SysParam> getGenericDao() {
		return this.sysParamDao;
	}
	
	@Transactional(readOnly=true,propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Page<SysParam> listSysParam(DtPageable dtPageable) throws Exception{
		
		Map<String, Object> param = new HashMap<String, Object>();
	

		 long count=this.sysParamDao.count("select count(*) from SysParam where 1=1 ",param);
		 List<SysParam> listSysParam=	this.sysParamDao.find("from SysParam where 1=1 ", param, dtPageable.getPage(), dtPageable.getRows());
		 Page<SysParam> sysParamPages=new Page<SysParam>();
		 sysParamPages.setiTotalRecords(listSysParam.size());
		 sysParamPages.setiTotalDisplayRecords(count);
		 sysParamPages.setAaData(listSysParam);
		 
		 return sysParamPages;
	}
	@Override
	public List<SysParam>getSysParams()throws Exception{
		List<SysParam>sysParams = this.sysParamDao.find("from SysParam");
		return sysParams;
	}
	@Override
	public SysParam getSysParamByParamName(String paramName)throws Exception{
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("paramName", paramName);
		SysParam sysParam = null;
		List<SysParam>sysParamlist=this.sysParamDao.find(" from SysParam where paramName=:paramName", param);
		if(sysParamlist != null && sysParamlist.size()>0){
			sysParam = sysParamlist.get(0);
		}
		return sysParam;
	}


	@SuppressWarnings("unchecked")
	public void saveSysParams(Map<String, Object> param) throws Exception{
		String hql = "from SysParam where paramName = :paramName";
		SysParam sysparm = new SysParam();
		Map<String, Object> params = new HashMap<String, Object>();
		for (Map.Entry entry : param.entrySet()) {
			String paramName = entry.getKey().toString();
			String paramValue = entry.getValue().toString();
			params.put("paramName", paramName);
			sysparm = this.sysParamDao.get(hql, params);
			sysparm.setParamValue(paramValue);
			this.sysParamDao.save(sysparm);
		}
	}
}
