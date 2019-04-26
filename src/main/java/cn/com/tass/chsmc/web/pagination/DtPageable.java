package cn.com.tass.chsmc.web.pagination;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 标题: JQuery Datatables分页参数对象
 * <p>
 * 描述: JQuery Datatables分页参数对象
 * <p>
 * 版权: Copyright (c) 2015
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 *
 * @author 卢灿 [lucan@tass.com.cn]
 * @version 1.0
 * created 2016-2-23 下午02:56:40
 */
public class DtPageable {

    // 分页页数
    private int page = 1;

    // 分页页面大小
    private int rows = 10;

    // 分页排序列
    private List<OrderCol> orderCols = new ArrayList<OrderCol>();

    // 分页查询参数
    private Map<String, String> paramMap = new HashMap<String, String>();

    public Integer getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public List<OrderCol> getOrderCols() {
        return orderCols;
    }

    public Map<String, String> getParamMap() {
        return paramMap;
    }

    /**
     * 拼接排序列信息
     *
     * @return 返回排序列字符串
     */
    public String getOderColsStr() {

        String orderCols = " ";
        if (this.orderCols.size() > 0)
            orderCols = orderCols + "ORDER BY ";

        for (OrderCol orderCol : this.orderCols) {
            orderCols += orderCol.getOrderColName() + " " + orderCol.getOrderColType() + " ";
        }

        return orderCols;
    }

    /**
     * 获取默认全部页面的对象，用于ListAll方法
     *
     * @return DtPageable对象
     */
    public static DtPageable getAllDtPageable() {

        DtPageable dtPageable = new DtPageable();
        dtPageable.setRows(0);
        dtPageable.setPage(0);
        return dtPageable;
    }

    /**
     * 标题: DataTable排序列
     * <p>
     * 描述: DataTable排序列对象
     * <p>
     * 版权: Copyright (c) 2015
     * <p>
     * 公司: 江南天安 [www.tass.com.cn]
     * <p>
     *
     * @author 卢灿 [lucan@tass.com.cn]
     *         created 2016-2-23 下午10:11:19
     * @version 1.0
     */
    static class OrderCol {

        private String orderColName;

        private String orderColType;

        /**
         * 排序的列名
         *
         * @return 返回排序列名
         */
        String getOrderColName() {
            return orderColName;
        }

        void setOrderColName(String orderColName) {
            this.orderColName = orderColName;
        }

        /**
         * 排序类型（升序\降序）
         *
         * @return 返回排序类型
         */
        String getOrderColType() {
            return orderColType;
        }

        void setOrderColType(String orderColType) {
            this.orderColType = orderColType;
        }
    }
}
