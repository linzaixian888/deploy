package com.lzx.deploy.filter.struts2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lzx.deploy.filter.Filter;
import com.lzx.deploy.filter.FilterChain;
import com.lzx.deploy.util.Global;
import com.lzx.deploy.util.StringUtil;

public class DeployStrtus2Interceptor implements Filter{
	private static Logger logger=LoggerFactory.getLogger(DeployStrtus2Interceptor.class);
	private String interceptorClassName="interceptorClassName";
	private String interceptorPackage="interceptorPackage";
	private String interceptorPath;
	boolean success=true;
	public void process(FilterChain filterChain) {
		logger.debug("begin---开始部署Interceptor拦载器");
		interceptorClassName=(String) filterChain.get(interceptorClassName);
		interceptorPackage=(String) filterChain.get(interceptorPackage);
		interceptorPath=StringUtil.sourcePackageToPath(interceptorPackage);
		success=Global.FU.process("Struts2Interceptor", filterChain.getRoot(), interceptorPath+interceptorClassName+".java");
		if(success){
			logger.debug("end---失功部署Interceptor拦载器");
		}else{
			logger.error("部署Interceptor拦载器失败");
			throw new RuntimeException("部署Interceptor拦载器失败");
		}
		
	}
}
