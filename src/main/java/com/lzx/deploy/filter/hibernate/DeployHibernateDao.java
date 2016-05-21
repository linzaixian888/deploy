package com.lzx.deploy.filter.hibernate;

import java.io.File;
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
	private String mapperPackage="mapperPackage";
	private List<MyClass> myClasses;
	private String mapperPath;
	boolean success=true;
	public void process(FilterChain filterChain) {
		logger.debug("begin---开始部署Dao类");
		mapperPackage=(String) filterChain.get(mapperPackage);
		myClasses=filterChain.getClassList();
		mapperPath=StringUtil.sourcePackageToPath(mapperPackage);
		new File(mapperPath).mkdirs();
		success=Global.FU.process("HibernateBaseDao", filterChain.getRoot(), mapperPath+"base/BaseDao.java")
				&&Global.FU.process("HibernateIBaseDao", filterChain.getRoot(), mapperPath+"base/IBaseDao.java")
				&&Global.FU.process("QueryCallBack", filterChain.getRoot(), mapperPath+"base/QueryCallBack.java")
				&&Global.FU.process("Page", filterChain.getRoot(), mapperPath+"base/Page.java");
		if(success){
			logger.debug("成功部署IBaseDao类、BaseDao类、QueryCallBack类、Page类");
		}else{
			logger.error("部署IBaseDao类、BaseDao类、QueryCallBack类、Page类失败");
			throw new RuntimeException("部署IBaseDao类、BaseDao类、QueryCallBack类、Page类失败");
		}
		for(MyClass myClass:myClasses){
			filterChain.put("myClass", myClass);
			success=Global.FU.process("HibernateDao", filterChain.getRoot(),mapperPath+myClass.getClassName()+"Dao.java");
			if(success){
				logger.debug("成功部署{}Dao类",myClass.getClassName());
			}else{
				logger.error("部署{}Dao类失败",myClass.getClassName());
				throw new RuntimeException("部署"+myClass.getClassName()+"Dao类失败");
			}
		}
		logger.debug("end---成功部署所有Dao类");
		
	}
}
