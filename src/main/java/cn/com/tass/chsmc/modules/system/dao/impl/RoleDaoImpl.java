package cn.com.tass.chsmc.modules.system.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import cn.com.tass.chsmc.modules.common.dao.impl.GenericDaoImpl;
import cn.com.tass.chsmc.modules.system.dao.RoleDao;
import cn.com.tass.chsmc.modules.system.model.Role;

/**
 * 标题: 角色 DAO实现
 * <p>
 * 描述: 角色 DAO实现
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
 @Repository
public class RoleDaoImpl extends GenericDaoImpl<Role> implements RoleDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getList(String roleName) {
		String hql = "from Role r join r.list m where roleName = "+"'"+roleName+"'";
		Query q = this.getEntityManager().createQuery(hql);
		List<Object> list = q.getResultList();
		for (int i = 0; i < list.size();i++) {
			Role role =   (Role) list.get(i);
			list.add(role);
			return list;
		}
		return null;
	}
  
}
