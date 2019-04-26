package cn.com.tass.chsmc.exception;
/**
 * 标题: 删除记录，改记录不能存在异常
 * <p>
 * 描述: 删除记录，改记录不能存在异常
 * <p>
 * 版权: Copyright (c) 2016
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 * 
 * @author 卢灿 [lucan@tass.com.cn]
 * @created 2016-2-26 下午03:50:26
 * @version 1.0
 */

public class RecordNotFoundException extends BaseException {

	private static final long serialVersionUID = -2316927672433193362L;

	public RecordNotFoundException() {
	}

	public RecordNotFoundException(String message) {
		super(message);
	}

	public RecordNotFoundException(Throwable throwable) {
		super(throwable);
	}

	public RecordNotFoundException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
