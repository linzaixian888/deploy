package com.lzx.deploy.filter.jpa;

import java.io.File;
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
	private String pojoPath;
	private List<MyClass> myClasses;
	@Override
	public void process(FilterChain filterChain) throws Exception {
		logger.debug("begin---开始部署pojo类");
		myClasses=filterChain.getClassList();
		pojoPackage=(String) filterChain.get(pojoPackage);
		pojoPath=StringUtil.sourcePackageToPath(pojoPackage);
		new File(pojoPath).mkdirs();
		boolean success=true;
		for(MyClass myClass:myClasses){
			filterChain.put("myClass", myClass);
			success=Global.FU.process("JpaPojo", filterChain.getRoot(), pojoPath+myClass.getClassName()+".java");
			if(success){
				logger.debug("已部署POJO类:{}",myClass.getClassName());
			}else{
				logger.error("部署POJO类:{}失败",myClass.getClassName());
				throw new RuntimeException("部署POJO类:"+myClass.getClassName()+"失败");
			}
		}
		logger.debug("end---成功部署所有pojo类");
		
	}

}
