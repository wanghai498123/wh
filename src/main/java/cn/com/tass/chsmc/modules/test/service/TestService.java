package cn.com.tass.chsmc.modules.test.service;

import org.springframework.transaction.annotation.Transactional;

import cn.com.tass.chsmc.modules.common.service.GenericService;
import cn.com.tass.chsmc.modules.test.model.Test;
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
 * @created 2016-2-23 下午05:56:44
 * @version 1.0
 */
@Transactional
public interface TestService extends GenericService<Test> {
	
	Page<Test> listTest(DtPageable dtPageable);
}
