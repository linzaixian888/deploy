package com.lzx.deploy.filter.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lzx.deploy.filter.Filter;
import com.lzx.deploy.filter.FilterChain;
import com.lzx.deploy.filter.common.DeployFreemarkerConfig;
import com.lzx.deploy.filter.common.DeployJavaScript;
import com.lzx.deploy.filter.common.DeployMavenPom;
import com.lzx.deploy.filter.common.DeploySpring;
import com.lzx.deploy.filter.common.DeployView;
import com.lzx.deploy.filter.common.DeployWeb;
import com.lzx.deploy.filter.hibernate.DeployHibernateDao;
import com.lzx.deploy.filter.hibernate.DeployHibernatePojo;
import com.lzx.deploy.filter.hibernate.DeployHibernateService;
import com.lzx.deploy.filter.jpa.DeployJpaDao;
import com.lzx.deploy.filter.jpa.DeployJpaPojo;
import com.lzx.deploy.filter.jpa.DeployJpaService;
import com.lzx.deploy.filter.mybatis.DeployMybatis;
import com.lzx.deploy.filter.mybatis.DeployMybatisDao;
import com.lzx.deploy.filter.mybatis.DeployMybatisMapperXML;
import com.lzx.deploy.filter.mybatis.DeployMybatisPoJo;
import com.lzx.deploy.filter.mybatis.DeployMybatisService;
import com.lzx.deploy.filter.springmvc.DeployController;
import com.lzx.deploy.filter.springmvc.DeploySpringMVC;
import com.lzx.deploy.filter.springmvc.DeploySpringMVCInterceptor;
import com.lzx.deploy.filter.springmvc.DeploySpringScan;
import com.lzx.deploy.filter.struts2.DeployAction;
import com.lzx.deploy.filter.struts2.DeployStrtus2Interceptor;
import com.lzx.deploy.filter.struts2.DeployStruts2Xml;
import com.lzx.deploy.filter.test.DeployHibernateJunit;
import com.lzx.deploy.filter.test.DeployMybatisJunit;



/**
 * 一个过滤器的后续选择类
 * @author lzx
 *
 */
public class DeployChooseFilter implements Filter{
	private static Logger logger=LoggerFactory.getLogger(DeployChooseFilter.class);
	private String[] frameworks;
	private String type;
	public void process(FilterChain filterChain) {
		frameworks=(String[]) filterChain.get("frameworks");
		type=(String) filterChain.get("type");
		if(isExist(frameworks, "mybatis")){
			logger.debug("存在{}框架","mybatis");
			addMybaitsFilter(filterChain);
		}else if(isExist(frameworks, "hibernate")){
			logger.debug("存在{}框架","hibernate");
			addHibernateFilter(filterChain);
		}else if(isExist(frameworks, "jpa")){
			logger.debug("存在{}框架","jpa");
			addJpaFilter(filterChain);
		}
		if("bs".equals(type)){
			if(isExist(frameworks, "springmvc")){
				logger.debug("存在{}框架","springmvc");
				addSpringMVC(filterChain);
			}
			if(isExist(frameworks, "struts2")){
				logger.debug("存在{}框架","struts2");
				addStruts2(filterChain);
			}
			
		}
		
		
	}
	private void addMybaitsFilter(FilterChain chain){
		chain.addFilter(new DeployMybatisPoJo());
		chain.addFilter(new DeployMybatisDao());
		chain.addFilter(new DeployMybatisMapperXML());
		chain.addFilter(new DeployMybatis());
		chain.addFilter(new DeployMybatisService());
		chain.addFilter(new DeploySpring());
		chain.addFilter(new DeployMybatisJunit());
		chain.addFilter(new DeployMavenPom());
	}
	private void addHibernateFilter(FilterChain chain){
		chain.addFilter(new DeployHibernatePojo());
		chain.addFilter(new DeployHibernateDao());
		chain.addFilter(new DeployHibernateService());
		chain.addFilter(new DeploySpring());
		chain.addFilter(new DeployHibernateJunit());
		chain.addFilter(new DeployMavenPom());
	}
	
	private void addJpaFilter(FilterChain chain){
		chain.addFilter(new DeployJpaPojo());
		chain.addFilter(new DeployJpaDao());
		chain.addFilter(new DeployJpaService());
		chain.addFilter(new DeploySpring());
		chain.addFilter(new DeployMavenPom());
	}
	private void addSpringMVC(FilterChain chain){
		chain.addFilter(new DeployFreemarkerConfig());
		chain.addFilter(new DeploySpringScan());
		chain.addFilter(new DeploySpringMVCInterceptor());
		chain.addFilter(new DeploySpringMVC());
		chain.addFilter(new DeployWeb());
		chain.addFilter(new DeployView());
		chain.addFilter(new DeployController());
		chain.addFilter(new DeployJavaScript());
	}
	private void addStruts2(FilterChain chain){
		chain.addFilter(new DeployWeb());
		chain.addFilter(new DeployFreemarkerConfig());
		chain.addFilter(new DeployStruts2Xml());
		chain.addFilter(new DeployStrtus2Interceptor());
		chain.addFilter(new DeployView());
		chain.addFilter(new DeployAction());
		chain.addFilter(new DeployJavaScript());
	}
	
	private boolean isExist(String[] array,String str){
		for(String a:array){
			if(a.equalsIgnoreCase(str)){
				return true; 
			}
		}
		return false;
	}
	

}
