package com.briup.environment.client;

import java.util.Collection;

import com.briup.environment.bean.Environment;
import com.briup.environment.util.EmdcModule;


public interface Client extends EmdcModule{
	/*
	 * ����Ѳɼ��õ����ݷ���������
	 * Client�ӿ�������������������Ŀ����ģ��ͻ��˵Ĺ淶
	 * client�����þ��������������ͨ�ţ�������Ϣ
	 */
	public void send(Collection<Environment> col) throws Exception;
}
