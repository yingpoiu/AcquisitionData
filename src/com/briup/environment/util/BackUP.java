package com.briup.environment.util;

public interface BackUP extends EmdcModule{
	/*
	 * д�����ļ�
	 */
	public void store(String filePath, Object obj,boolean append);
	/*
	 * ��ȡ�����ļ�
	 */
	public Object load(String filePath, boolean del);

}
