package cn.com.tass.chsmc.modules.system.service;

import java.util.List;
import java.util.Map;

import cn.com.tass.chsmc.modules.common.service.GenericService;
import cn.com.tass.chsmc.modules.system.model.SysParam;
import cn.com.tass.chsmc.web.pagination.DtPageable;
import cn.com.tass.chsmc.web.pagination.Page;

/**
 * 标题: 系统参数 服务接口
 * <p>
 * 描述: 系统参数 服务接口
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
public interface SysParamService extends  GenericService<SysParam>{

	Page<SysParam> listSysParam(DtPageable dtPageable)  throws Exception ;
	
	void saveSysParams(Map<String, Object> param) throws Exception;
	
	SysParam getSysParamByParamName(String paramName)throws Exception;
	
	List<SysParam> getSysParams()throws Exception;
}


