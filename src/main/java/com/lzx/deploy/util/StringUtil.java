package com.lzx.deploy.util;

import java.io.File;

public class StringUtil {
	public static String SOURCE="src"+File.separator;
	public static String TEST="test"+File.separator;
	public static void setSource(String str){
		if(!str.endsWith("/")&&!str.endsWith("\\")){
			SOURCE=str+File.separator;
		}
	}
	public static void setTest(String str){
		if(!str.endsWith("/")&&!str.endsWith("\\")){
			TEST=str+File.separator;
		}
	}
	/**
	 * 代码包路径转化成相对路径
	 * @param str
	 * @return
	 */
	public static String sourcePackageToPath(String str){
		str=str.replace(".", "/");
		return SOURCE+getPath(str);
	}
	/**
	 * 代码包路径转化成相对路径(测试专用)
	 * @param str
	 * @return
	 */
	public static String testPackageToPath(String str){
		str=str.replace(".", "/");
		return TEST+getPath(str);
	}
	/**
	 * 如果路径没有文件分隔符，则加上文件分隔符
	 * @param path
	 * @return
	 */
	public static String getPath(String path){
		boolean isAdd=true;
		String temp=path.trim();
		if(temp.endsWith("/")||temp.endsWith("\\")){
			isAdd=false;
		}
		if(isAdd){
			path=path+"/";
		}
		return path;
		
	}
	/**
	 * 首字母大写
	 * @param str
	 * @return
	 */
	public static String firstUp(String str){
		if(str==null||"".equals(str)){
			return str;
		}else{
			String temp=str.substring(0,1);
			return temp.toUpperCase()+str.substring(1);
		}
	}
	public static String sourceParse(String path){
		if(path.startsWith("classpath:")){
			path=SOURCE+path.substring(10);
		}else if(path.startsWith("/")){
			path=FileUtil.getWebRoot()+path;
		}else{
			path=null;
		}
		return path;
	}
	/**
	 * 去掉下横线并将各个首字母大写
	 * @param source
	 * @return
	 */
	public static String trimUnderLine(String source){
		if(source==null||"".equals(source)||source.indexOf("_")==-1){
			return firstUp(source);
		}else{
			StringBuffer sb=new StringBuffer();
			String[] strs=source.split("_");
			for(int i=0;i<strs.length;i++){
				sb.append(firstUp(strs[i]));
			}
			return sb.toString();
		}
	}
	/**
	 * 首字母小写
	 * @param str
	 * @return
	 */
	public static String firstLow(String str){
		if(str==null||"".equals(str)){
			return str;
		}else{
			String temp=str.substring(0,1);
			return temp.toLowerCase()+str.substring(1);
		}
	}
	
	
	public static void main(String[] args) {
		
	}
}
