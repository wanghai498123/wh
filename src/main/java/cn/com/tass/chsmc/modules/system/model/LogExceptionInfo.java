package cn.com.tass.chsmc.modules.system.model;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="t_log_exception")
public class LogExceptionInfo {
	
	private int logID;
	//操作名称
	private String operatName;
	//异常位置
	private String exceptionLocation;

	//异常发生时间
	private Timestamp exceptionTime;
	
    //异常详情
	private LogExceptionInfoDetailInfo exceptionInfoDetailInfo;
	
	public LogExceptionInfo(){
		
	}
	
	public LogExceptionInfo(int logID, String ipAddress, String operatName,
			String exceptionLocation, String exceptionName) {
		this.logID = logID;
		this.operatName = operatName;
		this.exceptionLocation = exceptionLocation;
	}


    @Id
    @GeneratedValue
	public int getLogID() {
		return logID;
	}

	public void setLogID(int logID) {
		this.logID = logID;
	}

	public String getOperatName() {
		return operatName;
	}

	public void setOperatName(String operatName) {
		this.operatName = operatName;
	}

	public String getExceptionLocation() {
		return exceptionLocation;
	}

	public void setExceptionLocation(String exceptionLocation) {
		this.exceptionLocation = exceptionLocation;
	}
	
	public Timestamp getExceptionTime() {
		return exceptionTime;
	}

	public void setExceptionTime(Timestamp exceptionTime) {
		this.exceptionTime = exceptionTime;
	}

    /**
     *单项关联异常详情表 
     *cascade=CascadeType.ALL（级联更新,删除,修改）
     *optional = false(false 表示详情信息不可为空)
     *referencedColumnName ="detail_ID"("关联详情表列名")
     *unique=true (表示detailID不可为空)
     */
	@OneToOne(cascade=CascadeType.ALL,optional = false)
	@JoinColumn(name = "detail_id",referencedColumnName ="detail_ID",unique = true)
	public LogExceptionInfoDetailInfo getExceptionInfoDetailInfo() {
		return exceptionInfoDetailInfo;
	}

	public void setExceptionInfoDetailInfo(
			LogExceptionInfoDetailInfo exceptionInfoDetailInfo) {
		this.exceptionInfoDetailInfo = exceptionInfoDetailInfo;
	}
    
	

	
	

}
