package cn.com.tass.chsmc.modules.system.model;


import java.sql.Timestamp;

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

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 标题: 口令策略
WebServer：读/写 
 * <p>
 * 描述: 口令策略
WebServer：读/写 
 * <p>
 * 版权: Copyright (c) 2016
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 * 
 * @author JNTA Codegenerator [lucan@tass.com.cn]
 * @created 2016-03-28 11:51:43
 * @version 1.0
 */
@Entity
@Table(name = "t_sys_passwordpolicy")
public class PassWordPoliy extends BaseEntity{

	private static final long serialVersionUID = 5101225675673387689L;

	/**
	 * 口令策略标识，流水号
	 */
	private Integer policyID;

	/**
	 * 策略名
	 */
	private String policyName;

	/**
	 * 创建人
	 */
	private User user;
	
	/**
	 * 创建人名称
	 */
	private String loginName;

	/**
	 * 更新时间，记录策略从创建开始每次更新的时间
	 */
	private Timestamp updateTime;

	/**
	 * 最大长度
	 */
	private Integer maxLength;

	/**
	 * 最小长度
	 */
	private Integer minLength;

	/**
	 * 最小包含数字
	 */
	private Integer containMinFigure;

	/**
	 * 最小包含字母
	 */
	private Integer containMinCharacter;

	/**
	 * 密码更新间隔，间隔单位为天。
	 */
	private Integer pwdUpdateInterval;

	/**
	 * 日期在过期多少天前通知用户
	 */
	private Integer advanceDaysInform;

	/**
	 * 是否过期：
            0 不过期 
            1 过期
	 */
	private Integer isExpired;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 标识：1 内置 0 用户创建，缺省为0
	 */
	private Integer isSysDefault=0;

	/**
	 * 过期提醒方式：1 邮件 2短信 3邮件和短信
	 */
	private Integer remindType;

	/**
	 * 包含最小特殊字符数
	 */
	private Integer containMinSpecialChar;

	/**
	 * 是否做相似口令检查：0 否 1是
	 */
	private Integer isSimilarPasswordChecked;

	/**
	 * 是否做连续字检测：0 否 1是
	 */
	private Integer isContinuousPasswordChecked;

	/**
	 * 检查连续字检测次数
	 */
	private Integer continuousPasswordCount;

	/**
	 * 是否用同一口令设置：0 否 1是
	 */
	private Integer isSamePasswordChecked;

	/**
	 * 是否检测历史代码：0 不检查 1检查
	 */
	private Integer isCheckHisPassword;

	/**
	 * 检查历史代码次数
	 */
	private Integer checkHisPasswordCount;
	
	/**
	 *是否正在使用:0否，1是 
	 */
	private Integer isUsing;
	
	private Integer isSamePassword;

	@Id
	@GeneratedValue
	public Integer getPolicyID() {
		return policyID;
	}

	public void setPolicyID(Integer policyID) {
		this.policyID = policyID;
	}

	public String getPolicyName() {
		return policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}
	@JSONField(serialize = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "creatorID", referencedColumnName = "userID", updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}

	public Integer getMinLength() {
		return minLength;
	}

	public void setMinLength(Integer minLength) {
		this.minLength = minLength;
	}

	public Integer getContainMinFigure() {
		return containMinFigure;
	}

	public void setContainMinFigure(Integer containMinFigure) {
		this.containMinFigure = containMinFigure;
	}

	public Integer getContainMinCharacter() {
		return containMinCharacter;
	}

	public void setContainMinCharacter(Integer containMinCharacter) {
		this.containMinCharacter = containMinCharacter;
	}

	public Integer getPwdUpdateInterval() {
		return pwdUpdateInterval;
	}

	public void setPwdUpdateInterval(Integer pwdUpdateInterval) {
		this.pwdUpdateInterval = pwdUpdateInterval;
	}

	public Integer getAdvanceDaysInform() {
		return advanceDaysInform;
	}

	public void setAdvanceDaysInform(Integer advanceDaysInform) {
		this.advanceDaysInform = advanceDaysInform;
	}

	public Integer getIsExpired() {
		return isExpired;
	}

	public void setIsExpired(Integer isExpired) {
		this.isExpired = isExpired;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getIsSysDefault() {
		return isSysDefault;
	}

	public void setIsSysDefault(Integer isSysDefault) {
		this.isSysDefault = isSysDefault;
	}

	public Integer getRemindType() {
		return remindType;
	}

	public void setRemindType(Integer remindType) {
		this.remindType = remindType;
	}

	public Integer getContainMinSpecialChar() {
		return containMinSpecialChar;
	}

	public void setContainMinSpecialChar(Integer containMinSpecialChar) {
		this.containMinSpecialChar = containMinSpecialChar;
	}

	public Integer getIsSimilarPasswordChecked() {
		return isSimilarPasswordChecked;
	}

	public void setIsSimilarPasswordChecked(Integer isSimilarPasswordChecked) {
		this.isSimilarPasswordChecked = isSimilarPasswordChecked;
	}

	public Integer getIsContinuousPasswordChecked() {
		return isContinuousPasswordChecked;
	}

	public void setIsContinuousPasswordChecked(Integer isContinuousPasswordChecked) {
		this.isContinuousPasswordChecked = isContinuousPasswordChecked;
	}

	public Integer getContinuousPasswordCount() {
		return continuousPasswordCount;
	}

	public void setContinuousPasswordCount(Integer continuousPasswordCount) {
		this.continuousPasswordCount = continuousPasswordCount;
	}

	public Integer getIsSamePasswordChecked() {
		return isSamePasswordChecked;
	}

	public void setIsSamePasswordChecked(Integer isSamePasswordChecked) {
		this.isSamePasswordChecked = isSamePasswordChecked;
	}

	public Integer getIsCheckHisPassword() {
		return isCheckHisPassword;
	}

	public void setIsCheckHisPassword(Integer isCheckHisPassword) {
		this.isCheckHisPassword = isCheckHisPassword;
	}

	public Integer getCheckHisPasswordCount() {
		return checkHisPasswordCount;
	}

	public void setCheckHisPasswordCount(Integer checkHisPasswordCount) {
		this.checkHisPasswordCount = checkHisPasswordCount;
	}

	public Integer getIsUsing() {
		return isUsing;
	}

	public void setIsUsing(Integer isUsing) {
		this.isUsing = isUsing;
		
	}

	public Integer getIsSamePassword() {
		return isSamePassword;
	}

	public void setIsSamePassword(Integer isSamePassword) {
		this.isSamePassword = isSamePassword;
	}
	@Transient
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
}
