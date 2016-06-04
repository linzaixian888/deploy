package com.lzx.deploy.filter;

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
		int size=10;
		for (int i = 0; i < size; i++) {
			chain.addFilter(new AFilter());
		}
		chain.process();
		Assert.assertEquals(size, chain.getFilterCount());
	}
	@Test
	public void testProcess2() throws Exception{
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
		Assert.assertEquals(size*3, chain.getSuccessFilterCount());
	}
	@Test(expected=RuntimeException.class)
	public void testProcess4() throws Exception{
		FilterChain chain=new FilterChain();
		chain.addFilter(new CFilter());
		chain.process();
	}
	@Test
	public void testProcess5() throws Exception{
		FilterChain chain=new FilterChain();
		int size=5;
		for (int i = 0; i < size; i++) {
			chain.addFilter(new DFilter());
		}
		
		FilterChain chain2=new FilterChain();
		for (int i = 0; i < size; i++) {
			chain2.addFilter(new AFilter());
		}
		chain.addFilter(chain2);
		chain.process();
		Assert.assertEquals(size, chain.getSuccessFilterCount());
	}
	
	
	
	public class AFilter  implements Filter{
		@Override
		public void process(FilterChain filterChain) {
			System.out.println("A");
			
		}
	}
	public class BFilter implements Filter{

		@Override
		public void process(FilterChain filterChain)throws Exception {
			// TODO Auto-generated method stub
			throw new Exception("报错了");
		}
	}
	public class CFilter extends CheckConfFilter{
		@Override
		public String[] getConfNames() {
			// TODO Auto-generated method stub
			return new String[]{"不可能存在的配置项"};
		}
		@Override
		public Result validate(String confName, Object value) throws Exception {
			return Result.stop;
		}

		@Override
		public void process(FilterChain filterChain) throws Exception {
			System.out.println("C");
			
		}

	}
	public class DFilter extends NotNullConfStopFilter{
		@Override
		public String[] getConfNames() {
			// TODO Auto-generated method stub
			return new String[]{"不可能存在的配置项"};
		}

		@Override
		public void process(FilterChain filterChain) throws Exception {
			System.out.println("D");
			
		}
		@Override
		public Result validate(String confName, Object value) throws Exception {
			// TODO Auto-generated method stub
			return Result.skip;
		}
		

	}
	
	
}
