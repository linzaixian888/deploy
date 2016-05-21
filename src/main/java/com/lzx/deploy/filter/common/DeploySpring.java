package com.lzx.deploy.filter.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lzx.deploy.filter.Filter;
import com.lzx.deploy.filter.FilterChain;
import com.lzx.deploy.util.Global;
import com.lzx.deploy.util.StringUtil;

public class DeploySpring implements Filter{
	private static Logger logger=LoggerFactory.getLogger(DeploySpring.class);
	private String springPath="springPath";
	private String tempPath;
	boolean success=true;
	public void process(FilterChain filterChain) {
		logger.debug("begin---开始部署spring主配置文档");
		springPath=(String) filterChain.get(springPath);
		tempPath=StringUtil.sourceParse(springPath);
		if(tempPath==null){
			logger.error("[{}]该路径格式不正确，无法解析",springPath);
			throw new RuntimeException("["+springPath+"]该路径格式不正确，无法解析");
		}
		success=Global.FU.process("spring", filterChain.getRoot(), tempPath);
		if(success){
			logger.debug("end---成功部署spring主配置文档");
		}else{
			logger.error("部署部署spring主配置文档失败");
			throw new RuntimeException("部署部署spring主配置文档失败");
		}
		
	}
}
