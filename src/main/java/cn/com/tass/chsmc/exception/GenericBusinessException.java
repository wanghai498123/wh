package cn.com.tass.chsmc.exception;
/**
 * 标题: 通用业务异常
 * <p>
 * 描述: 通用业务异常
 * <p>
 * 版权: Copyright (c) 2016
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 * 
 * @author 卢灿 [lucan@tass.com.cn]
 * @created 2016-2-26 下午03:55:48
 * @version 1.0
 */
public class GenericBusinessException extends BaseException{

	private static final long serialVersionUID = -5721677431506328970L;

	private String errCode;
	private String errMessage;
	
	public GenericBusinessException(String code,String message) {
		super(message);
		
		this.errCode=code;
		this.errMessage=message;
	}


	public GenericBusinessException(String message) {
		super(message);
		this.errMessage=message;
	}

	public GenericBusinessException(Throwable throwable) {
		super(throwable);
	}

	public GenericBusinessException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMessage() {
		return errMessage;
	}

	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}
}
