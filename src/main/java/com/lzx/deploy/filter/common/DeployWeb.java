package com.lzx.deploy.filter.common;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lzx.deploy.filter.Filter;
import com.lzx.deploy.filter.FilterChain;
import com.lzx.deploy.util.FileUtil;
import com.lzx.deploy.util.Global;

public class DeployWeb implements Filter{
	private static Logger logger=LoggerFactory.getLogger(DeployWeb.class);
	private String webPath="webPath";
	private String path="path";
	public void process(FilterChain filterChain) throws Exception {
		logger.debug("begin---开始部署web.xml");
		webPath=(String) filterChain.get(webPath);
		if(webPath==null){
			path=(String) filterChain.get(path);
			if(path==null){
				webPath=FileUtil.getWebPath();
			}else{
				webPath=new File(path, "src/main/webapp/WEB-INF/web.xml").getAbsolutePath();
			}
		}
		if(webPath!=null){
			logger.debug("web.xml的路径是:{}",webPath);
			Global.FU.process("WebXml", filterChain.getRoot(), webPath);
			logger.debug("end---成功部署web.xml");
		}else{
			logger.error("获取web.xml路径失败，无法部署");
		}
	}
}
