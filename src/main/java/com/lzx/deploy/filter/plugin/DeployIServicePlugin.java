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

public class DeployIServicePlugin implements Filter{
	private static Logger logger=LoggerFactory.getLogger(DeployIServicePlugin.class);
	private List<MyClass> myClasses;
	private String servicePackage="servicePackage";
	private String servicePath;
	boolean success=true;
	public void process(FilterChain chain) {
		servicePackage=(String) chain.get(servicePackage);
		myClasses=chain.getClassList();
		servicePath=StringUtil.sourcePackageToPath(servicePackage);
		new File(servicePath).mkdirs();
		for(MyClass myClass:myClasses){
			chain.put("myClass", myClass);
			success=Global.FU.process("plugin_iservice", chain.getRoot(), servicePath+"I"+myClass.getClassName()+"Service.java");
			if(success){
				logger.debug("成功部署{}IService类",myClass.getClassName());
			}else{
				logger.error("部署I{}Service类失败",myClass.getClassName());
				throw new RuntimeException("部署I"+myClass.getClassName()+"Service类失败");
			}
		}
		logger.debug("end---成功部署所有IService类");
		
	}

}
