package cn.com.tass.chsmc.modules.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.tass.chsmc.constant.MenuKeyConst;
import cn.com.tass.chsmc.interceptor.annotation.MenuAnnotaion;
import cn.com.tass.chsmc.modules.common.controller.BaseController;
import cn.com.tass.chsmc.modules.system.model.LogOperationInfo;
import cn.com.tass.chsmc.modules.system.service.LogOperationInfoService;
import cn.com.tass.chsmc.web.json.JsonResponse;
import cn.com.tass.chsmc.web.pagination.DtPageable;
import cn.com.tass.chsmc.web.pagination.Page;


/**
 * 描述: 用户操作日志控制器
 * <p>
 * 版权: Copyright (c) 2016
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 * 
 * @author JNTA Codegenerator [lucan@tass.com.cn]
 * @created 2016-04-05 17:02:33
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/logOperation")
public class LogOperationInfoController extends BaseController {
	
	@Autowired
	private LogOperationInfoService service;

	@RequestMapping(value = "/list", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResponse logList(DtPageable dtPageable) throws Exception {
		JsonResponse jsonResponse = new JsonResponse();
		Page<LogOperationInfo> page = this.service.listLogOperationInfo(dtPageable);
		jsonResponse.setRespData(page);
		return jsonResponse;
	}

	@RequestMapping(value = "/toList")
	@MenuAnnotaion(menuKey = MenuKeyConst.SYS_USER_LOG_MENU)
	public String LogExceptionPage() {
		return "system/log/logOperationList";
	}
}
