package cn.com.tass.chsmc.modules.system.service;

import java.util.List;

import cn.com.tass.chsmc.exception.SqlException;
import cn.com.tass.chsmc.modules.common.service.GenericService;
import cn.com.tass.chsmc.modules.system.model.Menu;

/**
 * 标题: 菜单服务类
 * <p>
 * 描述: 菜单服务类
 * <p>
 * 版权: Copyright (c) 2016
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 * 
 * @author 卢灿 [lucan@tass.com.cn]
 * @created 2016-2-25 上午09:45:19
 * @version 1.0
 */
public interface MenuService extends  GenericService<Menu>{

	/**
	 * 根据角色ID获取菜单
	 * @param id
	 * @return
	 */
	List<Menu> getMenuByRoleId(int id) throws SqlException;

	/**
	 *获取菜单列表 
	 */
	List<Menu> getMenuList()throws SqlException;
}
