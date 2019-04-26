package cn.com.tass.chsmc.modules.${e_ModuleName}.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.tass.chsmc.modules.common.dao.GenericDao;
import cn.com.tass.chsmc.modules.common.service.impl.GenericServiceImpl;
import cn.com.tass.chsmc.web.pagination.DtPageable;
import cn.com.tass.chsmc.web.pagination.Page;

import cn.com.tass.chsmc.modules.${e_ModuleName}.dao.${e_ClassName}Dao;
import cn.com.tass.chsmc.modules.${e_ModuleName}.model.${e_ClassName};
import cn.com.tass.chsmc.modules.${e_ModuleName}.service.${e_ClassName}Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 标题: ${e_ClassComment} 服务实现
 * <p>
 * 描述: ${e_ClassComment} 服务实现
 * <p>
 * 版权: Copyright (c) 2016
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 * 
 * @author JNTA Codegenerator
 * @created ${e_SysTime?string("yyyy-MM-dd HH:mm:ss")}
 * @version 1.0
 */
@Service
@Transactional(readOnly=false,propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ${e_ClassName}ServiceImpl extends GenericServiceImpl<${e_ClassName}> implements ${e_ClassName}Service{

	@Autowired
	private ${e_ClassName}Dao ${e_ClassName?uncap_first}Dao;

	@Override
	public GenericDao<${e_ClassName}> getGenericDao() {
		return this.${e_ClassName?uncap_first}Dao;
	}
	
	@Transactional(readOnly=true,propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Page<${e_ClassName}> list${e_ClassName}(DtPageable dtPageable) throws Exception{
		
		Map<String, Object> param = new HashMap<String, Object>();
	

		 long count=this.${e_ClassName?uncap_first}Dao.count("select count(*) from ${e_ClassName} where 1=1 ",param);
		 List<${e_ClassName}> list${e_ClassName}=	this.${e_ClassName?uncap_first}Dao.find("from ${e_ClassName} where 1=1 ", param, dtPageable.getPage(), dtPageable.getRows());
		 Page<${e_ClassName}> ${e_ClassName?uncap_first}Pages=new Page<${e_ClassName}>();
		 ${e_ClassName?uncap_first}Pages.setiTotalRecords(list${e_ClassName}.size());
		 ${e_ClassName?uncap_first}Pages.setiTotalDisplayRecords(count);
		 ${e_ClassName?uncap_first}Pages.setAaData(list${e_ClassName});
		 
		 return ${e_ClassName?uncap_first}Pages;
	}
}
