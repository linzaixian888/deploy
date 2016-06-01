package com.lzx.deploy.filter;
/**
 * 处理器
 * @author lzx
 *
 */
public interface Filter {
	/**
	 * 处理器的运行
	 * @param filterChain
	 * @throws Exception
	 */
	public void process(FilterChain filterChain)throws Exception;
}
