package com.lzx.deploy.filter.init;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.lzx.deploy.filter.Filter;
import com.lzx.deploy.filter.FilterChain;
import com.lzx.deploy.util.FileUtil;
import com.lzx.deploy.util.StringUtil;
/**
 * 一个读取项目配置的类
 * @author lzx
 *
 */
public class DeployReadProjectXML extends DefaultHandler implements Filter{
	private static Logger logger=LoggerFactory.getLogger(DeployReadProjectXML.class);
	private List<String> list=new ArrayList<String>();
	
	@Override
	public void startDocument() throws SAXException {
		logger.debug("begin---开始读取.classpath文件");
	}

	@Override
	public void endDocument() throws SAXException {
		setSrc();
		setTest();
		logger.debug("end---成功读取.classpath文件");
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if("classpathentry".equals(qName)){
			int len=attributes.getLength();
			Map<String, String> map=new HashMap<String, String>();
			for(int i=0;i<len;i++){
				map.put(attributes.getLocalName(i), attributes.getValue(i));
			}
			if("src".equals(map.get("kind"))&&map.get("combineaccessrules")==null){
				list.add(map.get("path"));
			}
		}
	}

	public void process(FilterChain filterChain)throws Exception {
			SAXParserFactory factory=SAXParserFactory.newInstance();
			SAXParser parser=factory.newSAXParser();
			String root=FileUtil.getRootPath();
			logger.debug("项目路径是{}",root.toString());
			File file=new File(root, ".classpath");
			parser.parse(file, this);
	}
	private void setSrc(){
		logger.debug("获取到的源路径为：{}",list);
		int size=list.size();
		if(size==0){
			logger.debug("获取不到源路径，默认源路径为:src");
		}else{
			for(String str:list){
				if(str.endsWith("deploy")){
					logger.debug("设置源路径为:{}",str);
					StringUtil.setSource(str);
					return;
				}
			}
			StringUtil.setSource(list.get(0));
			logger.debug("设置源路径为第一个:{}",list.get(0));
		}
	}
	private void setTest(){
		int size=list.size();
		if(size==0){
			logger.debug("获取不到源路径，默认测试路径为:test");
		}else{
			for(String str:list){
				if(str.endsWith("test")){
					logger.debug("设置测试路径为:{}",str);
					StringUtil.setTest(str);
					return;
				}
			}
			for(String str:list){
				if(str.indexOf("test")!=-1){
					logger.debug("设置测试路径为:{}",str);
					StringUtil.setTest(str);
					return;
				}
			}
			StringUtil.setSource(list.get(0));
			logger.debug("设置测试路径为第一个:{}",list.get(0));
		}
	}
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		super.characters(ch, start, length);
	}

}
