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
	private boolean success;
	public void process(FilterChain chain) {
		logger.debug("开始部署freemarker配置文件");
		success=Global.FU.process("freemarker", chain.getRoot(), StringUtil.sourceParse(defaultPath));
		if(success){
			logger.debug("成功部署freemarker配置文件");
		}else{
			logger.error("部署freemarker配置文件失败");
			new RuntimeException("部署freemarker配置文件失败");
		}
		
	}

}
