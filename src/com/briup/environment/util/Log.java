package com.briup.environment.util;

import java.util.Properties;
/*
 * �ýӿ��ṩ����־ģ��Ĺ淶����־ģ�齫��־��Ϣ����Ϊ���ּ���
 * ��ͬ�������־��¼��ʽ����¼��ʽ������ͬ
 */
public interface Log extends EmdcModule{
	//��¼debug�������־
	public void debug(String msg);
	//��¼info�������־
    public void info(String msg);
  //��¼warn�������־
    public void warn(String msg);
  //��¼error�������־
    public void error(String msg);
  //��¼fatal�������־
    public void fatal(String msg);
	
  
}
