package cn.com.tass.chsmc.modules.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import cn.com.tass.chsmc.modules.common.dao.GenericDao;
import cn.com.tass.chsmc.modules.common.service.impl.GenericServiceImpl;
import cn.com.tass.chsmc.modules.system.dao.PassWordPoliyDao;
import cn.com.tass.chsmc.modules.system.model.PassWordPoliy;
import cn.com.tass.chsmc.modules.system.service.PassWordPoliyService;
import cn.com.tass.chsmc.utils.CharUtil;
import cn.com.tass.chsmc.utils.DataTypeUtils;
import cn.com.tass.chsmc.web.pagination.DtPageable;
import cn.com.tass.chsmc.web.pagination.Page;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 标题: 生成策略 服务实现
 * <p>
 * 描述: 生成策略 服务实现
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
@Service
@Transactional(readOnly=false,propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class PassWordPoliyServiceImpl extends GenericServiceImpl<PassWordPoliy> implements PassWordPoliyService{

	@Autowired
	private PassWordPoliyDao passWordPoliyDao;

	@Override
	public GenericDao<PassWordPoliy> getGenericDao() {
		return this.passWordPoliyDao;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true,propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Page<PassWordPoliy> listPassWordPoliy(DtPageable dtPageable) throws Exception{
			
			List<Object>params = new ArrayList<Object>();
			List<PassWordPoliy>listPassWordPoliy = new ArrayList<PassWordPoliy>();
			String str="";
			String policyName  = dtPageable.getParamMap().get("policyName");
			String isSysDefault = dtPageable.getParamMap().get("isSysDefault");
			if(!(CharUtil.isEmpty(policyName))){
				str = str + " and p.policyName like ?";
				params.add("%"+policyName+"%");
			}
			if(!(CharUtil.isEmpty(isSysDefault))){
		        str = str + " and p.isSysDefault = ?";
		        params.add(Integer.valueOf(isSysDefault));
		    }
		    if (!DataTypeUtils.isEmpty(dtPageable.getOderColsStr())) {
				str = str + dtPageable.getOderColsStr();
			}
		    
		    String sql="select p.policyID,p.policyName,u.loginName,p.updateTime,p.isSysDefault from t_sys_passwordpolicy p left join t_sys_user u on p.creatorID = u.userID where 1=1 "+ str;
		    String sqlCount = "select count(*) from t_sys_passwordpolicy p where 1=1 "+ str;
		    String userCount = "select count(*) from t_sys_user u where u.policyID = ? ";
		    long total = this.passWordPoliyDao.countByNativeSql(sqlCount, params);
		      List localObject1 = this.passWordPoliyDao.executeNativeSqlForQuery(sql + " limit " + ((dtPageable.getPage() - 1) * dtPageable.getRows()) + "," + dtPageable.getRows(), params);
		      Object localObject2 = (localObject1).iterator();
		      while (((Iterator)localObject2).hasNext()){
		        Object localObject3 = ((Iterator)localObject2).next();
		        Object[] arrayOfObject = (Object[])(Object[])localObject3;
		        PassWordPoliy passWordPoliy = new PassWordPoliy();
		        passWordPoliy.setPolicyID((Integer)arrayOfObject[0]);
		        passWordPoliy.setPolicyName((String)arrayOfObject[1]);
		        passWordPoliy.setLoginName((String)arrayOfObject[2]);
		        passWordPoliy.setUpdateTime((Timestamp) arrayOfObject[3]);
		        if(true==(Boolean)arrayOfObject[4]){
		        	passWordPoliy.setIsSysDefault(1);
		        }else{
		        	passWordPoliy.setIsSysDefault(0);
		        }
		        List<Object>param = new ArrayList<Object>();
		        param.add((Integer)arrayOfObject[0]);
		        long userPolicyCount = this.passWordPoliyDao.countByNativeSql(userCount, param);
		        if(userPolicyCount>0){
		        	passWordPoliy.setIsUsing(1);
		        }else{
		        	passWordPoliy.setIsUsing(0);
		        }
		        listPassWordPoliy.add(passWordPoliy);
		      }
			 Page<PassWordPoliy> PassWordPoliyPages=new Page<PassWordPoliy>();
			 PassWordPoliyPages.setiTotalRecords(listPassWordPoliy.size());
			 PassWordPoliyPages.setiTotalDisplayRecords(total);
			 PassWordPoliyPages.setAaData(listPassWordPoliy);
			 
			 return PassWordPoliyPages;
	}

	
}
