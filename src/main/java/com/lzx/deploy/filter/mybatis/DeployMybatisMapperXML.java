package com.lzx.deploy.filter.mybatis;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lzx.deploy.filter.Filter;
import com.lzx.deploy.filter.FilterChain;
import com.lzx.deploy.pojo.MyClass;
import com.lzx.deploy.pojo.MyField;
import com.lzx.deploy.util.Global;
import com.lzx.deploy.util.StringUtil;

public class DeployMybatisMapperXML implements Filter{
	private static Logger logger=LoggerFactory.getLogger(DeployMybatisMapperXML.class);
	private String daoPackage="daoPackage";
	private String i="interface";
	private List<MyClass> myClasses;
	private String daoXMLPath;
	boolean success=true;
	public void process(FilterChain filterChain) {
		logger.debug("begin---开始部署DaoXML文档");
		myClasses=filterChain.getClassList();
		i=(String) filterChain.get(i);
		daoPackage=(String) filterChain.get(daoPackage);
		daoXMLPath=StringUtil.sourcePackageToPath(daoPackage)+"xml";
		filterChain.put("daoXMLPath", toPath(daoPackage)+"/xml");
		new File(daoXMLPath).mkdirs();
		for(MyClass myClass:myClasses){
			if(myClass.getIdField()==null){
				logger.warn("{}类没有设置主键,跳过该类的DaoXml部署",myClass.getClassName());
				continue;
			}
			List<MyField> noIDFieldList=new ArrayList<MyField>();
			List<MyField> allFieldList=new ArrayList<MyField>();
			allFieldList.add(myClass.getIdField());
			Iterator<MyField> it=myClass.getFields().iterator();
			while(it.hasNext()){
				MyField field=it.next();
				if(myClass.getIdField()==null||!field.getColumnName().equals(myClass.getIdField().getColumnName())){
					noIDFieldList.add(field);
					allFieldList.add(field);
				}
			}
			filterChain.put("insertSql", getInsertSql(allFieldList));
			filterChain.put("insertBatchKeySql",getInsertBatchKeySql(allFieldList));
			filterChain.put("insertBatchValueSql",getInsertBatchValueSql(allFieldList));
			filterChain.put("findByPojoSql", getFindByPojoSql(allFieldList));
			filterChain.put("noIDFieldList", noIDFieldList);
			filterChain.put("updateSql", getUpdateSql(noIDFieldList));
			filterChain.put("myClass", myClass);
			String daoI=format(i, myClass.getClassName()+"Dao");
			filterChain.put("daoI", daoI);
			success=Global.FU.process("MybatisDaoXML", filterChain.getRoot(), daoXMLPath+File.separator+daoI+".xml");
			if(success){
				logger.debug("成功部署{}XML文档",daoI);
			}else{
				logger.error("部署{}XML文档失败",daoI);
				throw new RuntimeException("部署"+daoI+"XML文档失败");
			}
		}
		logger.debug("end---成功部署所有DaoXML文档");
		
	}
	private String getInsertSql(List<MyField> allFieldList){
		StringBuilder sb=new StringBuilder();
		StringBuilder temp=new StringBuilder();
		sb.append("(");
		temp.append("(");
		for(MyField field:allFieldList){
			sb.append(field.getColumnName());
			sb.append(",");
			temp.append("#{");
			temp.append(field.getName());
			temp.append("},");
		}
		sb.setCharAt(sb.length()-1, ')');
		temp.setCharAt(temp.length()-1, ')');
		sb.append("VALUES");
		sb.append(temp);
		return sb.toString();
	}
	private List<String> getUpdateSql(List<MyField> noIDFieldList){
		List<String> tempList=new ArrayList<String>();
		int size=noIDFieldList.size();
		for(int i=0;i<size;i++){
			StringBuilder sb=new StringBuilder();
			MyField field=noIDFieldList.get(i);
			sb.append("<if test=\"");
			sb.append(field.getName());
			sb.append(" != ");
			sb.append(getNull(field.getType()));
			sb.append("\">");
			sb.append(field.getColumnName());
			sb.append("=#{");
			sb.append(field.getName());
			sb.append("}");
			if(i!=size-1){
				sb.append(",");
			}
			sb.append("</if>");
			tempList.add(sb.toString());
		}

		return tempList;
	}
	private List<String> getFindByPojoSql(List<MyField> allFieldList){
		List<String> tempList=new ArrayList<String>();
		int size=allFieldList.size();
		for(int i=0;i<size;i++){
			StringBuilder sb=new StringBuilder();
			MyField field=allFieldList.get(i);
			sb.append("<if test=\"");
			sb.append(field.getName());
			sb.append(" != ");
			sb.append(getNull(field.getType()));
			sb.append("\">");
			if(i!=0){
				sb.append("AND ");
			}
			sb.append(field.getColumnName());
			sb.append("=#{");
			sb.append(field.getName());
			sb.append("}");
			sb.append("</if>");
			tempList.add(sb.toString());
		}
		return tempList;
	}
	private String getInsertBatchKeySql(List<MyField> allFieldList){
		StringBuilder sb=new StringBuilder();
		int size=allFieldList.size();
		sb.append("(");
		for(int i=0;i<size;i++){
			MyField field=allFieldList.get(i);
			sb.append(field.getColumnName());
			if(i!=size-1){
				sb.append(",");
			}
		}
		sb.append(")");
		return sb.toString();
	}
	private String getInsertBatchValueSql(List<MyField> allFieldList){
		StringBuilder sb=new StringBuilder();
		int size=allFieldList.size();
		sb.append("(");
		for(int i=0;i<size;i++){
			MyField field=allFieldList.get(i);
			sb.append("#{");
			sb.append("item.");
			sb.append(field.getColumnName());
			sb.append("}");
			if(i!=size-1){
				sb.append(",");
			}
		}
		sb.append(")");
		return sb.toString();
	}
	private String getNull(String type){
		String temp="null";
//		if("int".equals(type)){
//			temp="0";
//		}
		return temp;
	}
	private String toPath(String myPackage){
		return myPackage.replace(".", "/");
	}
	private String format(String templage,String...params){
		logger.debug("开始进行类名的格式化");
		String format= MessageFormat.format(templage, params);
		logger.debug("{}转换为{}",params[0],format);
		return format;
	}
}
