package cn.com.tass.chsmc.modules.system.model;

import java.util.List;

/**
 * 标题: 
 * <p>
 * 描述: 
 * <p>
 * 版权: Copyright (c) 2015
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 * 
 * @author 卢灿 [lucan@tass.com.cn]
 * @created 2016-2-22 下午04:07:18
 * @version 1.0
 */
public class Page<T> {

	private List<T> aaData;
	
	private long iTotalRecords;
	
	private long iTotalDisplayRecords;

	public List<T> getAaData() {
		return aaData;
	}

	public void setAaData(List<T> aaData) {
		this.aaData = aaData;
	}

	public long getiTotalRecords() {
		return iTotalRecords;
	}

	public void setiTotalRecords(long iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}

	public long getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}

	public void setiTotalDisplayRecords(long iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}
	
	
}
