package cn.com.tass.chsmc.constant;

public enum TaskStatus {
	// 任务已接收
	ACCEPT(0),
	// 任务接受失败
	ACCEPTFAILED(1),
	// 任务运行中
	RUNNING(2),
	// 任务完成
	COMPLETE(3),
	// 任务运行失败
	FAILED(4),
	// 执行超时
	TIMEOUT(5);

	private int value = 0;

	private TaskStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
	public static TaskStatus valueOf(int value) {
		switch (value) {
		case 0:
			return ACCEPT;
		case 1:
			return ACCEPTFAILED;
		case 2:
			return RUNNING;
		case 3:
			return COMPLETE;
		case 4:
			return FAILED;
		case 5:
			return TIMEOUT;
		default:
			return RUNNING;
		}
	}
}
