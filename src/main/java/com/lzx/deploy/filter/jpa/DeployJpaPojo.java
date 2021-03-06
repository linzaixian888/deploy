package com.lzx.deploy.filter.jpa;

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

public class DeployJpaPojo implements Filter{
	private static Logger logger=LoggerFactory.getLogger(DeployJpaPojo.class);
	private String pojoPackage="pojoPackage";
	private String pojoName="pojoName";
	private String pojoPath;
	private List<MyClass> myClasses;
	@Override
	public void process(FilterChain filterChain) throws Exception {
		logger.debug("begin---开始部署pojo类");
		myClasses=filterChain.getClassList();
		pojoPackage=(String) filterChain.get(pojoPackage);
		pojoName=(String) filterChain.get(pojoName);
		pojoPath=StringUtil.sourcePackageToPath(pojoPackage);
		new File(pojoPath).mkdirs();
		Global.FU.process("BasePojo", filterChain.getRoot(), pojoPath+"base/BasePojo.java");
		logger.debug("成功部署{}类","BasePojo");
		for(MyClass myClass:myClasses){
			filterChain.put("myClass", myClass);
			logger.debug("开始进行类名的格式化");
			String pojoNameFormat=MessageFormat.format(pojoName, myClass.getClassName());
			logger.debug("{}转换为{}",myClass.getClassName(),pojoNameFormat);
			myClass.setClassName(pojoNameFormat);
			Global.FU.process("JpaPojo", filterChain.getRoot(), pojoPath+myClass.getClassName()+".java");
			logger.debug("已部署POJO类:{}",myClass.getClassName());
		}
		logger.debug("end---成功部署所有pojo类");
		
	}

}
