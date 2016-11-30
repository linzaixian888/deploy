package com.lzx.deploy.filter.mybatis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lzx.deploy.filter.Filter;
import com.lzx.deploy.filter.FilterChain;
import com.lzx.deploy.util.Global;
import com.lzx.deploy.util.StringUtil;

public class DeployMybatis implements Filter{
	private static Logger logger=LoggerFactory.getLogger(DeployMybatis.class);
	private String mybatisPath="mybatisPath";
	private String tempPath;
	public void process(FilterChain filterChain) throws Exception {
		logger.debug("begin---开始部署mybatis主配置文档");
		mybatisPath=(String) filterChain.get(mybatisPath);
		tempPath=StringUtil.resourcesParse(mybatisPath,filterChain.get("path"));
		if(tempPath==null){
			logger.error("[{}]该路径格式不正确，无法解析",mybatisPath);
			throw new RuntimeException("["+mybatisPath+"]该路径格式不正确，无法解析");
		}
		filterChain.put("myClasses", filterChain.getClassList());
		Global.FU.process("Mybatis", filterChain.getRoot(), tempPath);
		logger.debug("end---成功部署mybatis主配置文档");
		
	}
	
}
