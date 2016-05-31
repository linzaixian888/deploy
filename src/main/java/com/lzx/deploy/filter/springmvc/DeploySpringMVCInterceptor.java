package com.lzx.deploy.filter.springmvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lzx.deploy.filter.Filter;
import com.lzx.deploy.filter.FilterChain;
import com.lzx.deploy.util.Global;
import com.lzx.deploy.util.StringUtil;

public class DeploySpringMVCInterceptor implements Filter{
	private static Logger logger=LoggerFactory.getLogger(DeploySpringMVCInterceptor.class);
	private String interceptorClassName="interceptorClassName";
	private String interceptorPackage="interceptorPackage";
	private String interceptorPath;
	public void process(FilterChain filterChain) throws Exception {
		logger.debug("begin---开始部署Interceptor拦载器和Exception处理器");
		interceptorClassName=(String) filterChain.get(interceptorClassName);
		interceptorPackage=(String) filterChain.get(interceptorPackage);
		interceptorPath=StringUtil.sourcePackageToPath(interceptorPackage);
		Global.FU.process("SpringMVCInterceptor", filterChain.getRoot(), interceptorPath+interceptorClassName+".java");
		Global.FU.process("SpringMVCExceptionResolver", filterChain.getRoot(), interceptorPath+"ExceptionResolver.java");
		logger.debug("end---成功部署Interceptor拦载器和Exception处理器");
	}

}
