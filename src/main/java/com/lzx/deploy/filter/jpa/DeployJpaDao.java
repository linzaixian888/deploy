package com.lzx.deploy.filter.jpa;

import java.io.File;
import java.text.MessageFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lzx.deploy.filter.Filter;
import com.lzx.deploy.filter.FilterChain;
import com.lzx.deploy.filter.hibernate.DeployHibernateDao;
import com.lzx.deploy.pojo.MyClass;
import com.lzx.deploy.util.Global;
import com.lzx.deploy.util.StringUtil;

public class DeployJpaDao implements Filter{
	private static Logger logger=LoggerFactory.getLogger(DeployJpaDao.class);
	private String baseDao="BaseDao";
	private String daoPackage="daoPackage";
	private String i="interface";
	private String impl="implements";
	private List<MyClass> myClasses;
	public void process(FilterChain filterChain) throws Exception {
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
		Global.FU.process("JpaBaseDaoImplements", filterChain.getRoot(), daoPath+"base/"+baseDaoImpl+".java");
		Global.FU.process("JpaBaseDaoInterface", filterChain.getRoot(), daoPath+"base/"+baseDaoI+".java");
		logger.debug("成功部署{}类、{}类",baseDaoI,baseDaoImpl);
		for(MyClass myClass:myClasses){
			if(myClass.getIdField()==null){
				logger.warn("{}类没有设置主键,跳过该类的dao部署",myClass.getClassName());
				continue;
			}
			filterChain.put("myClass", myClass);
			String daoI=format(i, myClass.getClassName()+"Dao");
			String daoImpl=format(impl, myClass.getClassName()+"Dao");
			filterChain.put("daoI", daoI);
			filterChain.put("daoImpl", daoImpl);
			Global.FU.process("JpaDaoImplements", filterChain.getRoot(),daoPath+daoImpl+".java");
			Global.FU.process("JpaDaoInterface", filterChain.getRoot(),daoPath+daoI+".java");
			Global.FU.process("JpaDaoCustom", filterChain.getRoot(),daoPath+daoI+"Custom.java");
			logger.debug("成功部署Dao类的接口:{}和实现:{}",daoI,daoImpl);
		}
		logger.debug("end---成功部署所有Dao类");
		
	}
	private String format(String templage,String...params){
		logger.debug("开始进行类名的格式化");
		String format= MessageFormat.format(templage, params);
		logger.debug("{}转换为{}",params[0],format);
		return format;
	}

}
