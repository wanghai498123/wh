package cn.com.tass.codeg.model;

public class ColumnInfo {
	private String columnName;
	private String ordinalPosition;
	private String isNullable;
	private String columnDefault;
	private String columnType;
	private String columnKey;
	private String extra;
	private String columnComment;

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getOrdinalPosition() {
		return ordinalPosition;
	}

	public void setOrdinalPosition(String ordinalPosition) {
		this.ordinalPosition = ordinalPosition;
	}

	public String getIsNullable() {
		return isNullable;
	}

	public void setIsNullable(String isNullable) {
		this.isNullable = isNullable;
	}

	public String getColumnDefault() {
		return columnDefault;
	}

	public void setColumnDefault(String columnDefault) {
		this.columnDefault = columnDefault;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getColumnKey() {
		return columnKey;
	}

	public void setColumnKey(String columnKey) {
		this.columnKey = columnKey;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public String getColumnComment() {
		return columnComment;
	}

	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}

	/**
	 * 获取属性名称
	 * 
	 * @return
	 */
	public String getPropertyName() {
		return this.columnName;
	}

	/**
	 * 获取属性类型
	 * 
	 * @return
	 */
	public String getPropertyType() {
		String type = columnType.toLowerCase();
		String propertyType = null;
		if (type.startsWith("int")) {
			propertyType = "int";
		} else if (type.startsWith("tinyint")) { // long
			propertyType = "int";
		} else if (type.startsWith("bigint")) { // long
			propertyType = "long";
		} else if (type.startsWith("double")) { // double
			propertyType = "double";
		} else if (type.startsWith("float")) { // float
			propertyType = "float";
		} else if (type.startsWith("varchar")) { // String
			propertyType = "String";
		} else if (type.startsWith("char")) { // String
			propertyType = "String";
		} else if (type.startsWith("text")) { // String
			propertyType = "String";
		} else if (type.startsWith("date")) { // date
			propertyType = "Date";
		} else if (type.startsWith("datetime")) { // date
			propertyType = "Date";
		} else if (type.startsWith("timestamp")) { // date
			propertyType = "Date";

		} else {
			System.out.println("==类型[" + type + "]解析尚不支持==");
		}
		return propertyType;
	}

}
