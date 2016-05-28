package com.lzx.deploy.util;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lzx.deploy.pojo.ChildTable;
import com.lzx.deploy.pojo.Column;
import com.lzx.deploy.pojo.ParentTable;
import com.lzx.deploy.pojo.Table;
/**
 * 一个专门用来获得连接的类
 * @author lzx
 *
 */
public class DBHelper {
	private  String driver;
	private  String url;
	private  String username;
	private  String password;
	private  Connection conn;
	
	
	public DBHelper(String driver, String url, String username, String password) {
		super();
		this.driver = driver;
		this.url = url;
		this.username = username;
		this.password = password;
		init();
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	private void init(){
		try {
			Class.forName(driver);
			conn=DriverManager.getConnection(url, username, password);
		}  catch (Exception e) {
			e.printStackTrace();
		}
	}
	public  List<Table> getAllTable(){
		return getAllTable(conn);
	}
	/**
	 * 获得数据库所有表信息
	 * @return
	 * @throws Exception
	 */
	private  List<Table> getAllTable(Connection conn) {
		List<Table> tableList=new ArrayList<Table>();
		try {
			DatabaseMetaData db=conn.getMetaData();
			ResultSet tables=null;
			if(driver.startsWith("oracle")){
				tables=db.getTables(null, username.toUpperCase(), null, new String[]{"TABLE"});
			}else{
				tables=db.getTables(null, null, null, new String[]{"TABLE"});
			}
			while(tables.next()){
				String tableName=tables.getString("TABLE_NAME");
				Table table=new Table(tableName);
				table.setRemark(tables.getString("REMARKS"));
				//对没有表注释的处理
				if(table.getRemark()==null){
					table.setRemark("");
				}
				//id列的处理
				ResultSet idColumns=db.getPrimaryKeys(null, null, tableName);
				while(idColumns.next()){
					table.getIdColumn().setColumnName(idColumns.getString("COLUMN_NAME"));
				}
				idColumns.close();
				ResultSet columns=db.getColumns(null, null, tableName, null);
				List<Column> columnList=table.getColumns();
				while(columns.next()){
					Column column=new Column();
					column.setColumnName(columns.getString("COLUMN_NAME"));
					column.setColumnType(columns.getInt("DATA_TYPE"));
					column.setRemark(columns.getString("REMARKS"));
					if(column.getColumnName().equals(table.getIdColumn().getColumnName())){
						table.getIdColumn().setColumnType(column.getColumnType());
						table.getIdColumn().setRemark(column.getRemark());
						continue;
					}
					columnList.add(column);
				}
				columns.close();
				ResultSet parents=db.getImportedKeys(null, null, tableName);
				List<ParentTable> parentList=table.getParents();
				while (parents.next()) {
					ParentTable parentTable=new ParentTable();
					parentTable.setTableName(parents.getString("PKTABLE_NAME"));
					parentTable.setColumnName(parents.getString("FKCOLUMN_NAME"));
					parentTable.setPkTable(parents.getString("PKTABLE_NAME"));
					parentTable.setFkTable(parents.getString("FKTABLE_NAME"));
					parentTable.setPkColumn(parents.getString("PKCOLUMN_NAME"));
					parentTable.setFkColumn(parents.getString("FKCOLUMN_NAME"));
					parentList.add(parentTable);
				}
				ResultSet childs=db.getExportedKeys(null, null, tableName);
				List<ChildTable> childTableList=table.getChilds();
				while(childs.next()){
					ChildTable childTable=new ChildTable();
					childTable.setTableName(childs.getString("FKTABLE_NAME"));
					childTable.setColumnName(childs.getString("FKCOLUMN_NAME"));
					childTable.setPkTable(childs.getString("PKTABLE_NAME"));
					childTable.setFkTable(childs.getString("FKTABLE_NAME"));
					childTable.setPkColumn(childs.getString("PKCOLUMN_NAME"));
					childTable.setFkColumn(childs.getString("FKCOLUMN_NAME"));
					childTableList.add(childTable);
				}
				tableList.add(table);
				childs.close();
			}
			tables.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tableList;
	}
	public Connection getConn(){
		return this.conn;
	}
	public void closeConn(){
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获得数据表的主键
	 * @param tableName 表名
	 * @return 主键
	 */
	public   String getPrimary(String tableName){
		String primary="";
		ResultSet rs=null;
		try{
			DatabaseMetaData connData=conn.getMetaData();
			rs=connData.getPrimaryKeys(null, null, tableName);
			
			if(rs.next()){
				primary=rs.getString("COLUMN_NAME");
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//		if("".equals(primary)){
//			System.out.println("没有设置主键，请设置主键");
//		}
		return primary;
	}
	
}
