package cn.com.tass.chsmc.modules.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.tass.chsmc.constant.MenuKeyConst;
import cn.com.tass.chsmc.interceptor.annotation.MenuAnnotaion;
import cn.com.tass.chsmc.modules.common.controller.BaseController;
import cn.com.tass.chsmc.modules.system.model.SysParamCategory;
import cn.com.tass.chsmc.modules.system.service.SysParamCategoryService;

/**
 * 标题: 系统参数种类管理控制器
 * <p>
 * 描述: 系统参数种类管理控制器
 * <p>
 * 版权: Copyright (c) 2016
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 * 
 * @author JNTA Codegenerator [lucan@tass.com.cn]
 * @created 2016-04-02 23:13:24
 * @version 1.0
 */
@Controller
@RequestMapping("/sysParamCategory")
public class SysParamCategoryController extends BaseController {

	@Autowired
	private SysParamCategoryService sysParamCategoryService;

	/**
	 * 跳转至系统参数种类列表页面
	 * @return  系统参数种类列表视图
	 * @throws Exception 
	 */
	@RequestMapping(value = "/toList", method = RequestMethod.GET)
	@MenuAnnotaion(menuKey=MenuKeyConst.SYS_MENU)
	public String toList(Model model) throws Exception {
		List<SysParamCategory> listParam = this.sysParamCategoryService.listSysParamCategory();
		model.addAttribute("paramCategoryList",listParam);
		return "system/sysParamCategory/sysParamCategoryList";
	}

}
