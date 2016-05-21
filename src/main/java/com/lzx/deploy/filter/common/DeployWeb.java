package com.lzx.deploy.filter.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lzx.deploy.filter.Filter;
import com.lzx.deploy.filter.FilterChain;
import com.lzx.deploy.util.FileUtil;
import com.lzx.deploy.util.Global;

public class DeployWeb implements Filter{
	private static Logger logger=LoggerFactory.getLogger(DeployWeb.class);
	private String webPath="webPath";
	private boolean success;
	public void process(FilterChain filterChain) {
		logger.debug("begin---开始部署web.xml");
		webPath=(String) filterChain.get(webPath);
		if(webPath==null){
			webPath=FileUtil.getWebPath();
		}
		if(webPath!=null){
			logger.debug("web.xml的路径是:{}",webPath);
			success=Global.FU.process("webXml.ftl", filterChain.getRoot(), webPath);
			if(success){
				logger.debug("end---成功部署web.xml");
			}else{
				logger.error("end---部署web.xml失败");
			}
		}else{
			logger.error("获取web.xml路径失败，无法部署");
		}
	}
}
