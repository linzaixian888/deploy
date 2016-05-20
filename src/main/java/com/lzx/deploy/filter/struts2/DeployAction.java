package com.lzx.deploy.filter.struts2;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lzx.deploy.filter.Filter;
import com.lzx.deploy.filter.FilterChain;
import com.lzx.deploy.util.FileUtil;
import com.lzx.deploy.util.Global;
import com.lzx.deploy.util.StringUtil;

public class DeployAction implements Filter{
	private static Logger logger=LoggerFactory.getLogger(DeployAction.class);
	private String prefix="prefix";
	private String suffix="suffix";
	private String controllerPackage="controllerPackage";
	private String controllerPath;
	boolean success=true;
	public void process(FilterChain filterChain) {
		logger.debug("begin---开始部署Action");
		prefix=(String) filterChain.get(prefix);
		controllerPackage=(String) filterChain.get(controllerPackage);
		controllerPath=StringUtil.sourcePackageToPath(controllerPackage);
		File viewFile=getViewFile(prefix);
		suffix=(String) filterChain.get(suffix);
		logger.debug("视图所在目录为:{}",viewFile.getAbsolutePath());
		if(!viewFile.exists()){
			logger.debug("视图所在目录不存在，跳过该处理器");
		}else{
			List<File> all=FileUtil.getFolderAndFileList(viewFile);
			success=Global.FU.process("BaseAction", filterChain.getRoot(), controllerPath+"base/BaseAction.java");
			if(success){
				logger.debug("成功部署BaseAction");
			}else{
				logger.error("部署BaseAction失败");
				throw new RuntimeException("部署BaseAction失败");
			}
			for(File f:all){
				if(f.getAbsolutePath().endsWith(suffix)){
					String name=f.getName();
					name=name.substring(0, name.lastIndexOf("."));
					String controllerName=toControllerName(name);
					filterChain.put("pageName", name);
					filterChain.put("controllerName", controllerName);
					success=Global.FU.process("Action", filterChain.getRoot(), controllerPath+controllerName+"Action.java");
					if(success){
						logger.debug("成功部署{}Action.java",controllerName);
					}else{
						logger.error("部署{}Action.java失败",controllerName);
						throw new RuntimeException("部署"+controllerName+"Action.java失败");
					}
				}
			}
			logger.debug("end---成功部署所有Action");
		}
		
		
		
	}
	/**
	 * 转换为controller类名
	 * @param str
	 * @return
	 */
	private String toControllerName(String str){
		//return StringUtil.firstUp(str);
		return StringUtil.trimUnderLine(str);
	}
	/**
	 * 得到视图文件夹
	 * @param childPath
	 * @return
	 */
	private File getViewFile(String childPath){
		File f=new File(FileUtil.getWebRoot(), childPath);
		return f;
	}
	
	
}
