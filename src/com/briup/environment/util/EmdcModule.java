package com.briup.environment.util;

import java.util.Properties;

/*
 * �ýӿ��ǳ�����ģ�����������ģ��ĸ��ӿ�
 * ����ģ����ճ�ʼ��������Ϣ��ע������ģ��
 */
public interface EmdcModule {
	/*
	 * ������Ҫ��������Ϣ���ݽ����࣬����õ�������Ϣ
	 * ����г�ʼ����������ִ�и�����������֮ǰ����ִ���������
	 */
	public void init(Properties properties) throws Exception;
	public void setConfiguration(Configuration configuration);

}
