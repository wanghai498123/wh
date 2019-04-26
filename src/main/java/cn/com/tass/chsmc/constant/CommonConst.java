package cn.com.tass.chsmc.constant;
/**
 * 标题: 通用其它常量
 * <p>
 * 描述: 通用其它常量
 * <p>
 * 版权: Copyright (c) 2016
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 * 
 * @author 卢灿 [lucan@tass.com.cn]
 * @created 2016-2-25 下午05:55:48
 * @version 1.0
 */
public class CommonConst {

	public static final String MenuId="menuId";
	
	
	public static final String STATUS_ERROR="error";
	
	public static final String STATUS_SUCCESS="success";
	
	public static final String STATUS_INFO="info";
	
	public static final String STATUS_NOAUTH="noAuth";
	
	//用于AJAX返回应答的时候，需要重定位至Login页面
	public static final String STATUS_LOGIN="login";
	
	//WEB ROOT变量键值
	public static final String WEB_ROOT="chsmc.root";
	
	//HSM升级文件路径
	public static final String UPGRADE_HSM_FILE_PATH="/upgrade/hsm/";
	//VSM升级文件路径
	public static final String UPGRADE_VSM_FILE_PATH="/upgrade/vsm/";
	/** 许可证导入文件临时目录 */
	public static final String UPGRADE_LICENSE_FILE_PATH = "/upgrade/licenseTmpFiles/";
	/** 日志文件目录 */
	public static final String LOG_FILE_PATH = "/logs/chsmserver/";
	/** 集群标识 */
	public static final String CONTROLLER_CLUSTER = "CLUSTER";
	/** 宿主机标识 */
	public static final String CONTROLLER_HSM = "HSM";
	/** 虚拟机标识 */
	public static final String CONTROLLER_VSM = "VSM";
	/** 实体机标识 */
	public static final String CONTROLLER_RSM = "RSM";
	
	/** 日志类别 SYSTEM*/
	public static final String LOG_SYSTEM = "SYSTEM";
	/** 日志类别  MANAGER*/
	public static final String LOG_MANAGER = "MANAGER";
	/** 日志类别  USER*/
	public static final String LOG_USER = "USER";
	/** 日志类别  OTHER*/
	public static final String LOG_TYPE_OTHER = "OTHER";
	/** 日志类型 HSM*/
	public static final String LOG_TYPE_HSM = "HSM";
	/** 日志类型 VSM*/
	public static final String LOG_TYPE_VSM = "VSM";
	/** 日志类型 RSM*/
	public static final String LOG_TYPE_RSM = "RSM";
	/** 日志类型 VSM_SERVICE*/
	public static final String LOG_TYPE_VSM_SERVICE = "VSM_SERVICE";
	/** 日志类型 VSM_OPERATE*/
	public static final String LOG_TYPE_VSM_OPERATE = "VSM_OPERATE";
	/** 日志类型 RSM_SERVICE*/
	public static final String LOG_TYPE_RSM_SERVICE = "RSM_SERVICE";
	/** 日志类型 RSM_OPERATE*/
	public static final String LOG_TYPE_RSM_OPERATE = "RSM_OPERATE";
	
	
	/** 1 监控信息标识  1:监控;2: 日志*/
	public static final String PARA_TYPE2_MONITOR = "1";
	/** 2  日志文件标识  1:监控; 2:日志 */
	public static final String PARA_TYPE2_LOG = "2";
	//VSM影像文件
	public static final String ISO_VSM_FILE_PATH="/iso/vsm/";

	//HSM影像文件
	public static final String ISO_HSM_FILE_PATH="/iso/hsm/";
	
	/** 系统参数项 paramName: rootUrl; paramDisplayName:根路径配置 */
	public static final String SYS_PARA_ROOTURL              = "rootUrl";
	/** 系统参数项 paramName: logPolicy; paramDisplayName:日志清除策略 */
	public static final String SYS_PARA_LOGPOLICY            = "logPolicy";
	/** 系统参数项 paramName: vsmUpdate; paramDisplayName:VSM状态更新时间 */
	public static final String SYS_PARA_VSMUPDATE            = "vsmUpdate";
	/** 系统参数项 paramName: hsmUpdate; paramDisplayName:HSM状态更新时间 */
	public static final String SYS_PARA_HSMUPDATE            = "hsmUpdate";
	/** 系统参数项 paramName: publicKey; paramDisplayName:公钥 */
	public static final String SYS_PARA_PUBLICKEY            = "publicKey";
	/** 系统参数项 paramName:privateKey; paramDisplayName:私钥 */
	public static final String SYS_PARA_PRIVATEKEY           = "privateKey";
	/** 系统参数项 paramName: taskTimeOut; paramDisplayName:任务过期时间 */
	public static final String SYS_PARA_TASKTIMEOUT          = "taskTimeOut";
	/** 系统参数项 paramName: monitorPolicy; paramDisplayName:监控清除策略 */
	public static final String SYS_PARA_MONITORPOLICY        = "monitorPolicy";
	/** 系统参数项 paramName: taskPolicy; paramDisplayName:任务清除策略 */
	public static final String SYS_PARA_TASKPOLICY           = "taskPolicy";
	/** 系统参数项 paramName: CfgHsmAtuoFlag; paramDisplayName: 自动配置Hsm参数*/
	public static final String SYS_PARA_CFGHSMATUOFLAG       = "CfgHsmAtuoFlag";
	/** 系统参数项 paramName: CfgRptLogServerType; paramDisplayName:日志服务器类型 */
	public static final String SYS_PARA_CFGRPTLOGSERVERTYPE  = "CfgRptLogServerType";
	/** 系统参数项 paramName: CfgRptUploadImgFlg; paramDisplayName:影像是否上传 */
	public static final String SYS_PARA_CFGRPTUPLOADIMGFLG   = "CfgRptUploadImgFlg";
	/** 系统参数项 paramName: CfgRptUploadMonitor; paramDisplayName:监控是否上传 */
	public static final String SYS_PARA_CFGRPTUPLOADMONITOR  = "CfgRptUploadMonitor";
	/** 系统参数项 paramName: CfgRptUploadInterval; paramDisplayName:上传间隔 */
	public static final String SYS_PARA_CFGRPTUPLOADINTERVAL = "CfgRptUploadInterval";
	
	
	
	
	
}
