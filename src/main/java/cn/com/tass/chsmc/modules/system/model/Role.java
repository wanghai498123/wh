package cn.com.tass.chsmc.modules.system.model;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

import com.alibaba.fastjson.annotation.JSONField;



import cn.com.tass.chsmc.modules.common.model.BaseEntity;

/**
 * 标题: 角色表
WebServer：读/写 
 * <p>
 * 描述: 角色表
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
@Table(name = "t_sys_role")
public class Role extends BaseEntity{

	private static final long serialVersionUID = 5101225675673387689L;

	/**
	 * 角色标识
	 */
	private int roleID;

	/**
	 * 角色名称
	 */
	private String roleName;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 是否系统内置
            0 : 不内置
            1 : 内置
	 */
	private int isSysDefault;

	/**
	 * 状态
            1 : 正常
            2 : 删除
	 */
	private int status;
	

	private List<Menu> list;

	
	@Id
	@GeneratedValue
	public int getRoleID(){
		return this.roleID;
	}
	
	public void setRoleID(int roleID){
		this.roleID=roleID;
	}

	public String getRoleName(){
		return this.roleName;
	}
	
	public void setRoleName(String roleName){
		this.roleName=roleName;
	}

	public String getDescription(){
		return this.description;
	}
	
	public void setDescription(String description){
		this.description=description;
	}

	public int getIsSysDefault(){
		return this.isSysDefault;
	}
	
	public void setIsSysDefault(int isSysDefault){
		this.isSysDefault=isSysDefault;
	}

	public int getStatus(){
		return this.status;
	}
	
	public void setStatus(int status){
		this.status=status;
	}

	
	@JSONField(serialize = false)
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinTable(name = "t_sys_role_menu", joinColumns = { @JoinColumn(name = "roleID") }, inverseJoinColumns = { @JoinColumn(name = "menuID") })
	public List<Menu> getList() {
		return list;
	}

	public void setList(List<Menu> list) {
		this.list = list;
	}

	

	
	

}
