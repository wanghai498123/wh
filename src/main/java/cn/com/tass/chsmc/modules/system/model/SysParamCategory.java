package cn.com.tass.chsmc.modules.system.model;


import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import cn.com.tass.chsmc.modules.common.model.BaseEntity;

/**
 * 标题: 系统参数类型表，属于系统初始化表。
 * <p>
 * 描述: 系统参数类型表，属于系统初始化表。
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
@Entity
@Table(name = "t_sys_param_category")
public class SysParamCategory extends BaseEntity{

	private static final long serialVersionUID = 5101225675673387689L;

	/**
	 * 系统参数分类标识
	 */
	private Integer paramCategoryID;

	/**
	 * 系统参数分类名称
	 */
	private String paramCategoryName;

	/**
	 * 系统参数分类描述
	 */
	private String paramCategoryDesc;

	/**
	 * 参数是否可见：0： 不可见，1：可见。
	 */
	private int isVisable;
	
	private List<SysParam> sysParams;

	@Id
	@GeneratedValue
	public Integer getParamCategoryID(){
		return this.paramCategoryID;
	}
	
	public void setParamCategoryID(Integer paramCategoryID){
		this.paramCategoryID=paramCategoryID;
	}

	public String getParamCategoryName(){
		return this.paramCategoryName;
	}
	
	public void setParamCategoryName(String paramCategoryName){
		this.paramCategoryName=paramCategoryName;
	}

	public String getParamCategoryDesc(){
		return this.paramCategoryDesc;
	}
	
	public void setParamCategoryDesc(String paramCategoryDesc){
		this.paramCategoryDesc=paramCategoryDesc;
	}

	public int getIsVisable(){
		return this.isVisable;
	}
	
	public void setIsVisable(int isVisable){
		this.isVisable=isVisable;
	}
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "sysParamCategory")
	public List<SysParam> getSysParams() {
		return sysParams;
	}

	public void setSysParams(List<SysParam> sysParams) {
		this.sysParams = sysParams;
	}

}
