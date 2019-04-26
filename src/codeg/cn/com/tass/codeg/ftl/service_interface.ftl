package cn.com.tass.chsmc.modules.${e_ModuleName}.service;

import cn.com.tass.chsmc.modules.common.service.GenericService;
import cn.com.tass.chsmc.modules.${e_ModuleName}.model.${e_ClassName};

import cn.com.tass.chsmc.web.pagination.DtPageable;
import cn.com.tass.chsmc.web.pagination.Page;

/**
 * 标题: ${e_ClassComment} 服务接口
 * <p>
 * 描述: ${e_ClassComment} 服务接口
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
public interface ${e_ClassName}Service extends  GenericService<${e_ClassName}>{

	Page<${e_ClassName}> list${e_ClassName}(DtPageable dtPageable)  throws Exception ;
}


