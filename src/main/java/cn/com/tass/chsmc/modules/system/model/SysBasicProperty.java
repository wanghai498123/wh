package cn.com.tass.chsmc.modules.system.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.com.tass.chsmc.modules.common.model.BaseEntity;

/**
 * 标题: 基础属性表
 * <p>
 * 描述: 基础属性表
 * <p>
 * 版权: Copyright (c) 2016
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 * 
 * @author JNTA Codegenerator [lucan@tass.com.cn]
 * @created 2016-04-10 20:59:12
 * @version 1.0
 */
@Entity
@Table(name = "t_sys_basicproperty")
public class SysBasicProperty extends BaseEntity{

	private static final long serialVersionUID = 5101225675673387689L;

	/**
	 * 基础属性ID
	 */
	private int basicPropertyID;

	/**
	 * 基础属性类型
	 */
	private String basicPropertyType;

	/**
	 * 基础属性名称
	 */
	private String basicPropertyName;

	
	@Id
	@GeneratedValue
	public int getBasicPropertyID(){
		return this.basicPropertyID;
	}
	
	public void setBasicPropertyID(int basicPropertyID){
		this.basicPropertyID=basicPropertyID;
	}

	public String getBasicPropertyType(){
		return this.basicPropertyType;
	}
	
	public void setBasicPropertyType(String basicPropertyType){
		this.basicPropertyType=basicPropertyType;
	}

	public String getBasicPropertyName(){
		return this.basicPropertyName;
	}
	
	public void setBasicPropertyName(String basicPropertyName){
		this.basicPropertyName=basicPropertyName;
	}

}
