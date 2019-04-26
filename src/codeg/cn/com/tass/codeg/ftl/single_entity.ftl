<#--全局变量是否包含指定的包 -->
<#assign g_bPkgDate=false/>
<#list tableinfo.columns as column>
	<#if column.propertyType=="Date">
		<#assign g_bPkgDate=true/>
		<#break>
	</#if>
</#list>
package cn.com.tass.chsmc.modules.${moduleName}.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
<#if g_bPkgDate>
import java.util.Date;
</#if>

import cn.com.tass.chsmc.modules.common.model.BaseEntity;

/**
 * 标题: ${tableinfo.comment!""}
 * <p>
 * 描述: ${tableinfo.comment!""}
 * <p>
 * 版权: Copyright (c) 2016
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 * 
 * @author JNTA Codegenerator
 * @created ${sysTime?string("yyyy-MM-dd HH:mm:ss")}
 * @version 1.0
 */
@Entity
@Table(name = "${tableinfo.tableName}")
public class ${className} extends BaseEntity{

	private static final long serialVersionUID = 5101225675673387689L;

	<#list tableinfo.columns as column>
	<#if column.columnComment?length gt 0>
	/**
	 * ${column.columnComment}
	 */
	</#if>
	private ${column.propertyType} ${column.propertyName};${"\n"}
	</#list>
	
	<#--属性方法 -->
	<#list tableinfo.columns as column>
	<#if column.extra=="auto_increment">
	@Id
	@GeneratedValue
	</#if>
	public ${column.propertyType} get${column.propertyName?cap_first}(){
		return this.${column.propertyName};
	}
	
	public void set${column.propertyName?cap_first}(${column.propertyType} ${column.propertyName}){
		this.${column.propertyName}=${column.propertyName};
	}${"\n"}
	</#list>
}
