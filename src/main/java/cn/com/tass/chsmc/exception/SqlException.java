package cn.com.tass.chsmc.exception;

/**
 * 标题: SQL异常
 * <p>
 * 描述: SQL异常
 * <p>
 * 版权: Copyright (c) 2015
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 * 
 * @author 卢灿 [lucan@tass.com.cn]
 * @created 2016-2-22 下午10:49:17
 * @version 1.0
 */
public class SqlException extends Exception {

	private static final long serialVersionUID = -4872285415615880921L;

	public SqlException() {
	}

	public SqlException(String message) {
		super(message);
	}

	public SqlException(Throwable throwable) {
		super(throwable);
	}

	public SqlException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
