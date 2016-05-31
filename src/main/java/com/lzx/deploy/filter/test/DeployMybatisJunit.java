package com.lzx.deploy.filter.test;

import java.text.MessageFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lzx.deploy.filter.Filter;
import com.lzx.deploy.filter.FilterChain;
import com.lzx.deploy.pojo.MyClass;
import com.lzx.deploy.util.Global;
import com.lzx.deploy.util.StringUtil;

public class DeployMybatisJunit implements Filter{
	private static Logger logger=LoggerFactory.getLogger(DeployHibernateJunit.class);
	private String servicePackage="servicePackage";
	private String servicePath;
	private String i="interface";
	private String impl="implements";
	private List<MyClass> myClasses;
	@Override
	public void process(FilterChain filterChain) throws Exception {
		myClasses=filterChain.getClassList();
		servicePackage=(String) filterChain.get(servicePackage);
		servicePath=StringUtil.testPackageToPath(servicePackage);
		i=(String) filterChain.get(i);
		impl=(String) filterChain.get(impl);
		for(MyClass myClass:myClasses){
			if(myClass.getIdField()==null){
				logger.warn("{}类没有设置主键,跳过该类的测试部署",myClass.getClassName());
				continue;
			}
			if(myClass.getParentFields().size()>0){
				logger.warn("{}类有外键关联,跳过该类的测试部署",myClass.getClassName());
				continue;
			}
			filterChain.put("myClass", myClass);
			String serviceI=format(i, myClass.getClassName()+"Service");
			String serviceImpl=format(impl, myClass.getClassName()+"Service");
			filterChain.put("serviceI", serviceI);
			filterChain.put("serviceImpl", serviceImpl);
			Global.FU.process("MybatisJunit", filterChain.getRoot(), servicePath+"Test"+serviceI+".java");
			logger.debug("成功部署测试类:{}","Test"+serviceI);
		}
		
		
	}
	private String format(String templage,String...params){
		logger.debug("开始进行类名的格式化");
		String format= MessageFormat.format(templage, params);
		logger.debug("{}转换为{}",params[0],format);
		return format;
	}
}
