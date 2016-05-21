package com.lzx.deploy.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lzx.deploy.pojo.MyClass;
import com.lzx.deploy.util.StringPrintWriter;

public class FilterChain implements Filter{
	private static Logger logger=LoggerFactory.getLogger(FilterChain.class);
	/**
	 * 启动过的过滤器数量
	 */
	private int filterCount=0;
	/**
	 * 是否成功处理所有
	 */
	private boolean isSuccess=true;
	/**
	 * 当前过滤器链拥有的过滤器
	 */
	private List<Filter> filters=new ArrayList<Filter>();
	
	
	List<MyClass> classList=new ArrayList<MyClass>();
	/**
	 * 当前处理的过滤器下标索引
	 */
	
	private int index=0;
	/**
	 * 共享存储,保存传递信息
	 */
	Map<String, Object> map=new HashMap<String, Object>();
	/**
	 * 处理完其中的过滤器链后接下来要处理的过滤器,也就是要返回之前的过滤器继续处理
	 */
	private FilterChain lastFilterChain;
	public void process(FilterChain filterChain) throws Exception {
		FilterChain chain;
		if(index>=filters.size()){
			if(this.lastFilterChain!=null){
				//如果该过滤器链处理完了，判断还有没有之前的过滤器链要处理
				chain=this.lastFilterChain;
				if(chain!=null){
					chain.setRoot(map);
//					chain.setClassList(this.classList);
					chain.process(chain);
					addFilterCount(chain, this.filterCount);
					
					System.out.println(chain.filterCount);
				}
			}
			
		}else{
			Filter nowFilter=filters.get(index);
			index++;
			if(nowFilter instanceof FilterChain){
				//判断到要处理的是过滤器链
				processFilterChain((FilterChain) nowFilter);
			}else {
				//判断当前要处理的是过滤器
				//计算是第几个处理器 
				processFilter(nowFilter);
			}
		}
		
	}
	
	private synchronized void addFilterCount(FilterChain chain,int add){
		chain.filterCount=chain.filterCount+add;
	}
	private void processFilterChain(FilterChain nowFilterChain) throws Exception{
		//标识要返回原来的过滤器链
		nowFilterChain.lastFilterChain=this;
		nowFilterChain.setRoot(this.map);
//		chain.setClassList(this.classList);
		nowFilterChain.process(nowFilterChain);
	}
	private void processFilter(Filter nowFilter)throws Exception{
		//计算是第几个处理器 
		addFilterCount(this, 1);
		logger.debug("***当前为第{}个过滤器:{},位于{}链中***",filterCount,nowFilter.getClass().getName(),this);
		try {
			nowFilter.process(this);
		} catch (Exception e) {
			Logger filterLog=LoggerFactory.getLogger(nowFilter.getClass());
			StringPrintWriter spw=new StringPrintWriter();
			e.printStackTrace(spw);
			filterLog.error("发生异常:{}",spw.getString());
			this.isSuccess=false;
			throw e;
		}
		//过滤器链继续往下处理
		this.process(this);
	}
	public void process() throws Exception{
		process(this);
	}
	public void addFilter(Filter filter){
		filters.add(filter);
	}
	public void put(String key,Object value){
		map.put(key, value);
	}
	public Object get(String key){
		return map.get(key);
	}
	public Map getRoot(){
		return this.map;
	}
	public void setRoot(Map map){
		this.map=map;
	}
	
	public void addClassList(MyClass myClass){
		classList.add(myClass);
	}
	public List<MyClass> getClassList(){
		return this.classList;
	}
	public void setClassList(List<MyClass> classList) {
		this.classList = classList;
	}
	/**
	 * 获取运行过的过滤器数量(运行报错的也算，没运行过的就不算)
	 * @return
	 */
	public synchronized int getFilterCount() {
		return filterCount;
	}
	/**
	 * 获取正常运行的过滤器数量
	 * @return
	 */
	public synchronized int getSuccessFilterCount(){
		if(!isSuccess){
			return filterCount>0?--filterCount:0;
		}else{
			return filterCount;
		}
	}
	public static void main(String[] args) {
		
	}
}
