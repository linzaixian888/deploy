package com.lzx.deploy.pojo;

public class Column {
	private String columnName;
	private int columnType;
	private String remark;
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public int getColumnType() {
		return columnType;
	}
	public void setColumnType(int columnType) {
		this.columnType = columnType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "Column [columnName=" + columnName + ", columnType="
				+ columnType + ", remark=" + remark + "]";
	}


	
}
