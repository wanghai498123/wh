package cn.com.tass.chsmc.modules.common.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import cn.com.tass.chsmc.modules.common.service.SelectService;
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
 * @created 2016-1-28 下午04:34:49
 * @version 1.0
 */
@Controller
public class IsExistController {

	@Autowired
	private SelectService selectService;

	/**
	 * 判断对象是否存在
	 * @throws Exception 
	 */
	@RequestMapping(value = "/isExist")
	@ResponseBody
	public JsonResponse isExist(String exsitJson) throws Exception {

			JSONObject json = JSONObject.parseObject(exsitJson);
			String existEntity = json.getString("existEntity");
			String existCondition = json.getString("existCondition");

			// 匹配value部分值
//			String reg = "(,\"value\":\")(.*)(\"})";
//			Pattern pattern = Pattern.compile(reg);
//			Matcher matcher = pattern.matcher(existCondition);
//			String valueGroup = "";
//			if (matcher.find()) {
//				valueGroup = matcher.group(2);// 取得value部分值
//			}
			// 重新组合字符串，处理双引号
			//existCondition = existCondition.replaceAll(reg, "$1" + valueGroup.replace("\"", "\\\\\\\"") + "$3");
			
			JSONArray conditionArray = JSONArray.parseArray(existCondition);
			String hql = " select count(*) from " + existEntity + " where 1=1 ";
			JSONObject result = new JSONObject();
			Map<String, Object> param = null;
			if (conditionArray.size() > 0) {
				param = new HashMap<String, Object>();
				for (int i = 0; i < conditionArray.size(); i++) {
					JSONObject obj = conditionArray.getJSONObject(i);
					String name = obj.getString("name");
					String operator = obj.getString("operator");
					String paramName = name.substring(name.lastIndexOf(".") + 1, name.length());
					hql += " and " + name + operator + ":" + paramName;
					param.put(paramName, obj.get("value"));
				}
			}
			long count = selectService.count(hql, param);
			if (count > 0) {
				result.put("isExist", true);
			} else {
				result.put("isExist", false);
			}
			
			JsonResponse ret = new JsonResponse();
			ret.setRespData(result);
			return ret;
	}
}
