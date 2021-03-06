package com.lzx.deploy.filter.common;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lzx.deploy.filter.Filter;
import com.lzx.deploy.filter.FilterChain;
import com.lzx.deploy.util.Global;

public class DeployMavenPom implements Filter{
	private static Logger logger=LoggerFactory.getLogger(DeployFreemarkerConfig.class);
	private String path="path";
	private String pojoPackage="pojoPackage";
	@Override
	public void process(FilterChain filterChain) throws Exception {
		logger.debug("开始部署pom配置文件");
		path=(String) filterChain.get(path);
		if(path==null){
			logger.debug("在本项目中生成代码,跳过该处理器");
		}else{
			
			String projectName=(String) filterChain.get("projectName");
			if(projectName==null){
				File pathPath=new File(path);
				projectName=pathPath.getName();;
			}
			pojoPackage=(String) filterChain.get(pojoPackage);
			filterChain.put("artifactId", projectName);
			filterChain.put("groupId", getBasePackage(pojoPackage));
			Global.FU.process("MavenPom", filterChain.getRoot(),new File(path, "pom.xml"));
			logger.debug("成功部署pom配置文件");
		}
		
	}
	private String getBasePackage(String packageStr){
		return packageStr.substring(0,packageStr.lastIndexOf("."));
	}

}
