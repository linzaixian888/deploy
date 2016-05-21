package com.lzx.deploy.filter;

public interface Filter {
	public void process(FilterChain filterChain)throws Exception;
}
