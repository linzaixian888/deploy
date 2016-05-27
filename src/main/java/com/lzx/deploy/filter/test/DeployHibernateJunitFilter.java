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

public class DeployHibernateJunitFilter implements Filter{
	private static Logger logger=LoggerFactory.getLogger(DeployHibernateJunitFilter.class);
	private String servicePackage="servicePackage";
	private String servicePath;
	private String i="interface";
	private String impl="implements";
	private List<MyClass> myClasses;
	boolean success=true;
	@Override
	public void process(FilterChain filterChain) {
		myClasses=filterChain.getClassList();
		servicePackage=(String) filterChain.get(servicePackage);
		servicePath=StringUtil.testPackageToPath(servicePackage);
		i=(String) filterChain.get(i);
		impl=(String) filterChain.get(impl);
		for(MyClass myClass:myClasses){
			if(myClass.getParentFields().size()>0){
				continue;
			}
			filterChain.put("myClass", myClass);
			String serviceI=format(i, myClass.getClassName()+"Service");
			String serviceImpl=format(impl, myClass.getClassName()+"Service");
			filterChain.put("serviceI", serviceI);
			filterChain.put("serviceImpl", serviceImpl);
			success=Global.FU.process("HibernateJunit", filterChain.getRoot(), servicePath+"Test"+serviceI+".java");
			if(success){
				logger.debug("成功部署test{}测试类",myClass.getClassName());
			}else{
				logger.error("部署test{}测试类",myClass.getClassName());
				throw new RuntimeException("部署"+myClass.getClassName()+"测试类失败");
			}
		}
		
		
	}
	private String format(String templage,String...params){
		return MessageFormat.format(templage, params);
	}

}
