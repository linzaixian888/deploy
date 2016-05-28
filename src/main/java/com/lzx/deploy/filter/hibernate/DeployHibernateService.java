package com.lzx.deploy.filter.hibernate;

import java.text.MessageFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lzx.deploy.filter.Filter;
import com.lzx.deploy.filter.FilterChain;
import com.lzx.deploy.pojo.MyClass;
import com.lzx.deploy.util.Global;
import com.lzx.deploy.util.StringUtil;

public class DeployHibernateService implements Filter{
	private static Logger logger=LoggerFactory.getLogger(DeployHibernateService.class);
	private String servicePackage="servicePackage";
	private String baseDao="BaseDao";
	private String baseService="BaseService";
	private String i="interface";
	private String impl="implements";
	private String servicePath;
	private List<MyClass> myClasses;
	boolean success=true;
	public void process(FilterChain filterChain) {
		logger.debug("begin---开始部署service接口和实现");
		servicePackage=(String) filterChain.get(servicePackage);
		i=(String) filterChain.get(i);
		impl=(String) filterChain.get(impl);
		myClasses=filterChain.getClassList();
		servicePath=StringUtil.sourcePackageToPath(servicePackage);
		String baseDaoI=format(i, baseDao);
		String baseDaoImpl=format(impl, baseDao);
		filterChain.put("baseDaoI", baseDaoI);
		filterChain.put("baseDaoImpl", baseDaoImpl);
		String baseServiceI=format(i, baseService);
		String baseServiceImpl=format(impl, baseService);
		filterChain.put("baseServiceI", baseServiceI);
		filterChain.put("baseServiceImpl", baseServiceImpl);
		success=Global.FU.process("HibernateBaseServiceInterface", filterChain.getRoot(), servicePath+"base/"+baseServiceI+".java")
				&&Global.FU.process("HibernateBaseServiceImplements", filterChain.getRoot(), servicePath+"base/"+baseServiceImpl+".java");
		if(success){
			logger.debug("成功部署BaseService接口:{}和实现:{}",baseServiceI,baseServiceImpl);
		}else{
			logger.error("部署BaseService接口:{}和实现:{}失败",baseServiceI,baseServiceImpl);
			throw new RuntimeException("部署BaseService接口:"+baseServiceI+"和实现:"+baseServiceImpl+"失败");
		}
		for(MyClass myClass:myClasses){
			if(myClass.getIdField()==null){
				logger.warn("{}类没有设置主键,跳过该类的service部署",myClass.getClassName());
				continue;
			}
			filterChain.put("myClass", myClass);
			String daoI=format(i, myClass.getClassName()+"Dao");
			String daoImpl=format(impl, myClass.getClassName()+"Dao");
			filterChain.put("daoI", daoI);
			filterChain.put("daoImpl", daoImpl);
			String serviceI=format(i, myClass.getClassName()+"Service");
			String serviceImpl=format(impl, myClass.getClassName()+"Service");
			filterChain.put("serviceI", serviceI);
			filterChain.put("serviceImpl", serviceImpl);
			success=Global.FU.process("HibernateServiceImplements", filterChain.getRoot(), servicePath+serviceImpl+".java")
				  &&Global.FU.process("HibernateServiceInterface", filterChain.getRoot(), servicePath+serviceI+".java");
			if(success){
				logger.debug("成功部署serivce类的接口:{}和实现:{}",serviceI,serviceImpl);
			}else{
				logger.error("部署serivce类的接口:{}和实现:{}失败",serviceI,serviceImpl);
				throw new RuntimeException("部署serivce类的接口:"+serviceI+"和实现:"+serviceImpl+"失败");
			}
		}
		logger.debug("end---成功部署所有service接口和实现");
	}
	private String format(String templage,String...params){
		logger.debug("开始进行类名的格式化");
		String format= MessageFormat.format(templage, params);
		logger.debug("{}转换为{}",params[0],format);
		return format;
	}
}
