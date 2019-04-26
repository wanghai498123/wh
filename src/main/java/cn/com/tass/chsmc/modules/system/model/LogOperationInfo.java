package cn.com.tass.chsmc.modules.system.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_log_operation")
public class LogOperationInfo {

	private int logID;
	// ip地址
	private String ipAddress;
	// 日志级别
	private int logLevel;
	// 操作名称
	private String operatName;
	// 操作时间
	private Timestamp operatTime;
	// 操作用户
	private String operatUser;
	// 操作状态
	private String operatStatus;

	public LogOperationInfo() {

	}

	public LogOperationInfo(int logID, String ipAddress, int logLevel, String operatName, Date operatTime,
			String operatUser, String operatStatus) {
		this.logID = logID;
		this.ipAddress = ipAddress;
		this.logLevel = logLevel;
		this.operatName = operatName;
		this.operatUser = operatUser;
		this.operatStatus = operatStatus;
	}

	@Id
	@GeneratedValue
	public int getLogID() {
		return logID;
	}

	public void setLogID(int logID) {
		this.logID = logID;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public int getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(int logLevel) {
		this.logLevel = logLevel;
	}

	public String getOperatName() {
		return operatName;
	}

	public void setOperatName(String operatName) {
		this.operatName = operatName;
	}

	public String getOperatUser() {
		return operatUser;
	}

	public void setOperatUser(String operatUser) {
		this.operatUser = operatUser;
	}

	public String getOperatStatus() {
		return operatStatus;
	}

	public void setOperatStatus(String operatStatus) {
		this.operatStatus = operatStatus;
	}

	public Timestamp getOperatTime() {
		return operatTime;
	}

	public void setOperatTime(Timestamp operatTime) {
		this.operatTime = operatTime;
	}
}
