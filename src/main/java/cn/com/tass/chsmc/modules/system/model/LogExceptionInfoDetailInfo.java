package cn.com.tass.chsmc.modules.system.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="t_log_exception_detail")
public class LogExceptionInfoDetailInfo {
	private int detail_ID;
	//异常详情
	private String exceptDetail;
    
	@Id
	@GeneratedValue
	public int getDetail_ID() {
		return detail_ID;
	}

	public void setDetail_ID(int detailID) {
		detail_ID = detailID;
	}

	public String getExceptDetail() {
		return exceptDetail;
	}
	public void setExceptDetail(String exceptDetail) {
		this.exceptDetail = exceptDetail;
	}
}
