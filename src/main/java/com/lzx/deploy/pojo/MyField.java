package com.lzx.deploy.pojo;

public class MyField {
	private String name;
	private String columnName;
	private String type;
	private String remark;
	//有主外键关连的属性
	private String pkTable;
	private String fkTable;
	private String pkColumn;
	private String fkColumn;
	
	public String getPkTable() {
		return pkTable;
	}
	public void setPkTable(String pkTable) {
		this.pkTable = pkTable;
	}
	public String getFkTable() {
		return fkTable;
	}
	public void setFkTable(String fkTable) {
		this.fkTable = fkTable;
	}
	public String getPkColumn() {
		return pkColumn;
	}
	public void setPkColumn(String pkColumn) {
		this.pkColumn = pkColumn;
	}
	public String getFkColumn() {
		return fkColumn;
	}
	public void setFkColumn(String fkColumn) {
		this.fkColumn = fkColumn;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "MyField [name=" + name + ", columnName=" + columnName
				+ ", type=" + type + ", remark=" + remark + ", pkTable="
				+ pkTable + ", fkTable=" + fkTable + ", pkColumn=" + pkColumn
				+ ", fkColumn=" + fkColumn + "]";
	}
}
