package com.briup.environment.server;

import java.util.Collection;

import com.briup.environment.bean.Environment;
import com.briup.environment.util.EmdcModule;
/*
 * Server�����������server����������ʼ���ܿͻ���
 * ������Ϣ���ҵ���dbstore�����յ������ݳ־û�
 */

public interface Server extends EmdcModule{

	public void reciver() throws Exception;
	//�÷�������ʹServer��ȫ��ֹͣ����
    public void shutdown() throws Exception;
}
