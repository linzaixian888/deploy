package com.lzx.deploy.filter;

public class DeployMain {
	public static void main(String[] args) throws Exception {
		FilterChain chain=new FilterChain();
		chain.addFilter(new DeployReadConfig());
		chain.addFilter(new DeployInitDBData());
		chain.process();
	}
}
