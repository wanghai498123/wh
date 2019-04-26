package cn.com.tass.chsmc.exception;
/**
 * 标题: 异常基类
 * <p>
 * 描述: 所有自定义异常从该类继承
 * <p>
 * 版权: Copyright (c) 2016
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 * 
 * @author 卢灿 [lucan@tass.com.cn]
 * @created 2016-2-26 下午03:42:04
 * @version 1.0
 */
public class BaseException  extends Exception{


	private static final long serialVersionUID = -1546242495166813810L;

	public BaseException() {
	}

	public BaseException(String message) {
		super(message);
	}

	public BaseException(Throwable throwable) {
		super(throwable);
	}

	public BaseException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public String toString() {
		return getMessage();
	}
}