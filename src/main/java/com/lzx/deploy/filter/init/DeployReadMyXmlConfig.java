package com.lzx.deploy.filter.init;

import java.io.InputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lzx.deploy.filter.Filter;
import com.lzx.deploy.filter.FilterChain;

/**
 * 一个读取自定义xml配置文件的类
 * @author lzx
 *
 */
public class DeployReadMyXmlConfig implements Filter {
	private static Logger logger=LoggerFactory.getLogger(DeployReadMyXmlConfig.class);
	InputStream is = null;
	private String defaultPath="config.xml";
	public DeployReadMyXmlConfig(String configPath) {
		if(configPath==null){
			configPath=defaultPath;
		}
		is = ClassLoader.getSystemResourceAsStream(configPath);
	}
	

	public void process(FilterChain filterChain) {
		try {
			logger.debug("begin---开始读取自定义xml配置文件");
			if (is == null) {
				logger.error("自定义配置文件不存在:{}",defaultPath);
				throw new RuntimeException("自定义配置文件不存在");
			}
			SAXReader reader=new SAXReader();
			Document doc=reader.read(is);
			Element root=doc.getRootElement();
			setFramework(root.attributeValue("framework"), filterChain);
			List<Element> list=root.elements();
			for(Element e:list){
				if("sql".equals(e.getName())){
					handleSqlElement(e, filterChain);
				}else{
					setValue(e, filterChain);
				}
			}
			is.close();
			logger.debug("配置数据：{}",filterChain.getRoot());
			logger.debug("end---读取自定义xml配置文件结束");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void setFramework(String frameworkStr,FilterChain chain){
		if(frameworkStr!=null){
			String[] frameworks=frameworkStr.split(",");
			for(String framework:frameworks){
				chain.put(framework, true);
			}
			chain.put("frameworks", frameworks);
		}
	}
	private void setValue(Element element,FilterChain chain){
		if(element!=null){
			String value=element.getTextTrim();
			if(!"".equals(value)){
				chain.put(element.getName(), value);
			}
			List<Element> list=element.elements();
			for(Element e:list){
				setValue(e, chain);
			}
		}
	}
	private void handleSqlElement(Element element,FilterChain chain){
		String sqlType=element.attributeValue("sqlType");
		if(sqlType!=null){
			Element sql=element.element(sqlType);
			chain.put("sqlType", sqlType);
			setValue(sql, chain);
		}
	}
	
}
