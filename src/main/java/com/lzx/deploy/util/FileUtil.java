package com.lzx.deploy.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
	/**
	 * 获得web.xml文件路径
	 * @return
	 */
	public static String getWebPath(){
		List<File> allFile=getFolderAndFileList(new File(getRootPath()));
		for(File file:allFile){
			if(!file.isFile()){
				continue;
			}
			String path=file.getAbsolutePath();
			if(path.endsWith("web.xml")&&path.indexOf("bin")==-1){
				return path;
			}
		}
		return null;
	}
	/**
	 * 获得项目名字
	 * @return
	 */
	public static String getProjectName() {
		File projectFile=new File(getRootPath());
		return projectFile.getName();
	}
	/**
	 * 获取项目的类型
	 * @return
	 */
	public static String getType() {
		List<File> allFile = FileUtil.getFolderAndFileList(new File(getRootPath()));
		for (File file : allFile) {
			if(!file.isDirectory()){
				continue;
			}
			String path = file.getAbsolutePath();
			if (path.endsWith("WEB-INF")) {
				return "bs";
			}
		}
		return "cs";
	}
	/**
	 * 获得web项目的根目录，一般是webapps
	 * @return
	 */
	public static File getWebRoot(){
		List<File> allFile=getFolderAndFileList(new File(getRootPath()));
		for(File file:allFile){
			String path=file.getAbsolutePath();
			if(!file.isDirectory()){
				continue;
			}
			if(path.endsWith("WEB-INF")&&path.indexOf("bin")==-1){
				return file.getParentFile();
			}
		}
		return null;
	}
	/**
	 * 获得项目的路径
	 * @return
	 */
	public static String getRootPath(){
		return new File("").getAbsolutePath();
	}
	
	/**
	 * 获得文件夹及子文件夹的所有文件和文件夹
	 * @param targetFile
	 * @return
	 */
	public static List<File> getFolderAndFileList(File targetFile){
		List<File> list=new ArrayList<File>();
		if(targetFile.isDirectory()){
			getFileList(targetFile, list,true);
		}else {
				list.add(targetFile);
			
		}
		return list;
	}
	/**
	 * 获得文件夹及子文件夹的所有文件
	 * @param targetFile
	 * @return
	 */
	public static List<File> getFileList(File targetFile){
		List<File> list=new ArrayList<File>();
		if(!targetFile.exists()){
			return list;
		}
		if(targetFile.isDirectory()){
			getFileList(targetFile, list,false);
		}else {
				list.add(targetFile);
			
		}
		return list;
	}
	/**
	 * 将某个文件夹内的所有文件和文件夹全部放入一个list集合
	 * @param folder
	 * @param list
	 * @param flag 是否包含文件夹
	 */
	private static void getFileList(File folder,List<File> list,boolean flag){
		File[] files=folder.listFiles();
		for(File f:files){
			if(f.isDirectory()){
				getFileList(f, list,flag);
				if(flag){
					list.add(f);
				}
			}else{
				list.add(f);
			}
				
		}
	}
	
}
