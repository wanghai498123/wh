package cn.com.tass.chsmc.modules.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.tass.chsmc.exception.SqlException;
import cn.com.tass.chsmc.modules.common.dao.GenericDao;
import cn.com.tass.chsmc.modules.common.service.impl.GenericServiceImpl;
import cn.com.tass.chsmc.utils.DataTypeUtils;
import cn.com.tass.chsmc.web.pagination.DtPageable;
import cn.com.tass.chsmc.web.pagination.Page;

import cn.com.tass.chsmc.modules.system.dao.RoleDao;
import cn.com.tass.chsmc.modules.system.model.Role;
import cn.com.tass.chsmc.modules.system.service.RoleService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 标题: 角色 服务实现
 * <p>
 * 描述: 角色 服务实现
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
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class RoleServiceImpl extends GenericServiceImpl<Role> implements RoleService {

	@Autowired
	private RoleDao roleDao;

	@Override
	public GenericDao<Role> getGenericDao() {
		return this.roleDao;
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Page<Role> listRole(DtPageable dtPageable) throws Exception {

		Map<String, Object> param = new HashMap<String, Object>();
		StringBuilder condition = new StringBuilder();

		condition.append(buildSqlByString("roleName", dtPageable, param));
		condition.append(buildSqlByInteger("isSysDefault", dtPageable, param));
		
		if (!DataTypeUtils.isEmpty(dtPageable.getOderColsStr())) {
			condition.append(" " + dtPageable.getOderColsStr());
		}

		List<Role> listRole = this.roleDao.find("from Role where 1=1 " + condition, param, dtPageable.getPage(), dtPageable.getRows());
		long count = this.roleDao.count("select count(*) from Role");

		Page<Role> rolePages = new Page<Role>();
		rolePages.setiTotalRecords(listRole.size());
		rolePages.setiTotalDisplayRecords(count);
		rolePages.setAaData(listRole);

		return rolePages;
	}

	@Override
	public List<Role> find(String roleName) throws SqlException {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "from Role r join r.list m where roleName = :roleName";
		if (!DataTypeUtils.isEmpty(roleName)) {
			params.put("roleName", roleName);
		}
		List<Role> role = this.roleDao.find(hql, params);
		return role;
	}

	@Override
	public Boolean deleteRole(int[] ids) throws Exception {
		for (int id : ids) {
			Role role = this.roleDao.get(id);
			this.roleDao.executeNativeSqlForUpdate("delete from t_sys_role_menu where roleID=" + role.getRoleID(), null);
			this.roleDao.delete(role);
		}
		return true;
	}
}
