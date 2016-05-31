package com.lzx.deploy.filter.struts2;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lzx.deploy.filter.Filter;
import com.lzx.deploy.filter.FilterChain;
import com.lzx.deploy.util.FileUtil;
import com.lzx.deploy.util.Global;
import com.lzx.deploy.util.StringUtil;

import freemarker.template.TemplateException;

public class DeployAction implements Filter{
	private static Logger logger=LoggerFactory.getLogger(DeployAction.class);
	private String prefix="prefix";
	private String suffix="suffix";
	private String controllerPackage="controllerPackage";
	private String path="path";
	private String controllerPath;
	public void process(FilterChain filterChain) throws Exception {
		logger.debug("begin---开始部署Action");
		prefix=(String) filterChain.get(prefix);
		path=(String) filterChain.get(path);
		controllerPackage=(String) filterChain.get(controllerPackage);
		controllerPath=StringUtil.sourcePackageToPath(controllerPackage);
//		File viewFile=getViewFile(prefix);
		File viewFile=null;
		if(path!=null){
			viewFile=new File(new File(path, "src/main/webapp"), prefix);
		}else{
			viewFile=getViewFile(prefix);
		}
		suffix=(String) filterChain.get(suffix);
		logger.debug("视图所在目录为:{}",viewFile.getAbsolutePath());
		List<File> all=FileUtil.getFileList(viewFile);
		Global.FU.process("BaseAction", filterChain.getRoot(), controllerPath+"base/BaseAction.java");
		logger.debug("成功部署BaseAction");
		for(File f:all){
			if(f.getAbsolutePath().endsWith(suffix)){
				String name=f.getName();
				name=name.substring(0, name.lastIndexOf("."));
				String controllerName=toControllerName(name);
				filterChain.put("pageName", name);
				filterChain.put("controllerName", controllerName);
				Global.FU.process("Action", filterChain.getRoot(), controllerPath+controllerName+"Action.java");
				logger.debug("成功部署{}Action.java",controllerName);
			}
		}
		logger.debug("end---成功部署所有Action");
		
		
		
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
