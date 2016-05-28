package com.lzx.deploy.filter.init;

import java.io.File;

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
	private String path="path";
	private static Logger logger=LoggerFactory.getLogger(DeploySwitch.class);
	public void process(FilterChain filterChain) {
		logger.debug("begin---开始分析项目");
		String projectName=null;
		path=(String) filterChain.get(path);
		String type=(String) filterChain.get("type");
		if(path==null){
			logger.debug("没有设置要生成的目录");
			if(type==null){
				logger.debug("分析本身项目类型");
				type=FileUtil.getType();
			}
			logger.debug("分析本身项目名称");
			projectName=FileUtil.getProjectName();
			filterChain.put("type", type);
			filterChain.put("projectName", projectName);
		
			
		}else{
			File pathPath=new File(path);
			if(type==null){
				logger.debug("没有设置要生成的项目类型 ,默认为cs");
			}
			projectName=pathPath.getName();
			filterChain.put("type", type);
			filterChain.put("projectName", projectName);
		}
		logger.debug("要生成的项目类型是：-----{}",type);
		logger.debug("要生成的项目名字是:------{}",projectName);
		logger.debug("end---分析项目结束");
		
		
		
	}

	

}
