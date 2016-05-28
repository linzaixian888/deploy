package com.lzx.deploy.filter.struts2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lzx.deploy.filter.Filter;
import com.lzx.deploy.filter.FilterChain;
import com.lzx.deploy.util.Global;
import com.lzx.deploy.util.StringUtil;

public class DeployStruts2Xml implements Filter{
	private static Logger logger=LoggerFactory.getLogger(DeployStruts2Xml.class);
	private String defaultPath="classpath:struts.xml";
	boolean success=true;
	public void process(FilterChain filterChain) {
		logger.debug("begin---开始部署struts2主配置文档");
		success=Global.FU.process("struts2", filterChain.getRoot(), StringUtil.resourcesParse(defaultPath,filterChain.get("path")));
		if(success){
			logger.debug("end---成功部署struts2主配置文档");
			
		}else{
			logger.error("部署部署struts2主配置文档失败");
			throw new RuntimeException("部署部署struts2主配置文档失败");
		}
	}
}
