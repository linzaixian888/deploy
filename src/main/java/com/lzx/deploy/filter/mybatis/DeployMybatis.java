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
	boolean success=true;
	public void process(FilterChain filterChain) {
		logger.debug("begin---开始部署mybatis主配置文档");
		mybatisPath=(String) filterChain.get(mybatisPath);
		tempPath=StringUtil.sourceParse(mybatisPath);
		if(tempPath==null){
			logger.error("[{}]该路径格式不正确，无法解析",mybatisPath);
			throw new RuntimeException("["+mybatisPath+"]该路径格式不正确，无法解析");
		}
		filterChain.put("myClasses", filterChain.getClassList());
		success=true;Global.FU.process("mybatis", filterChain.getRoot(), tempPath);
		if(success){
			logger.debug("end---成功部署mybatis主配置文档");
//			chain.put("mybatisPath", mybatisPath);
		}else{
			logger.error("部署mybatis主配置文档失败");
			throw new RuntimeException("部署mybatis主配置文档失败");
		}
		
	}
	
}
