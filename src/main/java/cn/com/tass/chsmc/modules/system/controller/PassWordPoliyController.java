package cn.com.tass.chsmc.modules.system.controller;

import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.tass.chsmc.constant.MenuKeyConst;
import cn.com.tass.chsmc.constant.SessionConst;
import cn.com.tass.chsmc.interceptor.annotation.LogAnnotation;
import cn.com.tass.chsmc.interceptor.annotation.MenuAnnotaion;
import cn.com.tass.chsmc.modules.common.controller.BaseController;
import cn.com.tass.chsmc.modules.system.model.PassWordPoliy;
import cn.com.tass.chsmc.modules.system.model.User;
import cn.com.tass.chsmc.modules.system.service.PassWordPoliyService;

import cn.com.tass.chsmc.web.json.JsonResponse;
import cn.com.tass.chsmc.web.pagination.DtPageable;
import cn.com.tass.chsmc.web.pagination.Page;

/**
 * 标题: 生成策略管理控制器
 * <p>
 * 描述: 生成策略管理控制器
 * <p>
 * 版权: Copyright (c) 2016
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 * 
 * @author JNTA Codegenerator [lucan@tass.com.cn]
 * @created 2016-03-28 11:53:09
 * @version 1.0
 */
@Controller
@RequestMapping("/passWordPoliy")
public class PassWordPoliyController extends BaseController {

	@Autowired
	private PassWordPoliyService passWordPoliyService;

	/**
	 * 跳转至生成策略列表页面
	 * @return  生成策略列表视图
	 */
	@RequestMapping(value = "/toList", method = RequestMethod.GET)
	@MenuAnnotaion(menuKey=MenuKeyConst.SYS_PWD_POLICY_MENU)
	public String toList() {
		return "system/poliy/passWordPoliyList";
	}
	
	/**
	 * Ajax Json格式返回生成策略列表数据
	 * @param dtPageable dataTalbe分页对象
	 * @return dataTables 生成策略列表数据
	 */
	@RequestMapping(value = "/list", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResponse list(DtPageable dtPageable) throws Exception {
		JsonResponse ret = new JsonResponse();
		Page<PassWordPoliy> page = this.passWordPoliyService.listPassWordPoliy(dtPageable);
		ret.setRespData(page);
		return ret;
	}
	
	/**
	 * 跳转至新增页面
	 * @param model 页面数据模型
	 * @return 返回新增页面视图
	 * @throws Exception
	 */
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(Model model) throws Exception {
		model.addAttribute("passWordPoliy", new PassWordPoliy());
		return "system/poliy/passWordPoliyAdd";
	}

	/**
	 * 新增生成策略
	 * @param passWordPoliy  待新增的生成策略对象
	 * @return 返回操作结果
	 * @throws Exception
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	@LogAnnotation(logName="passwordpoliy.passWordPoliy.add")
	public JsonResponse add(@ModelAttribute("passWordPoliy") PassWordPoliy passWordPoliy) throws Exception {
		JsonResponse ret = new JsonResponse();
		User user = (User) getSession().getAttribute(SessionConst.UserInfo);
		passWordPoliy.setUser(user);
		this.passWordPoliyService.save(passWordPoliy);
		ret.setRespMessage(getResourceText("common.form.save.success"));
		return ret;
	}

	/**
	 * 跳转至编辑页面
	 * @param model 页面数据模型
	 * @param testId 待修改的Test对象Id
	 * @return 返回修改页面视图
	 * @throws Exception
	 */
	@RequestMapping(value = "/toEdit/{passWordPoliyId}", method = RequestMethod.GET)
	public String toEdit(ModelMap model, @PathVariable("passWordPoliyId") Integer passWordPoliyId)throws Exception {
		model.addAttribute("passWordPoliy", this.passWordPoliyService.getById(passWordPoliyId));
		return "system/poliy/passWordPoliyEdit";
	}

	/**
	 * 编辑生成策略
	 * @param passWordPoliy 待编辑的生成策略对象
	 * @return 返回编辑结果
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	@LogAnnotation(logName="passwordpoliy.passWordPoliy.edit")
	public JsonResponse edit(@ModelAttribute("passWordPoliy") PassWordPoliy passWordPoliy) throws Exception {
		JsonResponse ret = new JsonResponse();
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		passWordPoliy.setUpdateTime(timestamp);
		this.passWordPoliyService.update(passWordPoliy);
		ret.setRespMessage(getResourceText("common.form.save.success"));
		return ret;
	}

	/**
	 * 删除生成策略
	 * @param ids 待删除的生成策略对象Id列表
	 * @return 删除结果
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	@LogAnnotation(logName="passwordpoliy.passWordPoliy.delete")
	public JsonResponse delete(int[] ids) throws Exception {
//		String status = "0";
		JsonResponse ret = new JsonResponse();
		this.passWordPoliyService.deleteByIds(ids);
		ret.setRespMessage(getResourceText("common.table.delete.succes"));
		return ret;
	}
	
	@RequestMapping(value="/toView")
	public String poliyToView(HttpServletRequest request) throws Exception{
		String policyIDs = request.getParameter("policyID");
		PassWordPoliy poliy = new PassWordPoliy();
		poliy = this.passWordPoliyService.getById(Integer.parseInt(policyIDs));
		request.setAttribute("poliy", poliy);
		return "system/poliy/passWordPoliyView";
	}
	
	@RequestMapping(value="/toDetail")
	@ResponseBody
	public JsonResponse poliyDetails(HttpServletRequest request) throws Exception{
		String policyIDs = request.getParameter("policyID");
		JsonResponse ret = new JsonResponse();
		PassWordPoliy poliy = this.passWordPoliyService.getById(Integer.parseInt(policyIDs));
		ret.setRespData(poliy);
		return ret;
	}
}
