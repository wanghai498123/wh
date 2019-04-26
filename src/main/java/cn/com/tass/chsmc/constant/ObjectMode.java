package cn.com.tass.chsmc.constant;

public enum ObjectMode {
	// 数据中心
	CENTER(0),
	// 集群：主机集群或者虚拟机集群
	CLUSTER(1),
	// 主机
	HSM(2),
	// 虚拟机金融加密机
	EVSM(3),
	// 虚拟机签名验证服务器
	SVSM(4),
	// 虚拟机认证加密机
	GVSM(5),
	// 类型未知虚拟机
	VSM(6),
	// 金融加密机
	ESM(7),
	// 签名验证服务器
	SSM(8),
	// 认证加密机
	GSM(9),
	// 类型未知加密机
	SM(10);
	

	private int value = 0;

	public int getValue() {
		return value;
	}

	private ObjectMode(int value) {
		this.value = value;
	}

	public static ObjectMode valueOf(int value) {
		switch (value) {
		case 0:
			return CENTER;
		case 1:
			return CLUSTER;
		case 2:
			return HSM;
		case 3:
			return EVSM;
		case 4:
			return SVSM;
		case 5:
			return GVSM;
		case 6:
			return VSM;
		case 7:
			return ESM;
		case 8:
			return SSM;
		case 9:
			return GSM;
		default:
			return EVSM;
		}
	}
}
