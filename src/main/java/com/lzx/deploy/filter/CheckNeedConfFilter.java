package com.lzx.deploy.filter;
/**
 * 需要检测配置的处理器
 * @author lzx
 *
 */
public abstract class CheckNeedConfFilter implements Filter{
	/**
	 * 获取需要检测的配置项集合
	 * @return
	 */
	public abstract String[] getConfNames();
	/**
	 * 当检测到没有配置需要的配置项时要采取的处理结果
	 * @return
	 */
	public abstract Result getResult();
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
