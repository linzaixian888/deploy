package com.lzx.deploy.pojo;

import java.util.ArrayList;
import java.util.List;

public class MyClass {
	private String className;
	private String tableName;
	MyField idField=null;
	private String remark;
	List<MyField> fields=new ArrayList<MyField>();
	List<MyField> parentFields=new ArrayList<MyField>();
	List<MyField> childFields=new ArrayList<MyField>();
	


	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public List<MyField> getFields() {
		return fields;
	}
	public void setFields(List<MyField> fields) {
		this.fields = fields;
	}
	public List<MyField> getParentFields() {
		return parentFields;
	}
	public void setParentFields(List<MyField> parentFields) {
		this.parentFields = parentFields;
	}
	public List<MyField> getChildFields() {
		return childFields;
	}
	public void setChildFields(List<MyField> childFields) {
		this.childFields = childFields;
	}
	public void add(MyField my){
		fields.add(my);
	}
	public void addParent(MyField my){
		parentFields.add(my);
	}
	public void addChild(MyField my){
		childFields.add(my);
	}

	public MyField getIdField() {
		return idField;
	}
	public void setIdField(MyField idField) {
		this.idField = idField;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "MyClass [className=" + className + ", tableName=" + tableName
				+ ", idField=" + idField + ", fields=" + fields
				+ ", parentFields=" + parentFields + ", childFields="
				+ childFields + "]";
	}
	
}
