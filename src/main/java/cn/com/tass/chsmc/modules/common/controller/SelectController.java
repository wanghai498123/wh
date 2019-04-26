package cn.com.tass.chsmc.modules.common.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import cn.com.tass.chsmc.modules.common.model.SelectInit;
import cn.com.tass.chsmc.modules.common.service.SelectService;
import cn.com.tass.chsmc.utils.DataTypeUtils;
import cn.com.tass.chsmc.web.json.JsonResponse;
import cn.com.tass.chsmc.web.pagination.SelectPageable;

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
@RequestMapping("/select")
public class SelectController {

	@Autowired
	private SelectService selectService;

	@RequestMapping(value = "/list")
	@ResponseBody
	public JsonResponse initSelectList(SelectPageable selectPageable)
			throws Exception {
		JsonResponse ret = new JsonResponse();
		String queryValue = selectPageable.getQueryValue();
		String selKey = selectPageable.getSelKey();
		int rows = selectPageable.getPageSize();
		int page = selectPageable.getPageNo();
		String exCondition = selectPageable.getExCondition();// 额外查询条件
		String refCondition = selectPageable.getRefCondition();// 关联查询条件
		SelectInit sel = (SelectInit) selectService.getSelectInitByKey(selKey);
		String hql = sel.getFromSql(); // 查询hql
		String countHql = sel.getCntSql(); // 查询总数
		String condition = DataTypeUtils.isEmpty(sel.getWhereSql()) ? " " : " "
				+ sel.getWhereSql();
		String querySql = sel.getQuerySql();// 查询填充语句
		Map<String, Object> param = new HashMap<String, Object>(); // 设置查询参数
		// 顺序要和语句中的参数一一对应
		if (!DataTypeUtils.isEmpty(queryValue) 
				&& !DataTypeUtils.isEmpty(querySql)) {
			condition += querySql;
			param.put(sel.getFieldName(), "%" + queryValue + "%");
		}
		// 拼接关联查询条件
		if (!DataTypeUtils.isEmpty(refCondition)) {
			condition += " and " + refCondition;
		}

		// 拼接额外条件
		if (!DataTypeUtils.isEmpty(exCondition)) {
			condition += " and " + exCondition;
		}

		// 拼接select的初始化sql
		condition += getInitSelectSql(sel.getQueryKey(), selectPageable
				.getsID());

		hql += condition;
		if (!DataTypeUtils.isEmpty(sel.getOrderSql())) {
			hql += " " + sel.getOrderSql();
		}

		// 如果没有分页sql拼接，则认为查询全部数据
		JSONObject obj = new JSONObject();
		List<SelectInit> resultList = null;
		if (DataTypeUtils.isEmpty(countHql)) {
			resultList = selectService.listselData(hql, param, -1, -1);
		} else {
			countHql += " " + condition;
			long total = selectService.count(countHql, param); // 查询总数
			resultList = selectService.listselData(hql, param, page, rows);
			obj.put("total", total);
		}

		obj.put("data", resultList);

		ret.setRespData(obj);
		return ret;
	}

	public String getInitSelectSql(String fieldName, String sID) {
		String res = "";
		if (!(DataTypeUtils.isEmpty(sID)))
			if (sID.indexOf(",") != -1) {
				sID = sID.replaceAll(",", "','");
				res = res + " and " + fieldName + " in('" + sID + "') ";
			} else {
				res = res + " and " + fieldName + " = '" + sID + "' ";
			}
		return res;
	}
}
