package cn.com.tass.chsmc.modules.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.tass.chsmc.modules.common.dao.GenericDao;
import cn.com.tass.chsmc.modules.common.service.impl.GenericServiceImpl;
import cn.com.tass.chsmc.web.pagination.DtPageable;
import cn.com.tass.chsmc.web.pagination.Page;

import cn.com.tass.chsmc.modules.system.dao.SysParamCategoryDao;
import cn.com.tass.chsmc.modules.system.model.SysParamCategory;
import cn.com.tass.chsmc.modules.system.service.SysParamCategoryService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 标题: 系统参数种类 服务实现
 * <p>
 * 描述: 系统参数种类 服务实现
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
public class SysParamCategoryServiceImpl extends GenericServiceImpl<SysParamCategory> implements SysParamCategoryService{

	@Autowired
	private SysParamCategoryDao sysParamCategoryDao;

	@Override
	public GenericDao<SysParamCategory> getGenericDao() {
		return this.sysParamCategoryDao;
	}
	
	@Transactional(readOnly=true,propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<SysParamCategory> listSysParamCategory() throws Exception{
		 List<SysParamCategory> listSysParamCategory=	this.sysParamCategoryDao.find("from SysParamCategory");	 
		 return listSysParamCategory;
	}

	@Override
	public Page<SysParamCategory> pageSysParamCategory(DtPageable dtPageable)
			throws Exception {
		
		 Map<String, Object> param = new HashMap<String, Object>();
		 long count=this.sysParamCategoryDao.count("select count(*) from SysParamCategory where 1=1 ",param);
		 List<SysParamCategory> listSysParamCategory=	this.sysParamCategoryDao.find("from SysParamCategory where 1=1 ", param, dtPageable.getPage(), dtPageable.getRows());
		 Page<SysParamCategory> sysParamCategoryPages=new Page<SysParamCategory>();
		 sysParamCategoryPages.setiTotalRecords(listSysParamCategory.size());
		 sysParamCategoryPages.setiTotalDisplayRecords(count);
		 sysParamCategoryPages.setAaData(listSysParamCategory);		 
		 return sysParamCategoryPages;
	}
}
