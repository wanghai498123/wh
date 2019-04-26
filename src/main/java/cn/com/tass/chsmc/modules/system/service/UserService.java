package cn.com.tass.chsmc.modules.system.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.com.tass.chsmc.modules.common.service.GenericService;
import cn.com.tass.chsmc.modules.system.model.User;
import cn.com.tass.chsmc.web.pagination.DtPageable;
import cn.com.tass.chsmc.web.pagination.Page;

/**
 * 标题: 用户 服务接口
 * <p>
 * 描述: 用户 服务接口
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
public interface UserService extends  GenericService<User>{

	Page<User> listUser(DtPageable dtPageable)  throws Exception ;
	
	/**
     *获取登录信息
     */
	User getUser(String loginName)throws Exception;
	
	/**
	 * 判断密码是否符合密码策略
	 * @param password 密码
	 * @param policyID 密码策略
	 * @param userName 用户名
	 * @return true 符合 false 不符合
	 */
	boolean isValidPasswordPolicy(String password, int policyID, String userName) throws Exception;

	
	void editUser(Map<String, Object> param,HttpServletRequest request) throws Exception;
	
	void update(Map<String, Object> param)throws Exception;
	
}


