package com.briup.environment.util;

public interface BackUP extends EmdcModule{
	/*
	 * 写备份文件
	 */
	public void store(String filePath, Object obj,boolean append);
	/*
	 * 读取备份文件
	 */
	public Object load(String filePath, boolean del);

}
