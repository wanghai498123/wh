package cn.com.tass.chsmc.exception;
/**
 * 描述: 未登陆异常
 * <p>
 * 版权: Copyright (c) 2016
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 * 
 * @author 卢灿 [lucan@tass.com.cn]
 * @created 2016-4-12 下午05:12:21
 * @version 1.0
 */
public class NoLoginException extends BaseException{
	
	private static final long serialVersionUID = 6293964362246042821L;

	public NoLoginException() {
	}

	public NoLoginException(String message) {
		super(message);
	}

	public NoLoginException(Throwable throwable) {
		super(throwable);
	}

	public NoLoginException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
