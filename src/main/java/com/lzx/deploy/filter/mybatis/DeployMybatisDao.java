package com.lzx.deploy.filter.mybatis;

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

public class DeployMybatisDao implements Filter{
	private static Logger logger=LoggerFactory.getLogger(DeployMybatisDao.class);
	private String daoPackage="daoPackage";
	private String baseDao="BaseDao";
	private String i="interface";
	private String impl="implements";
	private List<MyClass> myClasses;
	private String daoPath;
	public void process(FilterChain filterChain) throws Exception {
		logger.debug("begin---开始部署Dao接口");
		daoPackage=(String) filterChain.get(daoPackage);
		myClasses=filterChain.getClassList();
		daoPath=StringUtil.sourcePackageToPath(daoPackage);
		i=(String) filterChain.get(i);
		new File(daoPath).mkdirs();
		String baseDaoI=format(i, baseDao);
		filterChain.put("baseDaoI", baseDaoI);
		Global.FU.process("MybatisBaseDaoInterface", filterChain.getRoot(), daoPath+baseDaoI+".java");
		logger.debug("成功部署接口:{}",baseDaoI);
		for(MyClass myClass:myClasses){
			if(myClass.getIdField()==null){
				logger.warn("{}类没有设置主键,跳过该类的dao部署",myClass.getClassName());
				continue;
			}
			String daoI=format(i, myClass.getClassName()+"Dao");
			String daoImpl=format(impl, myClass.getClassName()+"Dao");
			filterChain.put("daoI", daoI);
			filterChain.put("daoImpl", daoImpl);
			filterChain.put("myClass", myClass);
			Global.FU.process("MybatisDaoInterface", filterChain.getRoot(),daoPath+daoI+".java");
			logger.debug("成功部署接口:{}",daoI);
		}
		logger.debug("end---成功部署所有Dao接口");
	}
	private String format(String templage,String...params){
		logger.debug("开始进行类名的格式化");
		String format= MessageFormat.format(templage, params);
		logger.debug("{}转换为{}",params[0],format);
		return format;
	}
}
