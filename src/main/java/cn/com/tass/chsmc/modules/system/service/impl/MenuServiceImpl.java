package cn.com.tass.chsmc.modules.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.tass.chsmc.exception.SqlException;
import cn.com.tass.chsmc.modules.common.dao.GenericDao;
import cn.com.tass.chsmc.modules.common.service.impl.GenericServiceImpl;
import cn.com.tass.chsmc.modules.system.dao.MenuDao;
import cn.com.tass.chsmc.modules.system.model.Menu;
import cn.com.tass.chsmc.modules.system.service.MenuService;

/**
 * 标题: 菜单服务实现类
 * <p>
 * 描述: 菜单服务实现类
 * <p>
 * 版权: Copyright (c) 2016
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 * 
 * @author 卢灿 [lucan@tass.com.cn]
 * @created 2016-2-25 上午10:01:05
 * @version 1.0
 */
@Service
@Transactional
public class MenuServiceImpl extends GenericServiceImpl<Menu> implements MenuService{

	@Autowired
	private MenuDao menuDao;
	
	@Override
	public GenericDao<Menu> getGenericDao() {
		
		return menuDao;
	}

	public List<Menu> getMenuByRoleId(int id) throws SqlException {
		
		List<Menu> menus=this.menuDao.find("from Menu");
		
		return menus;
	}

	@Override
	public List<Menu> getMenuList() throws SqlException {
		List<Menu> menus = this.menuDao.find("from Menu");
		return menus;
	}

}
