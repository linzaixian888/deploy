package com.lzx.deploy.filter;

import java.io.File;

import com.lzx.deploy.filter.init.DeployChooseFilter;
import com.lzx.deploy.filter.init.DeployInitDBData;
import com.lzx.deploy.filter.init.DeployReadConfig;
import com.lzx.deploy.filter.init.DeployReadMyXmlConfig;
import com.lzx.deploy.filter.init.DeployReadProjectXML;
import com.lzx.deploy.filter.init.DeploySwitch;
import com.lzx.deploy.filter.jpa.DeployJpaPojo;

public class DeployMain {
	private String configPath=null;
	public DeployMain(String configPath) {
		this.configPath = configPath;
	}
	public DeployMain(File configFile){
		this.configPath=configFile.getAbsolutePath();
	}
	public DeployMain(){
		
	}
	/**
	 * 一些初始化的步骤
	 * @return
	 */
	public FilterChain getInitChain(){
		FilterChain chain=new FilterChain();
		chain.addFilter(new DeployReadProjectXML());
		chain.addFilter(new DeployReadConfig());
		chain.addFilter(new DeployReadMyXmlConfig(configPath));
		chain.addFilter(new DeployInitDBData());
		chain.addFilter(new DeploySwitch());
		return chain;
	}
	private FilterChain getChain(){
		FilterChain chain=getInitChain();
		chain.addFilter(new DeployChooseFilter());
//		chain.addFilter(new DeployJpaPojo());
		return chain;
	}
	public void deploy(){
		deploy(null);
	}
	public void deploy(FilterChain chain){
		try {
			long before=System.currentTimeMillis();
			if(chain==null){
				chain=getChain();
			}
			chain.process(chain);
			long after=System.currentTimeMillis();
			System.out.println("共运行了"+chain.getFilterCount()+"个处理器");
			System.out.println("共耗时"+(after-before)+"豪秒");
			System.out.println("共耗时"+((after-before)/1000.0)+"秒");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		DeployMain d=new DeployMain();
		d.deploy();
	}
}
