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
	public void process(FilterChain filterChain) throws Exception {
		logger.debug("begin---开始部署struts2主配置文档");
		Global.FU.process("struts2", filterChain.getRoot(), StringUtil.resourcesParse(defaultPath,filterChain.get("path")));
		logger.debug("end---成功部署struts2主配置文档");
	}
}
