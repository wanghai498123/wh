package cn.com.tass.chsmc.modules.test.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.alibaba.fastjson.annotation.JSONField;

import cn.com.tass.chsmc.modules.common.model.BaseEntity;
import cn.com.tass.chsmc.modules.system.model.User;

/**
 * 标题: TEST域模型
 * <p>
 * 描述: TEST域模型
 * <p>
 * 版权: Copyright (c) 2015
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 * 
 * @author 卢灿 [lucan@tass.com.cn]
 * @created 2016-2-23 上午10:55:30
 * @version 1.0
 */

@Entity
@Table(name = "t_test")
public class Test extends BaseEntity {

	private static final long serialVersionUID = -2522785117188872223L;

	private long testId;

	private String testName;

	private String testScript;
	

	private User user;


	@Id
	@GeneratedValue
	public long getTestId() {
		return testId;
	}

	public void setTestId(long testId) {
		this.testId = testId;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getTestScript() {
		return testScript;
	}

	public void setTestScript(String testScript) {
		this.testScript = testScript;
	}

	@JSONField(serialize = false)
	@ManyToOne(fetch = FetchType.LAZY,targetEntity = User.class)
	@JoinColumn(name = "userId", referencedColumnName = "userId",nullable = true, updatable = true)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Transient
	public String getUserName() {
		return user == null ? "" : user.getLoginName();
	}
	
	@Transient
	public long getUserID() {
		return user == null ? -1 : user.getUserID();
	}
}
