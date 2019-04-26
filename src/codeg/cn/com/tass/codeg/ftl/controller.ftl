package cn.com.tass.chsmc.modules.${e_ModuleName}.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.tass.chsmc.interceptor.annotation.LogAnnotation;
import cn.com.tass.chsmc.modules.common.controller.BaseController;
import cn.com.tass.chsmc.modules.${e_ModuleName}.model.${e_ClassName};
import cn.com.tass.chsmc.modules.${e_ModuleName}.service.${e_ClassName}Service;
import cn.com.tass.chsmc.web.json.JsonResponse;
import cn.com.tass.chsmc.web.pagination.DtPageable;
import cn.com.tass.chsmc.web.pagination.Page;

/**
 * 标题: ${e_ClassComment}管理控制器
 * <p>
 * 描述: ${e_ClassComment}管理控制器
 * <p>
 * 版权: Copyright (c) 2016
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 * 
 * @author JNTA Codegenerator
 * @created ${e_SysTime?string("yyyy-MM-dd HH:mm:ss")}
 * @version 1.0
 */
@Controller
@RequestMapping("/${e_ClassName?uncap_first}")
public class ${e_ClassName}Controller extends BaseController {

	@Autowired
	private ${e_ClassName}Service ${e_ClassName?uncap_first}Service;

	/**
	 * 跳转至${e_ClassComment}列表页面
	 * @return  ${e_ClassComment}列表视图
	 */
	@RequestMapping(value = "/toList", method = RequestMethod.GET)
	public String toList() {
		return "${e_ModuleName}/${e_ClassName?uncap_first}/${e_ClassName?uncap_first}List";
	}
	
	/**
	 * Ajax Json格式返回${e_ClassComment}列表数据
	 * @param dtPageable dataTalbe分页对象
	 * @return dataTables ${e_ClassComment}列表数据
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public JsonResponse list(DtPageable dtPageable) throws Exception {
		JsonResponse ret = new JsonResponse();
		Page<${e_ClassName}> page = this.${e_ClassName?uncap_first}Service.list${e_ClassName}(dtPageable);
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
		model.addAttribute("${e_ClassName?uncap_first}", new ${e_ClassName}());
		return "${e_ModuleName}/${e_ClassName?uncap_first}/${e_ClassName?uncap_first}Add";
	}

	/**
	 * 新增${e_ClassComment}
	 * @param ${e_ClassName?uncap_first}  待新增的${e_ClassComment}对象
	 * @return 返回操作结果
	 * @throws Exception
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	@LogAnnotation(logName="${e_ModuleName}.${e_ClassName?uncap_first}.add")
	public JsonResponse add(@ModelAttribute("${e_ClassName?uncap_first}") ${e_ClassName} ${e_ClassName?uncap_first}) throws Exception {
		JsonResponse ret = new JsonResponse();
		this.${e_ClassName?uncap_first}Service.save(${e_ClassName?uncap_first});
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
	@RequestMapping(value = "/toEdit/{${e_ClassName?uncap_first}Id}", method = RequestMethod.GET)
	public String toEdit(ModelMap model, @PathVariable("${e_ClassName?uncap_first}Id") Integer ${e_ClassName?uncap_first}Id)
			throws Exception {
		model.addAttribute("${e_ClassName?uncap_first}", this.${e_ClassName?uncap_first}Service.getById(${e_ClassName?uncap_first}Id));
		return "${e_ModuleName}/${e_ClassName?uncap_first}/${e_ClassName?uncap_first}Edit";
	}

	/**
	 * 编辑${e_ClassComment}
	 * @param ${e_ClassName?uncap_first} 待编辑的${e_ClassComment}对象
	 * @return 返回编辑结果
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	@LogAnnotation(logName="${e_ModuleName}.${e_ClassName?uncap_first}.edit")
	public JsonResponse edit(@ModelAttribute("${e_ClassName?uncap_first}") ${e_ClassName} ${e_ClassName?uncap_first}) throws Exception {
		JsonResponse ret = new JsonResponse();
		this.${e_ClassName?uncap_first}Service.update(${e_ClassName?uncap_first});
		ret.setRespMessage(getResourceText("common.form.save.success"));
		return ret;
	}

	/**
	 * 删除${e_ClassComment}
	 * @param ids 待删除的${e_ClassComment}对象Id列表
	 * @return 删除结果
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	@LogAnnotation(logName="${e_ModuleName}.${e_ClassName?uncap_first}.delete")
	public JsonResponse delete(int[] ids) throws Exception {
		JsonResponse ret = new JsonResponse();
		this.${e_ClassName?uncap_first}Service.deleteByIds(ids);
		ret.setRespMessage(getResourceText("common.table.delete.succes"));
		return ret;
	}
}
