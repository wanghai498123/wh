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

import cn.com.tass.chsmc.modules.system.dao.PassWordPoliyDao;
import cn.com.tass.chsmc.modules.system.dao.UserDao;
import cn.com.tass.chsmc.modules.system.model.PassWordPoliy;
import cn.com.tass.chsmc.modules.system.model.User;
import cn.com.tass.chsmc.modules.system.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 标题: 用户 服务实现
 * <p>
 * 描述: 用户 服务实现
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
@Transactional(readOnly=false,propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class UserServiceImpl extends GenericServiceImpl<User> implements UserService{
	
	private static Integer unlock = 1 ;
	private static Integer locking = 3;
	private static String  unType = "2";
	private static String  lockType = "3";
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private PassWordPoliyDao poliyDao;

	@Override
	public GenericDao<User> getGenericDao() {
		return this.userDao;
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Page<User> listUser(DtPageable dtPageable) throws Exception {

		Map<String, Object> param = new HashMap<String, Object>();
		StringBuilder condition = new StringBuilder();

		condition.append(buildSqlByString("u.loginName", dtPageable, param));
		condition.append(buildSqlByString("u.userName", dtPageable, param));
		condition.append(buildSqlByInteger("u.status", dtPageable, param));
		condition.append(buildSqlByDate("u.createTime", "startTime", "endTime", dtPageable, param));
		condition.append(buildSqlByDate("u.lastLoginTime", "lastLoginStartTime", "lastLoginEndTime", dtPageable, param));

		if (!DataTypeUtils.isEmpty(dtPageable.getOderColsStr())) {
			condition.append(" " + dtPageable.getOderColsStr());
		}

		long count = this.userDao.count("select count(*) from User u inner join u.role r where 1=1 " + condition, param);
		List<User> listUser = this.userDao.find("select u from User u inner join u.role r where 1=1 " + condition, param, dtPageable.getPage(), dtPageable.getRows());
		Page<User> userPages = new Page<User>();
		userPages.setiTotalRecords(listUser.size());
		userPages.setiTotalDisplayRecords(count);
		userPages.setAaData(listUser);

		return userPages;
	}

	@Override
	public User getUser(String loginName) throws Exception{
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("loginName", loginName);
		List<User>listUser=this.userDao.find("from User where loginName=:loginName", param);
		User user=null;
		if(listUser!=null && listUser.size()>0){
			user=listUser.get(0);
		}
		return user;
	}
	
	@Override
	public boolean isValidPasswordPolicy(String password, int policyID,
			String userName) throws Exception {
		
		PassWordPoliy passwordPolicy = (PassWordPoliy) poliyDao.get(policyID);
		// 判断长度
		if (password.length() <= passwordPolicy.getMaxLength()
				&& password.length() >= passwordPolicy.getMinLength()) {
			// 密码中数字个数
			int numNum = 0;
			// 密码中字母个数
			int letNum = 0;
			// 密码中特殊字符个数
			int otherNum = 0;
			for (int i = 0; i < password.length(); i++) {
				if (Character.isDigit(password.charAt(i))) {
					numNum++;
				} else if (Character.isLetter(password.charAt(i))) {
					letNum++;
				} else {
					otherNum++;
				}
			}
			// 判断最小包含数字,字母，特殊字符
			if ((passwordPolicy.getContainMinCharacter() == null || (passwordPolicy
					.getContainMinCharacter() != null && letNum >= passwordPolicy
					.getContainMinCharacter()))
					&& (passwordPolicy.getContainMinFigure() == null || (passwordPolicy
							.getContainMinFigure() != null && numNum >= passwordPolicy
							.getContainMinFigure()))
					&& (passwordPolicy.getContainMinSpecialChar() == null || (passwordPolicy
							.getContainMinSpecialChar() != null && otherNum >= passwordPolicy
							.getContainMinSpecialChar()))) {
				
                   return true;
				}
			}
		
		
		
		return false;
	}


	@Override
	public void editUser(Map<String, Object> params, HttpServletRequest request)
			throws SqlException {
		String unTypeHql = "update User u set u.status = :unlock where u.userID = :userID";
		String lockTypeHql = "update User u set u.status = :locking where u.userID = :userID";
		String type = request.getParameter("type");
		if (type.equals(unType)) {
			params.put("unlock", unlock);
			this.userDao.executeHql(unTypeHql, params);
		} else if (type.equals(lockType)) {
			params.put("locking", locking);
			this.userDao.executeHql(lockTypeHql, params);
		}

	}

	@Override
	public void update(Map<String, Object> param) throws Exception {
		String hql = "update User u set u.lastLoginTime = :lastLoginTime where u.userID = :userID";
		this.userDao.executeHql(hql, param);
	}
	
}
