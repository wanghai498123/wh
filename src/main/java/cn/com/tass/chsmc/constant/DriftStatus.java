package cn.com.tass.chsmc.constant;

public enum DriftStatus {
	//未处理
	NOTHING(0),
	//漂移中
	DRIFTING(1),
	//漂移成功
	SUCCESS(2),
	//漂移失败
	FAIL(3);
	
	private int value = 0;
	private DriftStatus(int value)
	{
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public static DriftStatus valueOf(int value) {
		switch (value) {
		case 0:
			return NOTHING;
		case 1:
			return DRIFTING;
		case 2:
			return SUCCESS;
		case 3:
			return FAIL;
		default:
			return NOTHING;
		}
	}
}
