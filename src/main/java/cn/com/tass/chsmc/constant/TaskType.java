package cn.com.tass.chsmc.constant;

public enum TaskType {
	//无
	NONE(0),
	// 虚拟机开机
	STARTVSM(1),
	// 虚拟机重启
	RESTARTVSM(2),
	// 虚拟机关机
	STOPVSM(3),
	// 虚拟机重置
	RESETVSM(4),
	// 虚拟机克隆
	CLONEVSM(5),
	// 虚拟机升级
	UPGRADEVSM(6),
	// 宿主机升级
	UPGRADEHSM(7),
	//虚拟机导入影像文件
	VSMIMPORTIMAGE(8),
	//虚拟机批量升级
	UPGRADEBATCHVSM(9),
	//宿主机批量升级
	UPGRADEBATCHHSM(10),
	//导出影像文件
	EXPORTIMAGEVSM(11),
	//重置VM
	RESETVM(12);

	private int value = 0;

	private TaskType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
	public static TaskType valueOf(int value) {
		switch (value) {
		case 0:
			return NONE;
		case 1:
			return STARTVSM;
		case 2:
			return RESTARTVSM;
		case 3:
			return STOPVSM;
		case 4:
			return RESETVSM;
		case 5:
			return CLONEVSM;
		case 6:
			return UPGRADEVSM;
		case 7:
			return UPGRADEHSM;
		case 8:
			return VSMIMPORTIMAGE;
		case 9:
			return UPGRADEBATCHVSM;
		case 10:
			return UPGRADEBATCHHSM;
		case 11:
			return EXPORTIMAGEVSM;
		case 12:
			return RESETVM;
		default:
			return NONE;
		}
	}
}
