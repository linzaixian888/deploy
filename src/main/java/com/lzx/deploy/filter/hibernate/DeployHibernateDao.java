package com.lzx.deploy.filter.hibernate;

import java.io.File;
import java.text.MessageFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lzx.deploy.filter.Filter;
import com.lzx.deploy.filter.FilterChain;
import com.lzx.deploy.pojo.MyClass;
import com.lzx.deploy.util.Global;
import com.lzx.deploy.util.StringUtil;

public class DeployHibernateDao implements Filter{
	private static Logger logger=LoggerFactory.getLogger(DeployHibernateDao.class);
	private String baseDao="BaseDao";
	private String daoPackage="daoPackage";
	private String i="interface";
	private String impl="implements";
	private List<MyClass> myClasses;
	boolean success=true;
	public void process(FilterChain filterChain) {
		logger.debug("begin---开始部署Dao类");
		daoPackage=(String) filterChain.get(daoPackage);
		i=(String) filterChain.get(i);
		impl=(String) filterChain.get(impl);
		myClasses=filterChain.getClassList();
		String daoPath=StringUtil.sourcePackageToPath(daoPackage);
		new File(daoPath).mkdirs();
		
		String baseDaoI=format(i, baseDao);
		String baseDaoImpl=format(impl, baseDao);
		filterChain.put("baseDaoI", baseDaoI);
		filterChain.put("baseDaoImpl", baseDaoImpl);
		success=Global.FU.process("HibernateBaseDaoImplements", filterChain.getRoot(), daoPath+"base/"+baseDaoImpl+".java")
				&&Global.FU.process("HibernateBaseDaoInterface", filterChain.getRoot(), daoPath+"base/"+baseDaoI+".java")
				&&Global.FU.process("QueryCallBack", filterChain.getRoot(), daoPath+"base/QueryCallBack.java")
				&&Global.FU.process("Page", filterChain.getRoot(), daoPath+"base/Page.java");
		if(success){
			logger.debug("成功部署{}类、{}类、QueryCallBack类、Page类",baseDaoI,baseDaoImpl);
		}else{
			logger.error("部署{}类、{}类、QueryCallBack类、Page类失败",baseDaoI,baseDaoImpl);
			throw new RuntimeException("部署"+baseDaoI+"类、"+baseDaoImpl+"类、QueryCallBack类、Page类失败");
		}
		for(MyClass myClass:myClasses){
			filterChain.put("myClass", myClass);
			String daoI=format(i, myClass.getClassName()+"Dao");
			String daoImpl=format(impl, myClass.getClassName()+"Dao");
			filterChain.put("daoI", daoI);
			filterChain.put("daoImpl", daoImpl);
			success=Global.FU.process("HibernateDaoImplements", filterChain.getRoot(),daoPath+daoImpl+".java")
					&&Global.FU.process("HibernateDaoInterface", filterChain.getRoot(),daoPath+daoI+".java");
			if(success){
				logger.debug("成功部署{}Dao类",myClass.getClassName());
			}else{
				logger.error("部署{}Dao类失败",myClass.getClassName());
				throw new RuntimeException("部署"+myClass.getClassName()+"Dao类失败");
			}
		}
		logger.debug("end---成功部署所有Dao类");
		
	}
	private String format(String templage,String...params){
		return MessageFormat.format(templage, params);
	}
}
