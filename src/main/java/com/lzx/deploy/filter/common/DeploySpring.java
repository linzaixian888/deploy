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
	public void process(FilterChain filterChain) throws Exception {
		logger.debug("begin---开始部署spring主配置文档");
		springPath=(String) filterChain.get(springPath);
		String tempPath=StringUtil.resourcesParse(springPath,filterChain.get("path"));
		if(tempPath==null){
			logger.error("[{}]该路径格式不正确，无法解析",springPath);
			throw new RuntimeException("["+springPath+"]该路径格式不正确，无法解析");
		}
		Global.FU.process("spring", filterChain.getRoot(), tempPath);
		logger.debug("end---成功部署spring主配置文档");
		
	}
}
