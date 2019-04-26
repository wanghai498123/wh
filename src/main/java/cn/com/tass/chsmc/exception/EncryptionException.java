package cn.com.tass.chsmc.exception;

/**
 * 标题: 加解密异常
 * <p>
 * 描述: 调用加解密方法错误时候，抛出该异常
 * <p>
 * 版权: Copyright (c) 2014
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 * 
 * @author jnta
 * @created 2014年8月19日 下午5:38:14
 * @version 1.0
 */
public class EncryptionException extends BaseException {

	private static final long serialVersionUID = -5300032795203922344L;

	public EncryptionException() {
	}

	public EncryptionException(String message) {
		super(message);
	}

	public EncryptionException(Throwable throwable) {
		super(throwable);
	}

	public EncryptionException(String message, Throwable throwable) {
		super(message, throwable);
	}
}