package com.lzx.deploy.filter.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lzx.deploy.filter.Filter;
import com.lzx.deploy.filter.FilterChain;
import com.lzx.deploy.util.Global;
import com.lzx.deploy.util.StringUtil;

public class DeployFreemarkerConfig implements Filter{
	private static Logger logger=LoggerFactory.getLogger(DeployFreemarkerConfig.class);
	private String defaultPath="classpath:freemarker.properties";
	public void process(FilterChain chain) throws Exception {
		logger.debug("开始部署freemarker配置文件");
		Global.FU.process("Freemarker", chain.getRoot(), StringUtil.resourcesParse(defaultPath,chain.get("path")));
		logger.debug("成功部署freemarker配置文件");
		
		
	}

}
