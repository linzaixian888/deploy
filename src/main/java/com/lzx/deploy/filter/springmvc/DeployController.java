package com.lzx.deploy.filter.springmvc;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lzx.deploy.filter.Filter;
import com.lzx.deploy.filter.FilterChain;
import com.lzx.deploy.util.FileUtil;
import com.lzx.deploy.util.Global;
import com.lzx.deploy.util.StringUtil;

public class DeployController implements Filter{
	private static Logger logger=LoggerFactory.getLogger(DeployController.class);
	private String prefix="prefix";
	private String suffix="suffix";
	private String controllerPackage="controllerPackage";
	private String controllerPath;
	boolean success=true;
	public void process(FilterChain chain) {
		logger.debug("begin---开始部署controller");
		prefix=(String) chain.get(prefix);
		controllerPackage=(String) chain.get(controllerPackage);
		controllerPath=StringUtil.sourcePackageToPath(controllerPackage);
		File viewFile=getViewFile(prefix);
		suffix=(String) chain.get(suffix);
		logger.debug("视图所在目录为:{}",viewFile.getAbsolutePath());
		if(!viewFile.exists()){
			logger.debug("视图所在目录不存在，跳过该处理器");
		}else{
			List<File> all=FileUtil.getFolderAndFileList(viewFile);
			for(File f:all){
				if(f.getAbsolutePath().endsWith(suffix)){
					String name=f.getName();
					name=name.substring(0, name.lastIndexOf("."));
					String controllerName=toControllerName(name);
					chain.put("pageName", name);
					chain.put("controllerName", controllerName);
					success=Global.FU.process("Controller", chain.getRoot(), controllerPath+controllerName+"Controller.java");
					if(success){
						logger.debug("成功部署{}Controller.java",controllerName);
					}else{
						logger.error("部署{}Controller.java失败",controllerName);
						throw new RuntimeException("部署"+controllerName+"Controller.java失败");
					}
				}
			}
			logger.debug("end---成功部署所有controller");
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
