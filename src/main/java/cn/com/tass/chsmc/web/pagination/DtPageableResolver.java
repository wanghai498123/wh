package cn.com.tass.chsmc.web.pagination;

import java.util.Iterator;
import java.util.Map;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import cn.com.tass.chsmc.utils.DataTypeUtils;
import cn.com.tass.chsmc.web.pagination.DtPageable.OrderCol;

/**
 * 标题: JQuery Datatables 分页参数解析器
 * <p>
 * 描述: 用户自动注入绑定DtPageable对象
 * <p>
 * 版权: Copyright (c) 2015
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 * 
 * @author 卢灿 [lucan@tass.com.cn]
 * @created 2016-2-23 下午03:10:02
 * @version 1.0
 */
public class DtPageableResolver implements HandlerMethodArgumentResolver {

	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

		return buildDtPageable(webRequest);
	}

	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().equals(DtPageable.class);
	}

	private DtPageable buildDtPageable(NativeWebRequest request) {

		DtPageable dtPageable = new DtPageable();

		setOrderColsInfo(request, dtPageable);

		setPageInfo(request, dtPageable);

		setParaMap(request, dtPageable);

		return dtPageable;
	}

	/**
	 * 设置排序信息
	 * 
	 * @param request
	 * @param dtPageable
	 */
	private void setOrderColsInfo(NativeWebRequest request, DtPageable dtPageable) {

		String sColumns = request.getParameter("sColumns");
		int colsCnt = DataTypeUtils.str2Int(request.getParameter("iColumns"));
		String[] colNames = sColumns.split(",");
		
		//类似这样的列名，最后一个列为按钮列：id,,vsmMode,versionCode,digest,filePath, 
		if(sColumns.lastIndexOf(",")==(sColumns.length()-1))
			colsCnt=colsCnt-1;
		
		int sortCols=DataTypeUtils.str2Int(request.getParameter("iSortingCols"));
		if(sortCols>0 && !DataTypeUtils.isEmpty(sColumns) && (colNames.length == colsCnt)){
			for (int i = 0; i < sortCols; i++) {
				int colIndex = DataTypeUtils.str2Int(request.getParameter("iSortCol_" + String.valueOf(i)));
				if (colIndex < colNames.length) {
					String colName = colNames[colIndex];
					if(!DataTypeUtils.isEmpty(colName)){
						String orderType = DataTypeUtils.getEmptyDefStr(request.getParameter("sSortDir_"+ String.valueOf(i)), "asc");

						OrderCol orderCol = new OrderCol();
						orderCol.setOrderColName(colName);
						orderCol.setOrderColType(orderType);

						dtPageable.getOrderCols().add(orderCol);
					}
				}
			}
		}
	}

	/**
	 * 设置分页信息
	 * 
	 * @param request
	 * @param dtPageable
	 */
	private void setPageInfo(NativeWebRequest request, DtPageable dtPageable) {

		int iDisplayStart = DataTypeUtils.str2Int(request.getParameter("iDisplayStart"));
		int iDisplayLength = DataTypeUtils.str2Int(request.getParameter("iDisplayLength"), 10); // 每页数量

		int rows = iDisplayLength;
		if (rows < 10) {
			rows = 10; // 最小分页为10
		}

		int page = iDisplayStart / rows + 1;

		dtPageable.setPage(page);
		dtPageable.setRows(rows);
	}

	/**
	 * 设置分页查询参数
	 * 
	 * @param request
	 * @param dtPageable
	 */
	@SuppressWarnings("unchecked")
	private void setParaMap(NativeWebRequest request, DtPageable dtPageable) {

		Map parasMap = request.getParameterMap();
		Iterator it = parasMap.keySet().iterator();
		String paraName = "";
		String paraVal = "";

		while (it.hasNext()) {

			String key = it.next().toString();
			int index = key.indexOf("dtp.");
			if (index >= 0) {
				// 所有查询参数以dtp.为前缀，扩展的dataTables的ServerParams将查询参数添加dtp.前缀
				paraName = key.substring(index + "dtp.".length()).trim();
				if (!dtPageable.getParamMap().containsKey(paraName)) {
					paraVal = request.getParameter(key);

					dtPageable.getParamMap().put(paraName, paraVal);
				}
			}
		}
	}
}
