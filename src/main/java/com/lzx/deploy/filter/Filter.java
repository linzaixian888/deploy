package com.lzx.deploy.filter;

public interface Filter {
	public void process(Filter filter)throws Exception;
}
