package com.lzx.deploy.filter.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lzx.deploy.filter.Filter;
import com.lzx.deploy.filter.FilterChain;
import com.lzx.deploy.util.FileUtil;

/**
 * 该类用来判断项目是bs项目还是cs项目
 * 
 * @author lzx
 * 
 */
public class DeploySwitch implements Filter {
	private static Logger logger=LoggerFactory.getLogger(DeploySwitch.class);
	public void process(FilterChain filterChain) {
		logger.debug("begin---开始分析项目");
		String type=FileUtil.getType();
		String projectName=FileUtil.getProjectName();
		filterChain.put("type", type);
		filterChain.put("projectName", projectName);
		logger.debug("当前的项目类型是：-----{}",type);
		logger.debug("当前的项目名字是:------{}",projectName);
		logger.debug("end---分析项目结束");
		
	}

	

}
