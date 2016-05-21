package com.lzx.deploy.filter.plugin;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lzx.deploy.filter.Filter;
import com.lzx.deploy.filter.FilterChain;
import com.lzx.deploy.pojo.MyClass;
import com.lzx.deploy.util.Global;
import com.lzx.deploy.util.StringUtil;

public class DeployServicePlugin implements Filter{
	private static Logger logger=LoggerFactory.getLogger(DeployServicePlugin.class);
	private List<MyClass> myClasses;
	private String servicePackage="servicePackage";
	private String servicePath;
	boolean success=true;
	public void process(FilterChain filterChain) {
		servicePackage=(String) filterChain.get(servicePackage);
		myClasses=filterChain.getClassList();
		servicePath=StringUtil.sourcePackageToPath(servicePackage);
		new File(servicePath).mkdirs();
		for(MyClass myClass:myClasses){
			filterChain.put("myClass", myClass);
			success=Global.FU.process("plugin_service", filterChain.getRoot(), servicePath+myClass.getClassName()+"Service.java");
			if(success){
				logger.debug("成功部署{}Service类",myClass.getClassName());
			}else{
				logger.error("部署{}Service类失败",myClass.getClassName());
				throw new RuntimeException("部署"+myClass.getClassName()+"Service类失败");
			}
		}
		logger.debug("end---成功部署所有Service类");
		
	}

}
