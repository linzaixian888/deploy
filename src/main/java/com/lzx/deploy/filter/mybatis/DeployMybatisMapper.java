package com.lzx.deploy.filter.mybatis;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lzx.deploy.filter.Filter;
import com.lzx.deploy.filter.FilterChain;
import com.lzx.deploy.pojo.MyClass;
import com.lzx.deploy.util.Global;
import com.lzx.deploy.util.StringUtil;

public class DeployMybatisMapper implements Filter{
	private static Logger logger=LoggerFactory.getLogger(DeployMybatisMapper.class);
	private String mapperPackage="mapperPackage";
	private List<MyClass> myClasses;
	private String mapperPath;
	boolean success=true;
	public void process(FilterChain filterChain) {
		logger.debug("begin---开始部署Mapper接口");
		mapperPackage=(String) filterChain.get(mapperPackage);
		myClasses=filterChain.getClassList();
		mapperPath=StringUtil.sourcePackageToPath(mapperPackage);
//		new File(mapperPath).mkdirs();
		success=Global.FU.process("MybatisBaseMapper", filterChain.getRoot(), mapperPath+"BaseMapper.java");
		if(success){
			logger.debug("成功部署BaseMapper接口");
		}else{
			logger.error("部署BaseMapper接口失败");
			throw new RuntimeException("部署BaseMapper接口失败");
		}
		for(MyClass myClass:myClasses){
			filterChain.put("myClass", myClass);
			success=Global.FU.process("MybatisMapper", filterChain.getRoot(),mapperPath+myClass.getClassName()+"Mapper.java");
			if(success){
				logger.debug("成功部署{}Mapper接口",myClass.getClassName());
			}else{
				logger.error("部署{}Mapper接口失败",myClass.getClassName());
				throw new RuntimeException("部署"+myClass.getClassName()+"Mapper接口失败");
			}
		}
		logger.debug("end---成功部署所有Mapper接口");
		
	}
}
