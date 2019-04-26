package cn.com.tass.chsmc.modules.system.controller;

import java.util.ArrayList;
import java.util.List;

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
import cn.com.tass.chsmc.exception.SqlException;
import cn.com.tass.chsmc.interceptor.annotation.LogAnnotation;
import cn.com.tass.chsmc.interceptor.annotation.MenuAnnotaion;
import cn.com.tass.chsmc.modules.common.controller.BaseController;
import cn.com.tass.chsmc.modules.system.model.Menu;
import cn.com.tass.chsmc.modules.system.model.Role;
import cn.com.tass.chsmc.modules.system.service.MenuService;
import cn.com.tass.chsmc.modules.system.service.RoleService;
import cn.com.tass.chsmc.web.json.JsonResponse;
import cn.com.tass.chsmc.web.pagination.DtPageable;
import cn.com.tass.chsmc.web.pagination.Page;

/**
 * 标题: 角色管理控制器
 * <p>
 * 描述: 角色管理控制器
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
@RequestMapping("/role")
public class RoleController extends BaseController {

	private static String status = "0";
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private MenuService menuService;

	/**
	 * 跳转至角色列表页面
	 * @return  角色列表视图
	 */
	@RequestMapping(value = "/toList", method = RequestMethod.GET)
	@MenuAnnotaion(menuKey=MenuKeyConst.SYS_ROLE_MENU)
	public String toList() {
		return "system/role/roleList";
	}
	
	/**
	 * Ajax Json格式返回角色列表数据
	 * @param dtPageable dataTalbe分页对象
	 * @return dataTables 角色列表数据
	 */
	@RequestMapping(value = "/list", produces = "application/json;charset=UTF-8")
	@ResponseBody
	@MenuAnnotaion(menuKey=MenuKeyConst.SYS_ROLE_MENU)
	public JsonResponse list(DtPageable dtPageable) throws Exception {
		JsonResponse ret = new JsonResponse();
		Page<Role> page = this.roleService.listRole(dtPageable);
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
		model.addAttribute("role", new Role());
		List<Menu> menu = this.menuService.getMenuList();
		model.addAttribute("menu", menu);
		return "system/role/roleAdd";
	}

	/**
	 * 新增角色
	 * @param role  待新增的角色对象
	 * @return 返回操作结果
	 * @throws Exception
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	@LogAnnotation(logName="system.role.add")
	public JsonResponse addRole(@ModelAttribute("role") Role role,HttpServletRequest request) throws Exception {
		JsonResponse ret = new JsonResponse();
		List<Menu> menuList = new ArrayList<Menu>();
		Menu menu = new Menu();
		String[] subBox = request.getParameterValues("ids");	
		if (subBox == null) {
			ret.setRespStatus(status);
			return ret;
		}
		for (int i = 0; i < subBox.length; i++) {
			int sub = Integer.parseInt(subBox[i]);
			menu.setMenuID(sub);
			int id = menu.getMenuID();
			menuList.add(this.menuService.getById(id));
			role.setList(menuList);
			if (i < 1) {
				this.roleService.save(role);
			}else{
				this.roleService.update(role);
			}
		}
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
	@RequestMapping(value = "/toEdit/{roleId}", method = RequestMethod.GET)
	public String toEdit(ModelMap model, @PathVariable("roleId") Integer roleId)
			throws Exception {
		
		Role role = this.roleService.getById(roleId);
		List<Menu> subMenu = role.getList();
		
		model.addAttribute("role", this.roleService.getById(roleId));
		List<Menu> menu = this.menuService.getMenuList();
		Menu menus = null;
		if (menu != null) {
			for (int i = 0; i < menu.size(); i++) {
				menus = menu.get(i);
				for (int j = 0; j < subMenu.size(); j++) {
					Menu subMenus = subMenu.get(j);
					if (subMenus.getMenuID() == menus.getMenuID()) {
						menus.setIsChecked(1);
					}
				}
			}
		}
		model.addAttribute("menu", menu);
		return "system/role/roleEdit";
	}

	/**
	 * 编辑角色
	 * @param role 待编辑的角色对象
	 * @return 返回编辑结果
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	@LogAnnotation(logName="system.role.edit")
	public JsonResponse edit(@ModelAttribute("role") Role role,HttpServletRequest request) throws Exception {
		JsonResponse ret = new JsonResponse();
		String[] menuID = request.getParameterValues("ids");
		List<Menu> menus = new ArrayList<Menu>();
		if (menuID == null) {
			ret.setRespStatus(status);
			return ret;
		}
		for (int i = 0; i < menuID.length; i++) {
			Menu menu = this.menuService.getById(Integer.parseInt(menuID[i]));
			menus.add(menu);
		}
		role.setList(menus);
		this.roleService.update(role);
		ret.setRespMessage(getResourceText("common.form.save.success"));
		
		return ret;
	}

	/**
	 * 删除角色
	 * @param ids 待删除的角色对象Id列表
	 * @return 删除结果
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	@LogAnnotation(logName="system.role.delete")
	public JsonResponse delete(int[] ids) throws Exception {
		JsonResponse ret = new JsonResponse();
		this.roleService.deleteRole(ids);
		ret.setRespMessage(getResourceText("common.table.delete.succes"));
		return ret;
	}
	
	/**
	 *角色详情 
	 * @throws SqlException 
	 * @throws NumberFormatException 
	 */
	
	@RequestMapping(value="/roleView")
	public String roleDetail(HttpServletRequest request) throws NumberFormatException, SqlException {
		String roleID = request.getParameter("roleID");
		Role role = new Role();
        role =  this.roleService.getById(Integer.parseInt(roleID));
        request.setAttribute("menu", role.getList());
        request.setAttribute("role", role);
		return "system/role/roleView";
	}
}
