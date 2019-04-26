package cn.com.tass.chsmc.modules.system.controller;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.tass.chsmc.constant.CommonConst;
import cn.com.tass.chsmc.constant.SessionConst;
import cn.com.tass.chsmc.interceptor.annotation.IgnoreLogin;
import cn.com.tass.chsmc.interceptor.annotation.LogAnnotation;
import cn.com.tass.chsmc.modules.common.controller.BaseController;
import cn.com.tass.chsmc.modules.system.model.Menu;
import cn.com.tass.chsmc.modules.system.model.User;
import cn.com.tass.chsmc.modules.system.service.UserService;
import cn.com.tass.chsmc.utils.DataTypeUtils;
import cn.com.tass.chsmc.utils.DateUtil;
import cn.com.tass.chsmc.web.json.JsonResponse;

/**
 * 标题: 
 * <p>
 * 描述: 
 * <p>
 * 版权: Copyright (c) 2015
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 * 
 * @author 卢灿 [lucan@tass.com.cn]
 * @created 2016-1-29 下午12:29:58
 * @version 1.0
 */
@Controller
public class LoginController extends BaseController{
	//用户被锁定
	private static String lock = "10010";
	//验证码不正确
	private static String verificationCode= "10011";
	//用户名密码错误
	private static String nameAndPassError = "10012";
	//用户不存在
	private static String userNotExist = "10013";
	//系统异常
	private static String systemError = "10014";
	//验证码为空
//	private static String varifyCodeNull = "10015";
	

	@Autowired
	private UserService userService;
	

	
	
	@RequestMapping(value="/login")
	@ResponseBody
	@IgnoreLogin
	@LogAnnotation(logName="common.login")
	public JsonResponse login(User user,@RequestParam("validCode") String validCode,
			         HttpSession session) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> result=new HashMap<String, Object>();
		JsonResponse jsonRes = new JsonResponse();
		Date date = new Date();
		String loginName = user.getLoginName();
		String passWord = user.getPassword();
		
		//获取生成的验证码
		String validateCode = (String) getSession().getAttribute("validateCode");
		
		try{
			user = this.userService.getUser(loginName);
		}catch(Exception e){
			jsonRes.setRespStatus(systemError);
			return jsonRes;
		}
		
		if (user== null) {
			//用户名不存在
			jsonRes.setRespStatus(userNotExist);
			return jsonRes;
		}
		
//		String dPassWord = EncryptUtil.DESDecrypt(user.getPassword());
//		if (!passWord.equals(dPassWord)) {
//			//密码错误
//			jsonRes.setRespStatus(nameAndPassError);
//			return jsonRes;
//		}
		if (!validateCode.equals(validCode)) {
			//验证码错误
			jsonRes.setRespStatus(verificationCode);
			return jsonRes;
		}
		
		//获取当前用户状态
		int status = user.getStatus();
		if (status != 1) {
			jsonRes.setRespStatus(lock);
			return jsonRes;
		}
		int userID = user.getUserID();
		String lastLoginTime = DateUtil.getDate(date);
		params.put("lastLoginTime", DateUtil.StringToDate(lastLoginTime));
		params.put("userID", userID);
		//更新用户登录时间
		this.userService.update(params);

		List<Menu> menusList = user.getRole().getList();
		List<Menu> menus = new ArrayList<Menu>();
		for (Menu menu : menusList) {
			//add by lwy in 20170209: status=2->隐藏菜单
			if (menu.getStatus() == 2) {
				continue;
			}
			menus.add(menu);
		}
		String lefMenuHtml = buildLeftMenuHtml(menus,false);
		String collpaseLeftMenuHtml = buildLeftMenuHtml(menus,true);

		Map<String, Integer> userMenuMap = getUserMenuMap(menus);

		getSession().setAttribute(SessionConst.UserMenuMapInfo, userMenuMap);
		getSession().setAttribute(SessionConst.UserMenuInfo, menus);
		getSession().setAttribute(SessionConst.LeftMenuHtml, lefMenuHtml);
		getSession().setAttribute(SessionConst.CollapseLeftMenuHtml, collpaseLeftMenuHtml);
		getSession().setAttribute(SessionConst.UserInfo, user);
		result.put("homePage", menus.get(0).getMenuUrl());
		jsonRes.setRespStatus(CommonConst.STATUS_SUCCESS);
		jsonRes.setRespData(result);
			
