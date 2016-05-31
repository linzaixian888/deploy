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

public class DeployDaoPlugin implements Filter{
	private static Logger logger=LoggerFactory.getLogger(DeployDaoPlugin.class);
	private List<MyClass> myClasses;
	private String mapperPackage="mapperPackage";
	private String mapperPath;
	public void process(FilterChain filterChain) throws Exception {
		mapperPackage=(String) filterChain.get(mapperPackage);
		myClasses=filterChain.getClassList();
		mapperPath=StringUtil.sourcePackageToPath(mapperPackage);
		new File(mapperPath).mkdirs();
		for(MyClass myClass:myClasses){
			filterChain.put("myClass", myClass);
			Global.FU.process("plugin_dao", filterChain.getRoot(), mapperPath+myClass.getClassName()+"Dao.java");
			logger.debug("成功部署{}Dao类",myClass.getClassName());
		}
		logger.debug("end---成功部署所有Dao类");
		
	}

}
