package com.lzx.deploy.deploy;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.lzx.deploy.filter.Filter;
import com.lzx.deploy.filter.FilterChain;
public class TestFilterChain {
	FilterChain chain=null;
	@Before
	public void setUp(){
		chain=new FilterChain();
	}
	@After
	public void TearDown(){
		
	}
	@Test
	public void testGetFilterCount() throws Exception{
		
		int size=10;
		for (int i = 0; i < size; i++) {
			chain.addFilter(new AFilter());
		}
		for (int i = 0; i < size; i++) {
			chain.addFilter(new BFilter());
		}
		try {
			chain.process();
		} catch (Exception e) {
		}
		Assert.assertEquals(size+1, chain.getFilterCount());
	}
	@Test
	public void testGetSuccessFilterCount() throws Exception{
		FilterChain chain=new FilterChain();
		int size=10;
		for (int i = 0; i < size; i++) {
			chain.addFilter(new AFilter());
		}
		for (int i = 0; i < size; i++) {
			chain.addFilter(new BFilter());
		}
		try {
			chain.process();
		} catch (Exception e) {
		}
		Assert.assertEquals(size, chain.getSuccessFilterCount());
	}

	@Test
	public void testProcess() throws Exception{
		FilterChain chain=new FilterChain();
		int size=10;
		for (int i = 0; i < size; i++) {
			chain.addFilter(new AFilter());
		}
		chain.process();
		Assert.assertEquals(size, chain.getFilterCount());
	}
	@Test
	public void testProcess2() throws Exception{
		FilterChain chain=new FilterChain();
		int size=10;
		for (int i = 0; i < size; i++) {
			chain.addFilter(new AFilter());
		}
		for (int i = 0; i < size; i++) {
			chain.addFilter(new BFilter());
		}
		try {
			chain.process(chain);
		} catch (Exception e) {
		}
		Assert.assertEquals(size, chain.getSuccessFilterCount());
		
	}
	@Test
	public void testProcess3() throws Exception{
		FilterChain chain=new FilterChain();
		int size=5;
		for (int i = 0; i < size; i++) {
			chain.addFilter(new AFilter());
		}
		
		FilterChain chain2=new FilterChain();
		for (int i = 0; i < size; i++) {
			chain2.addFilter(new AFilter());
		}
		chain.addFilter(chain2);
		for (int i = 0; i < size; i++) {
			chain.addFilter(new AFilter());
		}
		chain.process();
		Assert.assertEquals(size*3, chain.getFilterCount());
	}
	
	
	
	
	public class AFilter  implements Filter{
		@Override
		public void process(Filter filter) {
			System.out.println("A");
			
		}
	}
	public class BFilter implements Filter{

		@Override
		public void process(Filter filter)throws Exception {
			// TODO Auto-generated method stub
			throw new Exception("报错了");
			
		}

	}
}
