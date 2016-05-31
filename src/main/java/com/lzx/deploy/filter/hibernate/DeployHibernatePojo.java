package com.lzx.deploy.filter.hibernate;

import java.io.File;
import java.text.MessageFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lzx.deploy.filter.Filter;
import com.lzx.deploy.filter.FilterChain;
import com.lzx.deploy.pojo.MyClass;
import com.lzx.deploy.util.Global;
import com.lzx.deploy.util.StringUtil;

public class DeployHibernatePojo implements Filter{
	private static Logger logger=LoggerFactory.getLogger(DeployHibernatePojo.class);
	private String pojoPackage="pojoPackage";
	private String pojoName="pojoName";
	private String pojoPath;
	private List<MyClass> myClasses;
	public void process(FilterChain filterChain) throws Exception {
		logger.debug("begin---开始部署pojo类");
		myClasses=filterChain.getClassList();
		pojoPackage=(String) filterChain.get(pojoPackage);
		pojoPath=StringUtil.sourcePackageToPath(pojoPackage);
		pojoName=(String) filterChain.get(pojoName);
		new File(pojoPath).mkdirs();
		for(MyClass myClass:myClasses){
			filterChain.put("myClass", myClass);
			String pojoNameFormat= format(pojoName, myClass.getClassName());
			myClass.setClassName(pojoNameFormat);
			Global.FU.process("HibernatePojo", filterChain.getRoot(), pojoPath+myClass.getClassName()+".java");
			logger.debug("已部署POJO类:{}",myClass.getClassName());
		}
		logger.debug("end---成功部署所有pojo类");
	}
	private String format(String templage,String...params){
		logger.debug("开始进行类名的格式化");
		String format= MessageFormat.format(templage, params);
		logger.debug("{}转换为{}",params[0],format);
		return format;
	}
}
