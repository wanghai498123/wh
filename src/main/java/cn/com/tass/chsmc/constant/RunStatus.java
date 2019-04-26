package cn.com.tass.chsmc.constant;

public enum RunStatus {
	//已关机
	CLOSE(0),
	//运行正常
	NORMAL(1),
	//状态未知
	UNKNOWN(2),
	//处于关机状态
	POWEROFF(3),
	//处于开机状态,可以ping通
	PINGOK(4),
	//处于开机状态,ping不通
	PINGFAILED(5);
	private int value = 0;
	private RunStatus(int value)
	{
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public static RunStatus valueOf(int value) {
		switch (value) {
		case 0:
			return CLOSE;
		case 1:
			return NORMAL;
		case 2:
			return UNKNOWN;
		case 3:
			return POWEROFF;
		case 4:
			return PINGOK;
		case 5:
			return PINGFAILED;
		default:
			return NORMAL;
		}
	}
}
