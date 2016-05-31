package com.lzx.deploy.filter.common;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lzx.deploy.filter.Filter;
import com.lzx.deploy.filter.FilterChain;
import com.lzx.deploy.util.FileUtil;
import com.lzx.deploy.util.Global;

public class DeployView implements Filter{
	private static Logger logger=LoggerFactory.getLogger(DeployView.class);
	private String mostSuffix=null;
	private int mostCount=0;
	private Map<String, Integer> cache=new HashMap<String, Integer>();
	private String prefix="prefix";
	private String suffix="suffix";
	private String path="path";
	public void process(FilterChain filterChain) throws Exception {
		logger.debug("begin---开始部署view");
		prefix=(String) filterChain.get(prefix);
		suffix=(String) filterChain.get(suffix);
		if(suffix!=null&&!suffix.startsWith(".")){
			suffix="."+suffix;
		}
		File viewFile=getViewFile(prefix);
		logger.debug("视图所在目录为:{}",viewFile.getAbsolutePath());
		List<File> all=FileUtil.getFileList(viewFile);
		check(all);
		if(mostCount==0){
			logger.debug("视图目录里没有文件");
			if(path!=null){
				logger.debug("生成demo视图");
				path=(String) filterChain.get(path);
				Global.FU.process("View", filterChain.getRoot(), new File(new File(new File(path, "src/main/webapp"), prefix), "demo"+suffix));
			}
		}else{
			logger.debug("后缀名最多的为:{}",mostSuffix);
			logger.debug("数量为：{}",mostCount);
			for(File file:all){
				String allPath=file.getAbsolutePath();
				if(allPath.endsWith(mostSuffix)){
					allPath=allPath.replace(mostSuffix, suffix);
					boolean flag=file.renameTo(new File(allPath));
					if(flag){
						logger.debug("成功部署:{}",allPath);
					}else{
						logger.error("部署:{}失败",allPath);
					}
					
				}
			}
			
		}
		logger.debug("end---成功部署view");
		
		
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
	private void check(List<File> all){
		for(File file:all){
			String name=file.getName();
			String nameSuffix=getSuffix(name);
			if(nameSuffix==null){
				continue;
			}
			setCache(nameSuffix);
		}
	}
	private String getSuffix(String name){
		int index=name.lastIndexOf(".");
		if(index!=-1){
			return name.substring(index);
		}else{
			return null;
		}
	}
	private void setCache(String key){
		Integer temp=cache.get(key);
		if(temp==null){
			temp=0;
		}
		temp++;
		cache.put(key, temp);
		if(temp>=mostCount){
			mostSuffix=key;
			mostCount=temp;
		}
	}
}
