package com.lzx.deploy.filter.util;

public class Global {
	/**
	 * 模版处理引擎
	 */
	public static FreemarkerUtil FU;
	static{
		FU=new FreemarkerUtil();
		FU.addClassLoader(Global.class, "/com/linzaixian/deploy/template");
		FU.setSuffix("ftl");
		FU.setIsCloseStream(true);
	}

	
}
