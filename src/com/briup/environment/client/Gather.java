package com.briup.environment.client;

import java.util.Collection;

import com.briup.environment.bean.Environment;
import com.briup.environment.util.EmdcModule;

public interface Gather extends EmdcModule{
	/*
	 * Gather�ӿڹ涨�˲ɼ�ģ����Ӧ�õķ���
	 * ��ʼ������������������Ŀ������Ϣ���вɼ���
	 * ���ɼ������ݷ�װ��Collection<Environment>����
	 */

	public Collection<Environment> gather()throws Exception;
}
