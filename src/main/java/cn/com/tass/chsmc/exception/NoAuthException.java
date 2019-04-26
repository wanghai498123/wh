package cn.com.tass.chsmc.exception;


/**
 * 描述: 未授权的权限异常
 * <p>
 * 版权: Copyright (c) 2016
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 * 
 * @author 卢灿 [lucan@tass.com.cn]
 * @created 2016-2-26 上午11:55:08
 * @version 1.0
 */
public class NoAuthException extends BaseException {

	private static final long serialVersionUID = 4180813648828550056L;

	public NoAuthException() {
	}

	public NoAuthException(String message) {
		super(message);
	}

	public NoAuthException(Throwable throwable) {
		super(throwable);
	}

	public NoAuthException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
