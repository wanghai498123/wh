package cn.com.tass.chsmc.web.pagination;

public class SelectPageable {
	private String sID;
	private String queryValue;
	private String selKey;
	private int pageSize=0;
	private int pageNo=0;
	private String exCondition;// 额外查询条件
	private String refCondition;// 关联查询条件
	
	
	public String getsID() {
		return sID;
	}
	public void setsID(String sID) {
		this.sID = sID;
	}
	public String getQueryValue() {
		return queryValue;
	}
	public void setQueryValue(String queryValue) {
		this.queryValue = queryValue;
	}
	public String getSelKey() {
		return selKey;
	}
	public void setSelKey(String selKey) {
		this.selKey = selKey;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public String getExCondition() {
		return exCondition;
	}
	public void setExCondition(String exCondition) {
		this.exCondition = exCondition;
	}
	public String getRefCondition() {
		return refCondition;
	}
	public void setRefCondition(String refCondition) {
		this.refCondition = refCondition;
	}

}
