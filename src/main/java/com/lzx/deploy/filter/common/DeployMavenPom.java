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
	private boolean success;
	@Override
	public void process(FilterChain filterChain) throws Exception {
		logger.debug("开始部署pom配置文件");
		path=(String) filterChain.get(path);
		if(path==null){
			logger.debug("在本项目中生成代码,跳过该处理器");
		}else{
			File pathPath=new File(path);
			String projectName=pathPath.getName();
			pojoPackage=(String) filterChain.get(pojoPackage);
			filterChain.put("artifactId", projectName);
			filterChain.put("groupId", getBasePackage(pojoPackage));
			success=Global.FU.process("MavenPom", filterChain.getRoot(),new File(path, "pom.xml"));
			if(success){
				logger.debug("成功部署pom配置文件");
			}else{
				logger.error("部署pom配置文件失败");
				new RuntimeException("部署pom配置文件失败");
			}
		}
		
	}
	private String getBasePackage(String packageStr){
		return packageStr.substring(0,packageStr.lastIndexOf("."));
	}

}
