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
	public void process(FilterChain filterChain) throws Exception {
		logger.debug("begin---开始部署springMVC文档");
		springMVCPath=(String) filterChain.get(springMVCPath);
		tempPath=StringUtil.resourcesParse(springMVCPath,filterChain.get("path"));
		if(tempPath==null){
			logger.error("[{}]该路径格式不正确，无法解析",springMVCPath);
			throw new RuntimeException("["+springMVCPath+"]该路径格式不正确，无法解析");
		}
		Global.FU.process("springMVC", filterChain.getRoot(), tempPath);
		logger.debug("end---成功部署springMVC文档");
	}
}
