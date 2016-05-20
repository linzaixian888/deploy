package com.lzx.deploy.filter.springmvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lzx.deploy.filter.Filter;
import com.lzx.deploy.filter.FilterChain;
import com.lzx.deploy.util.Global;
import com.lzx.deploy.util.StringUtil;

public class DeploySpringMVC implements Filter{
	private static Logger logger=LoggerFactory.getLogger(DeploySpringMVC.class);
	private String springMVCPath="springMVCPath";
	private String tempPath;
	boolean success=true;
	public void process(FilterChain filterChain) {
		logger.debug("begin---开始部署springMVC文档");
		springMVCPath=(String) filterChain.get(springMVCPath);
		tempPath=StringUtil.sourceParse(springMVCPath);
		if(tempPath==null){
			logger.error("[{}]该路径格式不正确，无法解析",springMVCPath);
			throw new RuntimeException("["+springMVCPath+"]该路径格式不正确，无法解析");
		}
		success=Global.FU.process("springMVC", filterChain.getRoot(), tempPath);
		if(success){
			logger.debug("end---成功部署springMVC文档");
		}else{
			logger.error("部署springMVC文档失败");
			throw new RuntimeException("部署springMVC文档失败");
		}
	}
}
