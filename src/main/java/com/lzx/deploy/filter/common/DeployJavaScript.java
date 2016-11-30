package com.lzx.deploy.filter.common;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lzx.deploy.filter.Filter;
import com.lzx.deploy.filter.FilterChain;
import com.lzx.deploy.util.FileUtil;
import com.lzx.deploy.util.Global;
import com.lzx.deploy.util.StringUtil;

public class DeployJavaScript implements Filter{
	private static Logger logger=LoggerFactory.getLogger(DeployJavaScript.class);
	private String prefix="prefix";
	private String suffix="suffix";
	private String jsPath="jsPath";
	private String path="path";
	public void process(FilterChain chain) throws Exception  {
		logger.debug("begin---开始部署javascript");
		prefix=(String) chain.get(prefix);
		jsPath=(String) chain.get(jsPath);
		path=(String) chain.get(path);
		File viewFile=null;
		if(path!=null){
			viewFile=new File(new File(path, "src/main/webapp"), prefix);
		}else{
			viewFile=getFileInWeb(prefix);
		}
		
		File jsFile=getFileInWeb(jsPath);
		jsFile.mkdirs();
		suffix=(String) chain.get(suffix);
		logger.debug("视图所在目录为:{}",viewFile.getAbsolutePath());
		List<File> all=FileUtil.getFolderAndFileList(viewFile);
		for(File f:all){
			if(f.getAbsolutePath().endsWith(suffix)){
				String name=f.getName();
				name=name.substring(0, name.lastIndexOf("."));
				String jsName=toJsName(name);
				File temp=new File(jsFile, jsName+".js");
				logger.debug(temp.getAbsolutePath());
				if(temp.exists()){
					logger.debug("{}.js已存在，跳过步署",jsName);
				}else{
					String tempPath=FileUtil.getProjectName()+StringUtil.getPath(jsPath)+temp.getName();
					chain.put("jsScript", tempPath);
					Global.FU.process("Js", chain.getRoot(), temp);
					logger.debug("成功部署{}.js",jsName);
				}
			}
		}
		logger.debug("end---成功部署所有js");
		
	}
	/**
	 * 得到视图文件夹
	 * @param childPath
	 * @return
	 */
	private File getFileInWeb(String childPath){
		File f=new File(FileUtil.getWebRoot(), childPath);
		return f;
	}
	/**
	 * 转换为js文件名
	 * @param name
	 * @return
	 */
	private String toJsName(String name){
		return name;
	}
}
