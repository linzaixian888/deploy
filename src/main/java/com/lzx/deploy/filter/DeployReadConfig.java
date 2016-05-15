package com.lzx.deploy.filter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 读取默认配置文件的类
 * @author lzx
 *
 */
public class DeployReadConfig implements Filter{
	private static Logger logger=LoggerFactory.getLogger(DeployReadConfig.class);
	InputStream is=null;
	private String defaultPath="config.properties";
	
	public DeployReadConfig() {
		is=ClassLoader.getSystemResourceAsStream(defaultPath);
	}

	public void process(FilterChain filterChain) throws IOException {
			logger.debug("begin---开始读取默认配置文件");
			if(is==null){
				logger.error("配置文件不存在");
				throw new RuntimeException("配置文件不存在");
			}
			Properties p=new Properties();
			p.load(is);
			Set keys=p.keySet();
			for(Object obj:keys){
				String key=obj.toString();
				String value=p.getProperty(key);
				filterChain.put(key, value);
				logger.debug("默认配置属性：{}={}",key,value);
			}
			is.close();
			logger.debug("配置数据：{}",filterChain.getRoot());
			logger.debug("end---读取配置文件结束");
		
	}
	public static void main(String[] args) throws Exception {
		FilterChain chain=new FilterChain();
		chain.addFilter(new DeployReadConfig());
		chain.process(chain);
	}
}
