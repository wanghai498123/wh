package cn.com.tass.chsmc.modules.system.model;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.com.tass.chsmc.modules.common.model.BaseEntity;

/**
 * 标题: 系统参数表，属于系统初始化表
WebServer：读/写 
 * <p>
 * 描述: 系统参数表，属于系统初始化表
WebServer：读/写 
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
@Table(name = "t_sys_param_init")
public class SysParam extends BaseEntity{

	private static final long serialVersionUID = 5101225675673387689L;

	/**
	 * 参数标识
	 */
	private Integer paramID;

	/**
	 * 参数名称
	 */
	private String paramName;

	/**
	 * 该参数的显示名称
	 */
	private String paramDisplayName;

	/**
	 * 参数所属分类标识
	 */
	private SysParamCategory sysParamCategory;

	/**
	 * 参数值
	 */
	private String paramValue;

	/**
	 * 显示类型:
            1文本
            2下拉列表
            3单选
      

      4数值
            5密码
	 */
	private Integer displayType;

	/**
	 * 0：明文存放 
            1：加密存放 
	 */
	private Integer isEncrypted;

	/**
	 * 参数单位
	 */
	private String unit;

	/**
	 * 参数描述
	 */
	private String paramDesc;

	/**
	 * 是否可修改
            0：不可修改 
            1：可修改
	 */
	private Integer isModifiable;

	/**
	 * 页面提示信息
	 */
	private String tips;

	/**
	 * 页面用JS校验类型
	 */
	private Integer verificationType;

	@Id
	@GeneratedValue
	public Integer getParamID(){
		return this.paramID;
	}
	
	public void setParamID(Integer paramID){
		this.paramID=paramID;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "paramCategoryID", referencedColumnName = "paramCategoryID",nullable = true)
	public SysParamCategory getSysParamCategory() {
		return sysParamCategory;
	}

	public void setSysParamCategory(SysParamCategory sysParamCategory) {
		this.sysParamCategory = sysParamCategory;
	}


	public String getParamName(){
		return this.paramName;
	}
	
	public void setParamName(String paramName){
		this.paramName=paramName;
	}

	public String getParamDisplayName(){
		return this.paramDisplayName;
	}
	
	public void setParamDisplayName(String paramDisplayName){
		this.paramDisplayName=paramDisplayName;
	}



	public String getParamValue(){
		return this.paramValue;
	}
	
	public void setParamValue(String paramValue){
		this.paramValue=paramValue;
	}

	public Integer getDisplayType(){
		return this.displayType;
	}
	
	public void setDisplayType(Integer displayType){
		this.displayType=displayType;
	}

	public Integer getIsEncrypted(){
		return this.isEncrypted;
	}
	
	public void setIsEncrypted(Integer isEncrypted){
		this.isEncrypted=isEncrypted;
	}

	public String getUnit(){
		return this.unit;
	}
	
	public void setUnit(String unit){
		this.unit=unit;
	}

	public String getParamDesc(){
		return this.paramDesc;
	}
	
	public void setParamDesc(String paramDesc){
		this.paramDesc=paramDesc;
	}

	public Integer getIsModifiable(){
		return this.isModifiable;
	}
	
	public void setIsModifiable(Integer isModifiable){
		this.isModifiable=isModifiable;
	}

	public String getTips(){
		return this.tips;
	}
	
	public void setTips(String tips){
		this.tips=tips;
	}

	public Integer getVerificationType(){
		return this.verificationType;
	}
	
	public void setVerificationType(Integer verificationType){
		this.verificationType=verificationType;
	}

}
