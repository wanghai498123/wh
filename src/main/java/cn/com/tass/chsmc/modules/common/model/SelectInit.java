package cn.com.tass.chsmc.modules.common.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import cn.com.tass.chsmc.modules.common.model.BaseEntity;

@Entity
@Table(name = "t_sys_select_init")
public class SelectInit extends BaseEntity {
	/**
	 * @Fields serialVersionUID : TODO
	 **/
	private static final long serialVersionUID = 1L;

	/**
	 * 流水号
	 */
	private Integer selID;

	/**
	 * 下拉框标识
	 */
	private String selKey;

	/**
	 * from语句
	 */
	private String fromSql;

	/**
	 * where语句
	 */
	private String whereSql;

	/**
	 * order语句
	 */
	private String orderSql;

	/**
	 * group 语句
	 */
	private String groupSql;

	/**
	 * 数量统计sql
	 */
	private String cntSql;

	/**
	 * 查询sql
	 */
	private String querySql;

	/**
	 * 查询关键词
	 */
	private String queryKey;
	/**
	 * 字段名称
	 */
	private String fieldName;
	
	@Id
	@GeneratedValue
	public Integer getSelID() {
		return selID;
	}

	public void setSelID(Integer selID) {
		this.selID = selID;
	}

	public String getFromSql() {
		return fromSql;
	}

	public void setFromSql(String fromSql) {
		this.fromSql = fromSql;
	}

	public String getWhereSql() {
		return whereSql;
	}

	public void setWhereSql(String whereSql) {
		this.whereSql = whereSql;
	}

	public String getOrderSql() {
		return orderSql;
	}

	public void setOrderSql(String orderSql) {
		this.orderSql = orderSql;
	}

	public String getGroupSql() {
		return groupSql;
	}

	public void setGroupSql(String groupSql) {
		this.groupSql = groupSql;
	}

	public String getCntSql() {
		return cntSql;
	}

	public void setCntSql(String cntSql) {
		this.cntSql = cntSql;
	}

	public String getQuerySql() {
		return querySql;
	}

	public void setQuerySql(String querySql) {
		this.querySql = querySql;
	}

	public String getQueryKey() {
		return queryKey;
	}

	public void setQueryKey(String queryKey) {
		this.queryKey = queryKey;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getSelKey() {
		return selKey;
	}

	public void setSelKey(String selKey) {
		this.selKey = selKey;
	}
}
