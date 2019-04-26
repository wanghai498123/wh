package cn.com.tass.chsmc.modules.system.dao;

import java.util.List;
import cn.com.tass.chsmc.modules.common.dao.GenericDao;
import cn.com.tass.chsmc.modules.system.model.Role;

/**
 * 标题: 角色 DAO接口
 * <p>
 * 描述: 角色 DAO接口
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
public interface RoleDao extends  GenericDao<Role>{
   
	
	List<Object>  getList(String roleName);
	

}

