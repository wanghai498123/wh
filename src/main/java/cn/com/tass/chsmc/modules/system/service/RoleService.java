package cn.com.tass.chsmc.modules.system.service;

import java.util.List;

import cn.com.tass.chsmc.modules.common.service.GenericService;
import cn.com.tass.chsmc.modules.system.model.Role;

import cn.com.tass.chsmc.web.pagination.DtPageable;
import cn.com.tass.chsmc.web.pagination.Page;

/**
 * 标题: 角色 服务接口
 * <p>
 * 描述: 角色 服务接口
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
public interface RoleService extends  GenericService<Role>{

	Page<Role> listRole(DtPageable dtPageable)  throws Exception ;
	List<Role> find(String roleName)throws Exception;
	
	Boolean deleteRole(int[] ids)throws Exception;
}


