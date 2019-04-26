package cn.com.tass.chsmc.web.json;

import cn.com.tass.chsmc.constant.CommonConst;


/**
 * 标题: JSON格式响应
 * <p>
 * 描述: 所有采用JSON返回的数据，都采用此格式返回
 * <p>
 * 版权: Copyright (c) 2016
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 * 
 * @author 
 * @created 2016-2-26 下午05:41:38
 * @version 1.0
 */
public class JsonResponse {

	private String respStatus=CommonConst.STATUS_SUCCESS;
	
	private String respCode="";
	
	private String respMessage="";
	
	
	
	private Object respData;

	public String getRespStatus() {
		return respStatus;
	}

	public void setRespStatus(String respStatus) {
		this.respStatus = respStatus;
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getRespMessage() {
		return respMessage;
	}

	public void setRespMessage(String respMessage) {
		this.respMessage = respMessage;
	}

	public Object getRespData() {
		return respData;
	}

	public void setRespData(Object data) {
		this.respData = data;
	}
	
	/**
	 * 构建简单Json应答数据
	 * @param respStatus
	 * @param respCode
	 * @param respMessage
	 * @return
	 */
	public JsonResponse buidSimpleResponse(String respStatus,String respCode,String respMessage){
		
		JsonResponse jsonResponse=new JsonResponse();
		jsonResponse.setRespStatus(respStatus);
		jsonResponse.setRespCode(respCode);
		jsonResponse.setRespMessage(respMessage);
		
		return jsonResponse;
	}
}
