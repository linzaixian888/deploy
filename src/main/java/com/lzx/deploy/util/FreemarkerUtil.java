package com.lzx.deploy.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;



public class FreemarkerUtil {
	private List<TemplateLoader> list=new ArrayList<TemplateLoader>();
	private Configuration cfg;
	private boolean isSetLoader=false;
	private String suffix=null;
	private boolean isCloseStream=true;
	
	public FreemarkerUtil(){
		init();
	}
	/**
	 * 返回当前加载器个数
	 * @return
	 */
	public int loaderSize(){
		return list.size();
	}
	/**
	 * 替换指定位置的模版加载器
	 * @param index
	 * @param templateLoader
	 * @return
	 */
	public boolean setLoader(int index,TemplateLoader templateLoader){
		if(index<list.size()){
			list.set(index, templateLoader);
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 设置是否自动关闭流
	 * @param flag
	 */
	public void setIsCloseStream(boolean flag){
		this.isCloseStream=flag;
	}
	/**
	 * 设置模版后缀名
	 * @param suffix
	 */
	public void setSuffix(String suffix){
		this.suffix=suffix;
	}
	/**
	 * 消除当前所有模版加载器
	 */
	public void clearTemplateLoader(){
		list.clear();
		isSetLoader=false;
	}
	
	/**
	 * 返回当前所有模版加载器集合
	 * @return
	 */
	public List<TemplateLoader> getLoaderList(){
		return list;
	}
	/**
	 * 返回指定位置的模版加载器
	 * @param index
	 * @return
	 */
	public TemplateLoader getLoader(int index){
		if(index<list.size()){
			return list.get(index);
		}else{
			return null;
		}
	}
	/**
	 * 某些初始化
	 */
	private void init(){
		cfg=new Configuration();
		cfg.setObjectWrapper(new DefaultObjectWrapper());
	}
	/**
	 * 加入一个文件加载器
	 * @param folderPath
	 * @return
	 */
	public int addFolderLoader(String folderPath){
		return addFolderLoader(new File(folderPath));
	}
	/**
	 * 加入一个文件夹加载器
	 * @param folder
	 * @return
	 */
	public int addFolderLoader(File folder){
		int index=-1;
			try {
				FileTemplateLoader f=new FileTemplateLoader(folder);
				list.add(f);
				index=list.size()-1;
				isSetLoader=false;
			} catch (IOException e) {
				e.printStackTrace();
		}
		return index;
	}
	/**
	 * 加入一个类位置加载器
	 * @param classType
	 * @param prefix
	 * @return
	 */
	public int addClassLoader(Class<?> classType,String prefix){
		int index=-1;
		try {
			ClassTemplateLoader c=new ClassTemplateLoader(classType, prefix);
			list.add(c);
			index=list.size()-1;
			isSetLoader=false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return index;
	}
	/**
	 * 获得一个多位置加载器
	 * @return
	 */
	public MultiTemplateLoader getMultiTemplateLoader(){
		TemplateLoader[] loaders=new TemplateLoader[list.size()];
		for(int i=0;i<list.size();i++){
			loaders[i]=list.get(i);
		}
		MultiTemplateLoader m=new MultiTemplateLoader(loaders);
		return m;
	}
	/**
	 * 返回某个名字的模版
	 * @param name
	 * @return
	 */
	public Template getTemplate(String name){
		Template t=null;
		try {
			if(!isSetLoader){
				cfg.setTemplateLoader(getMultiTemplateLoader());
				isSetLoader=true;
			}
			if(suffix!=null&&name.indexOf(".")==-1){
				name=name+"."+suffix;
			}
			t= cfg.getTemplate(name);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return t;
	}
	/**
	 * 返回配置对象
	 * @return
	 */
	public Configuration getConfiguraton(){
		return cfg;
	}
	/**
	 * 刷新模版加载器
	 */
	public void flushLoader(){
		isSetLoader=true;
		cfg.setTemplateLoader(getMultiTemplateLoader());
	}
	/**
	 * 模版处理数据
	 * @param t
	 * @param rootMap
	 * @param out
	 * @return
	 */
	public boolean process(Template t,Object rootMap,Writer out){
		boolean flag=false;
		try {
			t.process(rootMap, out);
			out.flush();
			if(isCloseStream){
				out.close();
			}
			flag=true;
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return flag;
		
	}
	
	/**
	 * 模版处理数据
	 * @param templateName
	 * @param rootMap
	 * @param out
	 * @return
	 */
	public boolean process(String templateName,Object rootMap,Writer out){
		Template t=getTemplate(templateName);
		return process(t, rootMap, out);
	}
	/**
	 * 模版处理数据
	 * @param t
	 * @param rootMap
	 * @param os
	 * @return
	 */
	public boolean process(Template t,Object rootMap,OutputStream os){
		Writer out=new OutputStreamWriter(os);
		return process(t,rootMap,out);
		
	}
	/**
	 * 模版处理数据
	 * @param templateName
	 * @param rootMap
	 * @param os
	 * @return
	 */
	public boolean process(String templateName,Object rootMap,OutputStream os){
		Writer out=new OutputStreamWriter(os);
		return process(templateName, rootMap, out);
	}
	/**
	 * 模版处理数据
	 * @param templateName
	 * @param rootMap
	 * @param filePath
	 * @return
	 */
	public boolean process(String templateName,Object rootMap,String filePath){
		return process(templateName, rootMap, new File(filePath));
	}
	/**
	 * 模版处理数据
	 * @param templateName
	 * @param rootMap
	 * @param file
	 * @return
	 */
	public boolean process(String templateName,Object rootMap,File file){
		boolean flag=false;
		try {
			File folder=file.getParentFile();
			if(!file.exists()){
				folder.mkdirs();
			}
			Writer out=new FileWriter(file);
			flag= process(templateName, rootMap, out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	
	
}
