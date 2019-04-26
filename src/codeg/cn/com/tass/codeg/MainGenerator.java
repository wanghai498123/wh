package cn.com.tass.codeg;

import cn.com.tass.codeg.generator.ControllerGenerator;
import cn.com.tass.codeg.generator.DaoGenerator;
import cn.com.tass.codeg.generator.ResourceFileGenerator;
import cn.com.tass.codeg.generator.ServiceGenerator;
import cn.com.tass.codeg.generator.SingleModelGenerator;
import cn.com.tass.codeg.generator.ViewGenerator;

/**
 * 标题: 主代码生成器
 * <p>
 * 描述: 负责生成Model\DAO\Service\Controller层
 * <p>
 * 版权: Copyright (c) 2016
 * <p>
 * 公司:
 * <p>
 * 
 * @author 
 * @created 2016-3-4 下午04:01:39
 * @version 1.0
 */
public class MainGenerator {

	public static void main(String[] args) {
//		double ret=Math.log((Math.pow(2, 0)+Math.pow(2, 0)+Math.pow(2, 0))/3)/Math.log(2);
//		System.out.println(String .format("%.1f",ret));
//		genAllCode("hsm", "t_hsm_cluster", "HsmCluster", "主机集群");
//		genAllCode("hsm", "t_hsm_chsm", "Hsm", "主机");
//		genAllCode("hsm", "t_hsm_dns", "HsmDns", "主机dns");
//		genAllCode("hsm", "t_hsm_monitordata", "HsmMonitor", "主机监控");
//		genAllCode("hsm", "t_hsm_version", "HsmVersion", "主机版本");
//		genAllCode("hsm", "t_hsm_log", "HsmLog", "主机日志");
//		
		//genAllCode("vsm", "t_vsm_cluster", "VsmCluster", "虚拟机集群");
		//genAllCode("vsm", "t_vsm_cluster_policy", "VsmClusterPolicy", "虚拟机集群策略");
//		genAllCode("vsm", "t_vsm_vsm", "Vsm", "虚拟机");
//		genAllCode("vsm", "t_vsm_monitordata", "VsmMonitor", "虚拟机监控");
//		genAllCode("vsm", "t_vsm_version", "VsmVersion", "虚拟机版本");
//		genAllCode("vsm", "t_vsm_log", "VsmLog", "虚拟机日志");
//		
//		genAllCode("drift", "t_vsm_drift_imagefile", "DriftImagefile", "影像文件");
//		genAllCode("drift", "t_vsm_drift_policy", "DriftPolicy", "漂移策略");
//		genAllCode("drift", "t_vsm_drift_history", "DriftHistory", "漂移历史");
//		
//		genAllCode("task", "t_tsk_task", "Task", "任务");
//		
//		genAllCode("policy", "t_plc_scheduler", "Scheduler", "策略");
//		genAllCode("policy", "t_plc_scheduler_history", "SchedulerHistory", "策略完成历史");
//		
//		
//		genAllCode("license", "t_lic_license", "License", "许可证");
//		
//		genAllCode("system", "t_sys_user", "User", "用户");
//		genAllCode("system", "t_sys_role", "Role", "角色");
//		genAllCode("system", "t_sys_param_init", "SysParam", "系统参数");
//		genAllCode("system", "t_sys_param_category", "SysParamCategory", "系统参数种类");
		
//		genAllCode("system", "t_sys_basicproperty", "SysBasicProperty", "基础属性");
//		genAllCode("policy", "t_policy", "Policy", "策略配置");
//      genAllCode("drift", "t_vsm_drift_log", "DriftLog", "VSM漂移日志表");
		
//		genAllCode("monitor", "t_mon_info", "MonitorInfo", "监控信息");
//		genAllCode("monitor", "t_mon_cpu", "Cpu", "CUP信息");
//		genAllCode("monitor", "t_mon_eps", "Eps", "EPS");
//		genAllCode("monitor", "t_mon_io", "Io", "磁盘io");
//		genAllCode("monitor", "t_mon_load", "Load", "负载");
//		genAllCode("monitor", "t_mon_mem", "Mem", "内存");
//		genAllCode("monitor", "t_mon_network", "Network", "网络");
//		genAllCode("monitor", "t_mon_process", "Process", "进程");
//		genAllCode("monitor", "t_mon_storage", "Storage", "磁盘空间");
//		genAllCode("monitor", "t_mon_swap", "Swap", "交换空间");
//		genAllCode("monitor", "t_mon_tcp", "Tcp", "tcp连接数");
		
		//genAllCode("monitor", "t_mon_info_5min", "MonitorInfo5Min", "监控信息");
		//genAllCode("monitor", "t_mon_info_day", "MonitorInfoDay", "监控信息");
		//genAllCode("monitor", "t_mon_info_hour", "MonitorInfoHour", "监控信息");
		
		//genAllCode("monitor", "t_mon_eps_5min", "Eps5Min", "EPS");
		//genAllCode("monitor", "t_mon_eps_day", "EpsDay", "EPS");
		//genAllCode("monitor", "t_mon_eps_hour", "EpsHour", "EPS");
		
//		genAllCode("report", "t_rpt_chartdata", "CharData", "图表数据");
//		genAllCode("report", "t_rpt_instance", "Instance", "报表实例");
//		genAllCode("report", "t_rpt_instancefieldvalue", "InstanceFieldValue", "报表实例值");
//		genAllCode("report", "t_rpt_instance_object", "InstanceObject", "实例对象");
//		genAllCode("report", "t_rpt_listdata", "ListData", "报表实例数据");
//		genAllCode("report", "t_rpt_group", "ReportGroup", "报表分组");
//		genAllCode("report", "t_rpt_result", "Result", "结果");
//		genAllCode("report", "t_rpt_template", "Template", "模板");
//		genAllCode("report", "t_rpt_templatefield", "TemplateField", "模板字段");
//		genAllCode("report", "t_rpt_templatetype", "TemplateType", "模板类别");
		
		genAllCode("report", "t_rpt_response", "Response", "响应");
		
//		genAllCode("taskScheduler", "t_tsk_task", "CTask", "任务");
//		genAllCode("taskScheduler", "t_tsk_taskobject", "TaskObject", "任务对象");
//		genAllCode("taskScheduler", "t_tsk_taskscheduler", "TaskScheduler", "任务计划");
//		genAllCode("taskScheduler", "t_tsk_taskscheduler_object", "TaskSchedulerObject", "任务计划对象");
//		genAllCode("taskScheduler", "t_tsk_taskscheduler_policy", "TaskSchedulerPolicy", "任务计划策略");
		
	}
	
	private static void genAllCode(String moduleName,String tableName,String className,String classComment){
		
		  SingleModelGenerator.genSingleEntity(moduleName, tableName, className);
		  DaoGenerator.genDaoCode(moduleName, className, classComment);
		  ServiceGenerator.genServiceCode(moduleName, className, classComment);
		  ControllerGenerator.genControllerCode(moduleName, className, classComment);
		  ResourceFileGenerator.genResouceCode(moduleName,tableName, className, classComment);
		  ViewGenerator.genListPage(moduleName, tableName, className, classComment);
	}
}
