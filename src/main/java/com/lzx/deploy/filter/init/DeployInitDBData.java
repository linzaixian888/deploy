package com.lzx.deploy.filter.init;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lzx.deploy.filter.CheckConfFilter;
import com.lzx.deploy.filter.Filter;
import com.lzx.deploy.filter.FilterChain;
import com.lzx.deploy.filter.NotNullConfFilter;
import com.lzx.deploy.pojo.ChildTable;
import com.lzx.deploy.pojo.Column;
import com.lzx.deploy.pojo.MyClass;
import com.lzx.deploy.pojo.MyField;
import com.lzx.deploy.pojo.ParentTable;
import com.lzx.deploy.pojo.Table;
import com.lzx.deploy.util.DBHelper;
import com.lzx.deploy.util.StringUtil;
/**
 * 初始化数据库数据的类
 * @author lzx
 *
 */
public class DeployInitDBData extends NotNullConfFilter{
	private static Logger logger=LoggerFactory.getLogger(DeployInitDBData.class);
	private List<Table> tables;
	private DBHelper db;
	public void process(FilterChain filterChain) {
		String driver=(String) filterChain.get("driver");
		String url=(String) filterChain.get("url");
		String username=(String) filterChain.get("username");
		String password=(String) filterChain.get("password");
		db=new DBHelper(driver, url, username, password);
		tables=db.getAllTable();
		logger.debug("begin---开始对数据库数据进行初始化");
		for(Table table:tables){
			MyClass myClass=new MyClass();
			String idName=table.getIdColumn().getColumnName();
			if("".equals(idName)||idName==null){
				logger.warn("{}表没有设置主键",table.getTableName());
				myClass.setIdField(null);
//				throw new RuntimeException(table.getTableName()+"表没有设置主键，请设置主键");
			}else{
				//对id属性的赋值
				myClass.setIdField(new MyField());
				myClass.getIdField().setColumnName(table.getIdColumn().getColumnName());
				myClass.getIdField().setType(getTypeString(table.getIdColumn().getDataType(), table.getIdColumn(), table));
				myClass.getIdField().setName(toIdField(table.getIdColumn().getColumnName()));
				myClass.getIdField().setRemark(table.getIdColumn().getRemarks());
				if(myClass.getIdField().getRemark()==null){
					myClass.getIdField().setRemark("");
				}
			}
			//对表信息的处理
			myClass.setTableName(table.getTableName());
			myClass.setClassName(toClassName(table.getTableName()));
			myClass.setRemark(table.getRemark());
			processOneTable(table,myClass);
			filterChain.addClassList(myClass);
			logger.debug("初始化:{}",myClass.getClassName());
			logger.debug("{}",myClass);
		}
		logger.debug("end---成功对数据库数据进行了初始化");
	}
	/**
	 * 处理一个表
	 * @param table
	 * @param myClass
	 */
	private void processOneTable(Table table, MyClass myClass){
		List<ParentTable> parentTables=table.getParents();
		List<ChildTable> childTables=table.getChilds();
		List<Column> columns=table.getColumns();
		List<String> columnNames=new ArrayList<String>();
		for(Column column:columns){
			MyField field=new MyField();
			field.setName(toFieid(column.getColumnName()));
			field.setColumnName(column.getColumnName());
			field.setType(getTypeString(column.getDataType(),column,table));
			field.setRemark(column.getRemarks());
			//对没有列注释的处理
			if(field.getRemark()==null){
				field.setRemark("");
			}
			myClass.add(field);
			columnNames.add(field.getName());
		}
		for(ParentTable parentTable:parentTables){
			MyField parentField=new MyField();
			parentField.setName(toParentField(parentTable,columnNames));
			parentField.setType(toClassName(parentTable.getTableName()));
			parentField.setColumnName(parentTable.getColumnName());
			parentField.setPkTable(parentTable.getFkTable());
			parentField.setFkTable(parentTable.getFkTable());
			parentField.setPkColumn(parentTable.getPkColumn());
			parentField.setFkColumn(parentTable.getFkColumn());
			myClass.addParent(parentField);
			checkColumnName(table, columnNames, parentField.getName());
			columnNames.add(parentField.getName());
			
		}
		for(ChildTable childTable:childTables){
			MyField childField=new MyField();
			childField.setType(toClassName(childTable.getTableName()));
			childField.setName(toChildFiled(childTable,columnNames));
			childField.setColumnName(childTable.getColumnName());
			myClass.addChild(childField);
			checkColumnName(table, columnNames, childField.getName());
			columnNames.add(childField.getName());
		}
	}
	/**
	 * 检查列名是否重复
	 * @param table
	 * @param columnNames
	 * @param name
	 */
	private void checkColumnName(Table table,List<String> columnNames,String name){
		if(columnNames.contains(name)){
			logger.error("{}表的列名{}重复了",table.getTableName(),name);
			throw new RuntimeException(table.getTableName()+"表的列名:"+name+"重复了");
		}
	}
	/**
	 * 数据库类型转换为java类型
	 * @param type
	 * @param column
	 * @param table
	 * @return
	 */
	private String getTypeString(int type,Column column,Table table){
		String temp="String";
		switch (type) {
		//常量值是4
		case Types.INTEGER:
			temp="Integer";break;
		//常量值是12
		case Types.VARCHAR:
			temp="String";break;
		//常量值是-1
		case Types.CHAR:
		case Types.LONGVARCHAR:
			temp="String";break;
		case Types.TIMESTAMP:
			temp="Date";break;
		case Types.BIT:
			temp="Boolean";break;
		case Types.SMALLINT:
			temp="Integer";break;
		case Types.BIGINT:
			temp="Long";break;
		case Types.DECIMAL:
			temp="Float";break;
		case Types.DATE:
			temp="Date";break;
		case Types.LONGVARBINARY:
			temp="byte[]";break;
		default:
			logger.error("{}表的{}列的数据类型 [{}]未定义",table.getTableName(),column.getColumnName(),type);
			throw new RuntimeException(table.getTableName()+"表的"+column.getColumnName()+"列的数据类型["+type+"]未定义");
//			temp="String";
		}
		return temp;
	}
	/**
	 * 表名转换成类名
	 * @param tableName
	 * @return
	 */
	private String toClassName(String tableName){
//		tableName=tableName.substring(3);
		tableName=StringUtil.trimUnderLine(tableName);
		return StringUtil.firstUp(tableName);
	}
	/**
	 * 与子表的关联字段命名
	 * @param childTable
	 * @param columnNames 
	 * @return
	 */
	private String toChildFiled(ChildTable childTable, List<String> columnNames){
		String name=StringUtil.firstLow(toClassName(childTable.getTableName()))  ;
		String columnName=name+"List";
		if(columnNames.contains(columnName)){
			columnName=StringUtil.firstUp(columnName);
			columnName=toFieid(childTable.getColumnName())+columnName;
		}
		return columnName;
	}
	/**
	 * 与父表的关联字段命名
	 * @param columnName
	 * @return
	 */
	private String toParentField(ParentTable parent,List<String> columnNames){
		String columnName=parent.getColumnName();
		columnName=trimID(columnName);
		if(columnNames.contains(columnName)||"".equals(columnName)){
			columnName=parent.getTableName();
		}
		return columnName;
	}
	/**
	 * 将列名转换为类的属性名
	 * @param columnName
	 * @return
	 */
	private String toFieid(String columnName){
		return StringUtil.firstLow(StringUtil.trimUnderLine(columnName));
	}
	/**
	 * 将id列转换为类的属性名
	 * @param columnName
	 * @return
	 */
	private String toIdField(String columnName){
		return toFieid(columnName);
	}
	/**
	 * 去除后面的id字符串(不区分大小写)
	 * @param str
	 * @return
	 */
	private String trimID(String str){
		int length=str.length();
		String temp=str.substring(length-2);
		if(temp.toLowerCase().equals("id")){
			return str.substring(0, length-2);
		}
		return str;
	}
	
	public static void main(String[] args) {
		DeployInitDBData d=new DeployInitDBData();
		d.process(new FilterChain());
		
	}
	@Override
	public String[] getConfNames() {
		// TODO Auto-generated method stub
		return new String[]{"url","driver","username","password"};
	}
	

}
