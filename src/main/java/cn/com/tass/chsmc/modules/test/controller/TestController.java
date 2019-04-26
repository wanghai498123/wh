package cn.com.tass.chsmc.modules.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.tass.chsmc.modules.common.controller.BaseController;
import cn.com.tass.chsmc.modules.test.model.Test;
import cn.com.tass.chsmc.modules.test.service.TestService;
import cn.com.tass.chsmc.web.json.JsonResponse;
import cn.com.tass.chsmc.web.pagination.DtPageable;
import cn.com.tass.chsmc.web.pagination.Page;

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
 * @created 2016-1-28 下午04:34:49
 * @version 1.0
 */
@Controller
@RequestMapping("/test")
public class TestController extends BaseController {

	@Autowired
	private TestService testService;

	@RequestMapping(value = "/toList", method = RequestMethod.GET)
	public String toList(Model model) {
		return "testManage/ssss/testList";
	}

	@RequestMapping(value = "/toList2", method = RequestMethod.GET)
	public String toList2(Model model) {
		return "testManage/ssss/testList2";
	}

	@RequestMapping(value = "/list", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public JsonResponse list(DtPageable dtPageable) {
		JsonResponse ret = new JsonResponse();
		Page<Test> page = this.testService.listTest(dtPageable);
		ret.setRespData(page);
		return ret;
	}

	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(ModelMap model) throws Exception {
		model.addAttribute("test", new Test());
		return "testManage/ssss/testAdd";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse add(@ModelAttribute("test") Test test,
			BindingResult result) throws Exception {
		JsonResponse ret = new JsonResponse();
		this.testService.save(test);
		ret.setRespMessage(getResourceText("common.form.save.success"));
		return ret;
	}

	@RequestMapping(value = "/toEdit/{testId}", method = RequestMethod.GET)
	public String toEdit(ModelMap model, @PathVariable("testId") Integer testId)
			throws Exception {
		model.addAttribute("test", this.testService.getById(testId));
		return "testManage/ssss/testEdit";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse edit(@ModelAttribute("test") Test test,
			BindingResult result) throws Exception {
		JsonResponse ret = new JsonResponse();
		this.testService.update(test);
		ret.setRespMessage(getResourceText("common.form.save.success"));
		return ret;
	}

	@RequestMapping(value = "/delete")
	@ResponseBody
	public JsonResponse delete(int[] ids) throws Exception {
		JsonResponse ret = new JsonResponse();
		this.testService.deleteByIds(ids);
		ret.setRespMessage(getResourceText("common.table.delete.succes"));
		return ret;
	}
}
