package com.lzx.deploy.filter.mybatis;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lzx.deploy.filter.Filter;
import com.lzx.deploy.filter.FilterChain;
import com.lzx.deploy.pojo.MyClass;
import com.lzx.deploy.util.Global;
import com.lzx.deploy.util.StringUtil;

public class DeployMybatisService implements Filter{
	private static Logger logger=LoggerFactory.getLogger(DeployMybatisService.class);
	private String servicePackage="servicePackage";
	private String servicePath;
	private List<MyClass> myClasses;
	boolean success=false;
	public void process(FilterChain filterChain) {
		logger.debug("begin---开始部署service接口和实现");
		servicePackage=(String) filterChain.get(servicePackage);
		myClasses=filterChain.getClassList();
		servicePath=StringUtil.sourcePackageToPath(servicePackage);
		success=Global.FU.process("MybatisIBaseService", filterChain.getRoot(), servicePath+"IBaseService.java");
		success=success&&Global.FU.process("MybatisBaseService", filterChain.getRoot(), servicePath+"BaseService.java");
		if(success){
			logger.debug("成功部署BaseService接口和实现");
		}else{
			logger.error("部署BaseService接口和实现失败");
			throw new RuntimeException("部署BaseService接口和实现失败");
		}
		for(MyClass myClass:myClasses){
			filterChain.put("myClass", myClass);
			success=Global.FU.process("Service", filterChain.getRoot(), servicePath+myClass.getClassName()+"Service.java");
			success=success&&Global.FU.process("IService", filterChain.getRoot(), servicePath+"I"+myClass.getClassName()+"Service.java");
			if(success){
				logger.debug("部署serivce:{}Service",myClass.getClassName());
			}else{
				logger.error("部署serivce:{}Service失败",myClass.getClassName());
				throw new RuntimeException("部署serivce:"+myClass.getClassName()+"Service失败");
			}
		}
		logger.debug("end---成功部署所有service接口和实现");
	}

}
