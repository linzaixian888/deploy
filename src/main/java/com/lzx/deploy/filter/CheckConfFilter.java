package com.lzx.deploy.filter;

import java.lang.reflect.Method;

import com.lzx.deploy.util.StringUtil;

/**
 * 需要检测配置的处理器
 * @author lzx
 *
 */
public abstract class CheckConfFilter implements Filter{
	Class<?> classType=this.getClass();
	/**
	 * 获取需要检测的配置项集合
	 * @return
	 */
	public abstract String[] getConfNames();
	
	public Result validate(FilterChain filterChain)throws Exception{
		String[] confNames=getConfNames();
		if(confNames!=null&&confNames.length>0){
			for(String confName:confNames){
				Result result= validate(confName, filterChain.get(confName));
				if(result!=null){
					return result;
				}
			}
		}
		return null;
	}
	public Result validate(String confName,Object value) throws Exception{
		try {
			Method m=classType.getDeclaredMethod("validate"+StringUtil.firstUp(confName), Object.class);
			return (Result) m.invoke(this, value);
		} catch (Exception e) {
		}
		return null;
	}
	/**
	 * 检测配置项处理结果
	 * @author lzx
	 *
	 */
	public enum Result{
		/**
		 * 停止运行
		 */
		stop,
		/**
		 * 跳过该处理器的运行
		 */
		skip
	}
}
