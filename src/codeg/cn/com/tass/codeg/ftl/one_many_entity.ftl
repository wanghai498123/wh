<#--全局变量是否包含指定的包 -->
<#assign g_bPkgDate=false/>
<#list e_tableinfo.columns as column>
	<#if column.propertyType=="Date">
		<#assign g_bPkgDate=true/>
		<#break>
	</#if>
</#list>
package cn.com.tass.chsmc.modules.${e_moduleName}.model;

<#if e_bOneType>
import java.util.List;
</#if>

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
<#if e_bOneType>
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
<#else>
import javax.persistence.ManyToOne;
</#if>
import javax.persistence.Id;
import javax.persistence.Table;
<#if g_bPkgDate>
import java.util.Date;
</#if>

import com.alibaba.fastjson.annotation.JSONField;
import cn.com.tass.chsmc.modules.common.model.BaseEntity;

/**
 * 标题: ${e_tableinfo.comment!""}
 * <p>
 * 描述: ${e_tableinfo.comment!""}
 * <p>
 * 版权: Copyright (c) 2016
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 * 
 * @author JNTA Codegenerator [lucan@tass.com.cn]
 * @created ${e_sysTime?string("yyyy-MM-dd HH:mm:ss")}
 * @version 1.0
 */
@Entity
@Table(name = "${e_tableinfo.tableName}")
public class <#if e_bOneType>${e_oneclassName}<#else>${e_manyClassName}</#if> extends BaseEntity{

	private static final long serialVersionUID = 5101225675673387689L;

	<#list e_tableinfo.columns as column>
	<#-- 字段属性注释 -->
	<#if column.columnComment?length gt 0>
	/**
	<#list column.columnComment?split("\n") as line>  
	 *${line?trim} 
	</#list>
	 */
	</#if>
	<#if !e_bOneType&&column.propertyName==e_joinColumn>
	<#-- Many端逻辑处理 -->
	private ${e_oneClassName} ${e_oneClassName?uncap_first};
	
	<#else>
	private ${column.propertyType} ${column.propertyName};
	
	</#if>
	</#list>
	<#if e_bOneType>
	<#--One端额外处理逻辑-->
	private List<${e_manyClassName}> ${e_manyClassName?uncap_first}List;
	</#if>
	
	<#--属性方法 -->
	<#list e_tableinfo.columns as column>
	<#-- Many端逻辑处理 -->
	<#if !e_bOneType&&column.propertyName==e_joinColumn>
	@JSONField(serialize = false)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "${e_joinColumn}")
	public ${e_oneClassName} get${e_oneClassName}(){
		return this.${e_oneClassName?uncap_first};
	}
	
	public void set${e_oneClassName}(${e_oneClassName} ${e_oneClassName?uncap_first}){
		this.${e_oneClassName?uncap_first}=${e_oneClassName?uncap_first};
	}
	
	<#else>
	<#if column.extra=="auto_increment">
	@Id
	@GeneratedValue
	</#if>
	public ${column.propertyType} get${column.propertyName?cap_first}(){
		return this.${column.propertyName};
	}
	
	public void set${column.propertyName?cap_first}(${column.propertyType} ${column.propertyName}){
		this.${column.propertyName}=${column.propertyName};
	}
	
	</#if>
	</#list>
	<#if e_bOneType>
	<#-- One端额外处理逻辑-->
	@JSONField(serialize = false)
	@OneToMany(fetch = FetchType.LAZY, targetEntity = ${e_manyClassName}.class)
	@JoinColumns(value = { @JoinColumn(name = "${e_joinColumn}", referencedColumnName = "${e_joinColumn}") })
	public List<${e_manyClassName}> get${e_manyClassName}List(){
		return this.${e_manyClassName?uncap_first}List;
	}
	
	public void set${e_manyClassName}List(List<${e_manyClassName}> ${e_manyClassName?uncap_first}List){
		this.${e_manyClassName?uncap_first}List=${e_manyClassName?uncap_first}List;
	}${"\n"}
	</#if>
}
