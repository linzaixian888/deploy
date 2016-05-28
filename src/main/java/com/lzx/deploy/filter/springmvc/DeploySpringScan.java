package com.lzx.deploy.filter.springmvc;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lzx.deploy.filter.Filter;
import com.lzx.deploy.filter.FilterChain;
import com.lzx.deploy.util.Global;
import com.lzx.deploy.util.StringUtil;

public class DeploySpringScan implements Filter{
	private static Logger logger=LoggerFactory.getLogger(DeploySpringScan.class);
	private String springScanPath="springScanPath";
	private String tempPath;
	boolean success=true;
	public void process(FilterChain filterChain) {
		logger.debug("begin---开始部署spring控制器扫描文档");
		springScanPath=(String) filterChain.get(springScanPath);
		tempPath=StringUtil.resourcesParse(springScanPath,filterChain.get("path"));
		if(tempPath==null){
			logger.error("[{}]该路径格式不正确，无法解析",springScanPath);
			throw new RuntimeException("["+springScanPath+"]该路径格式不正确，无法解析");
		}
		success=Global.FU.process("springScan", filterChain.getRoot(), tempPath);
		if(success){
			logger.debug("end---成功部署spring控制器扫描文档");
			if(springScanPath.startsWith("/")){
				springScanPath=new File(springScanPath).getName();
			}
			filterChain.put("springScanPath", springScanPath);
		}else{
			logger.error("部署spring控制器扫描文档失败");
		}
	}
}