        return jsonRes;
	}

	private String buildLeftMenuHtml(List<Menu> menus,boolean isCollapse){
		// 拼接左侧公共菜单
		StringBuffer leftMenuHtml = new StringBuffer();
		StringBuffer minLeftMenuHtml = new StringBuffer();

		if(isCollapse){
			leftMenuHtml.append("<div id='sideMask' style='width:39px;'></div><aside id=\"sidebar\" style='width:39px;' class='isCollapse'>"+
					"<div class=\"side-options\" class='icon-arrow-right' style=\"cursor: pointer;width:39px;\">"+
					"<ul class=\"list-unstyled\">"+
					"<li>"+
					"<a title=\"\" class=\"act act-primary tip\" id=\"collapse-nav\" href=\"javascript:void(0)\" data-original-title=\"Collapse navigation\"><i style=\"font-size: 15px;\" class=\"icon-arrow-right\"></i></a>"+
					"</li>"+
					" </ul>"+
					" </div>"+
					"<div class=\"sidebar-wrapper\">"+
					"<div id=\"mainnav\">"+
					"<ul id=\"accordion-menu-js\" class=\"nav nav-list\">");
			
			minLeftMenuHtml.append(" <div id=\"sideMask-min\"></div><aside id=\"menu_min\" class=\"isCollapse\" style='width:39px;'>  <div class=\"side-options\"  class='icon-arrow-right' style=\"cursor: pointer;width:39px;\">" +
					"<ul class=\"list-unstyled\"> <li>" +
					"<a title=\"\" class=\"act act-primary tip\" id=\"collapse-nav\" href=\"javascript:void(0)\" data-original-title=\"Collapse navigation\"><i style=\"font-size: 15px;\" class=\"icon-arrow-right\"></i></a>" +
					"</li> </ul> </div> <div class=\"sidebar-wrapper\"> <div id=\"mainnav\">" +
					"<ul id=\"accordion-menu-js1\"  class=\"nav nav-list\">");
		}else {
			leftMenuHtml.append("<aside id=\"sidebar\" >"+
					"<div class=\"side-options\" style=\"cursor: pointer;\">"+
					"<ul class=\"list-unstyled\">"+
					"<li>"+
					"<a title=\"\" class=\"act act-primary tip\" id=\"collapse-nav\" href=\"javascript:void(0)\" data-original-title=\"Collapse navigation\"><i style=\"font-size: 15px;\" class=\"icon-arrow-left\"></i></a>"+
					"</li>"+
					" </ul>"+
					" </div>"+
					"<div class=\"sidebar-wrapper\">"+
					"<div id=\"mainnav\">"+
					"<ul id=\"accordion-menu-js\" class=\"nav nav-list\">");
			
			minLeftMenuHtml.append(" <div id=\"sideMask-min\"></div><aside id=\"menu_min\" class=\"isCollapse\">  <div class=\"side-options\" style=\"cursor: pointer;\">" +
					"<ul class=\"list-unstyled\"> <li>" +
					"<a title=\"\" class=\"act act-primary tip\" id=\"collapse-nav\" href=\"javascript:void(0)\" data-original-title=\"Collapse navigation\"><i style=\"font-size: 15px;\" class=\"icon-arrow-right\"></i></a>" +
					"</li> </ul> </div> <div class=\"sidebar-wrapper\"> <div id=\"mainnav\">" +
					"<ul id=\"accordion-menu-js1\"  class=\"nav nav-list\">");
		}
		for (Menu menu : menus) {

			if (menu.getParentMenu()==null) {
				
				leftMenuHtml.append("<li id=\"parentMenuID" + menu.getMenuID() + "\">");
				minLeftMenuHtml.append("<li id=\"mparentMenuID" + menu.getMenuID() + "\">");
				
				if (menu.getChildMenuList().size() == 0) {
					leftMenuHtml.append("<a  id=\"subMenuID" + menu.getMenuID() + "\" class=\"rotateOut\" href=\"" + (DataTypeUtils.isEmpty(menu.getMenuUrl()) ? "'javascript:void(0);'" : menu.getMenuUrl()) + "\">" +
									"<span class=\"icon\" ><i class=\"" + menu.getIconName() + "\"></i></span>" +
									"<span class=\"txt\">" + menu.getMenuName() + "</span>" +
									"</a>");
					minLeftMenuHtml.append("<a  id=\"msubMenuID" + menu.getMenuID() + "\" class=\"rotateOut\" href=\"" + (DataTypeUtils.isEmpty(menu.getMenuUrl()) ? "'javascript:void(0);'" : menu.getMenuUrl()) + "\">" +
									"<span class=\"icon\" ><i class=\"" +menu.getIconName() + "\"></i></span>" +
									"<span class=\"txt\">" + menu.getMenuName() + "</span></a>");
				} else {
					//add by lwy in 20170209: status=2->隐藏菜单
					leftMenuHtml.append("<a class=\"notExpand rotateOut\" href=\"javascript:void(0)\">" +
									"<span class=\"icon\"><i class=\"" + menu.getIconName() + "\"></i></span>" +
									"<span class=\"txt\">" + menu.getMenuName() + "</span>" +
									"<span class=\"more\"><i class=\"icon-chevron-down  pull-right\" style=\" font-size:11px;margin-top:4px;margin-right:5px;\"></i></span>" +
									"</a>");

					leftMenuHtml.append(" <ul id=\"subul" + menu.getMenuID() + "\" class=\"sub\" style=\"display: none;\">");

					minLeftMenuHtml.append("<a class=\"notExpand rotateOut\" href=\"javascript:void(0)\">" +
									"<span class=\"icon\"><i class=\"" + menu.getIconName() + "\"></i></span>" +
									"<span class=\"txt\">" + menu.getMenuName() + "</span>" +
									"<span class=\"more\"><i class=\"icon-chevron-down  pull-right\" style=\" font-size:11px;margin-top:4px;margin-right:5px;\"></i></span>" +
									"</a>");
					minLeftMenuHtml.append(" <ul id=\"msubul" + menu.getMenuID() + "\" class=\"sub\" style=\"display: none;\">");


					List<Menu> childMenus=menu.getChildMenuList();
				
					for (Menu sun : childMenus) {
						//add by lwy in 20170209: status=2->隐藏菜单
						if (sun.getStatus() == 2) {
							continue;
						}
						
						leftMenuHtml.append("<li><a id=\"subMenuID" + sun.getMenuID() + "\" href=\"" + (DataTypeUtils.isEmpty(sun.getMenuUrl()) ? "'javascript:void(0);'" : sun.getMenuUrl()) + "\">" +
											"<span class=\"icon\"><i class=\"" + sun.getIconName() + "\"></i></span>" +
											"<span class=\"txt\">" + sun.getMenuName() + "</span>" +
											"</a>");
						minLeftMenuHtml.append("<li id=\"msubMenuID" + sun.getMenuID() + "\">" +
											"<a  href=\"" + (DataTypeUtils.isEmpty(sun.getMenuUrl()) ? "'javascript:void(0);'" : sun.getMenuUrl()) + "\">" +
											"<span class=\"icon\"><i class=\"" + sun.getIconName() + "\"></i></span>" +
											"<span class=\"txt\">" + sun.getMenuName() + "</span>" +
											"</a>");
						}
						leftMenuHtml.append("</ul></li>");
						minLeftMenuHtml.append("</ul></li>");
					}
					
					leftMenuHtml.append("</li>");
					minLeftMenuHtml.append("</li>");
				}
			}
		

			leftMenuHtml.append(" </ul> </div> <div></aside>");
			minLeftMenuHtml.append(" </ul> </div> </div></aside>");
		
			leftMenuHtml.append(minLeftMenuHtml);
			return leftMenuHtml.toString();
		}

	private Map<String, Integer> getUserMenuMap(List<Menu> menus) {

		Map<String, Integer> menuMap = new HashMap<String, Integer>();
		for (Menu menu : menus) {
			if (!menuMap.containsKey(menu.getMenuKey())) {
				menuMap.put(menu.getMenuKey(), menu.getMenuID());
			}
		}
		return menuMap;
	}
}