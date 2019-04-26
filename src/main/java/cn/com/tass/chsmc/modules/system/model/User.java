package cn.com.tass.chsmc.modules.system.model;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import cn.com.tass.chsmc.modules.common.model.BaseEntity;


/**
 * 标题: 用户表
WebServer：读/写 
 * <p>
 * 描述: 用户表
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
@Table(name = "t_sys_user")
public class User extends BaseEntity{

	private static final long serialVersionUID = 5101225675673387689L;

	/**
	 * 标识
	 */
	private Integer userID;

	/**
	 * 角色
	 */
	private Role role;
	/**
	 * 登录名
	 */
	private String loginName;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 工号
	 */
	private String jobNum;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 电话
	 */
	private String telephone;

	/**
	 * 手机
	 */
	private String mobilePhone;

	/**
	 * ip范围
	 */
	private String ipAddress;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 状态
            1 : 正常
            2 : 删除
            3 : 锁定
	 */
	private int status;

	/**
	 * 是否系统内置
            0 : 不内置
            1 : 内置
	 */
	private int isSysDefault;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 最近登录时间
	 */
	private Date lastLoginTime;

	/**
	 * 填写的采用主题的css样式名称
	 */
	private String themeType;


	private String startStandardIP;

	private String endStandardIP;

	/**
	 * 密码是否过期 0：否，1：是，默认为0
	 */
	private int isExpired;
	
	private PassWordPoliy passWordPoliy;

	@Id
	@GeneratedValue
	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}
	//@JSONField(serialize = false)
	@ManyToOne(fetch = FetchType.EAGER,targetEntity = PassWordPoliy.class)
	@JoinColumn(name = "policyID", referencedColumnName = "policyID",nullable = true, updatable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	public PassWordPoliy getPassWordPoliy() {
		return passWordPoliy;
	}

	public void setPassWordPoliy(PassWordPoliy passWordPoliy) {
		this.passWordPoliy = passWordPoliy;
	}
	
	@ManyToOne(fetch = FetchType.EAGER,targetEntity = Role.class)
	@JoinColumn(name = "userRoleID", referencedColumnName = "roleID",nullable = true, updatable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
	@Transient
	public String getRoleName() {
		return role == null ? "" : role.getRoleName();
	}

	public String getLoginName(){
		return this.loginName;
	}
	
	public void setLoginName(String loginName){
		this.loginName=loginName;
	}

	public String getUserName(){
		return this.userName;
	}
	
	public void setUserName(String userName){
		this.userName=userName;
	}

	public String getJobNum(){
		return this.jobNum;
	}
	
	public void setJobNum(String jobNum){
		this.jobNum=jobNum;
	}

	public String getEmail(){
		return this.email;
	}
	
	public void setEmail(String email){
		this.email=email;
	}

	public String getTelephone(){
		return this.telephone;
	}
	
	public void setTelephone(String telephone){
		this.telephone=telephone;
	}

	public String getMobilePhone(){
		return this.mobilePhone;
	}
	
	public void setMobilePhone(String mobilePhone){
		this.mobilePhone=mobilePhone;
	}

	public String getIpAddress(){
		return this.ipAddress;
	}
	
	public void setIpAddress(String ipAddress){
		this.ipAddress=ipAddress;
	}



	public String getPassword(){
		return this.password;
	}
	
	public void setPassword(String password){
		this.password=password;
	}

	public String getDescription(){
		return this.description;
	}
	
	public void setDescription(String description){
		this.description=description;
	}

	public int getStatus(){
		return this.status;
	}
	
	public void setStatus(int status){
		this.status=status;
	}

	public int getIsSysDefault(){
		return this.isSysDefault;
	}
	
	public void setIsSysDefault(int isSysDefault){
		this.isSysDefault=isSysDefault;
	}

	public Date getCreateTime(){
		return this.createTime;
	}
	
	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}

	public Date getLastLoginTime(){
		return this.lastLoginTime;
	}
	
	public void setLastLoginTime(Date lastLoginTime){
		this.lastLoginTime=lastLoginTime;
	}

	public String getThemeType(){
		return this.themeType;
	}
	
	public void setThemeType(String themeType){
		this.themeType=themeType;
	}

	public String getStartStandardIP(){
		return this.startStandardIP;
	}
	
	public void setStartStandardIP(String startStandardIP){
		this.startStandardIP=startStandardIP;
	}

	public String getEndStandardIP(){
		return this.endStandardIP;
	}
	
	public void setEndStandardIP(String endStandardIP){
		this.endStandardIP=endStandardIP;
	}

	public int getIsExpired(){
		return this.isExpired;
	}
	
	public void setIsExpired(int isExpired){
		this.isExpired=isExpired;
	}

}
