package cn.com.tass.chsmc.modules.system.service;

import cn.com.tass.chsmc.modules.common.service.GenericService;
import cn.com.tass.chsmc.modules.system.model.PassWordPoliy;

import cn.com.tass.chsmc.web.pagination.DtPageable;
import cn.com.tass.chsmc.web.pagination.Page;

/**
 * 标题: 生成策略 服务接口
 * <p>
 * 描述: 生成策略 服务接口
 * <p>
 * 版权: Copyright (c) 2016
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 * 
 * @author JNTA Codegenerator [lucan@tass.com.cn]
 * @created 2016-03-28 11:52:27
 * @version 1.0
 */
public interface PassWordPoliyService extends  GenericService<PassWordPoliy>{

	Page<PassWordPoliy> listPassWordPoliy(DtPageable dtPageable)  throws Exception ;

}


