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
	public void process(FilterChain filterChain) throws Exception {
		servicePackage=(String) filterChain.get(servicePackage);
		myClasses=filterChain.getClassList();
		servicePath=StringUtil.sourcePackageToPath(servicePackage);
		new File(servicePath).mkdirs();
		for(MyClass myClass:myClasses){
			filterChain.put("myClass", myClass);
			Global.FU.process("plugin_service", filterChain.getRoot(), servicePath+myClass.getClassName()+"Service.java");
			logger.debug("成功部署{}Service类",myClass.getClassName());
		}
		logger.debug("end---成功部署所有Service类");
		
	}

}
