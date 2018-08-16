package com.briup.environment.util;



import com.briup.environment.client.Client;
import com.briup.environment.client.Gather;
import com.briup.environment.gui.Login;
import com.briup.environment.server.DBStore;
import com.briup.environment.server.Server;

/*
 * Configuration�ӿ��ṩ������ģ��Ĺ淶������ģ��ͨ��Gather��Client
 * Server��DBStore��ģ���ʵ�������ʵ�����ݣ�ͨ������ģ����Ի�ȡ����ģ���ʵ��
 */
public interface Configuration {
	//��ȡ��־ģ���ʵ��
	public Log getLog()throws Exception;
	//��ȡ��������ģ���ʵ��
	public Server getServer() throws Exception;
	//��ȡ�ͻ���ģ���ʵ��
	public Client getClient() throws Exception;
	//��ȡ���ģ���ʵ��
	public DBStore getDbStore() throws Exception;
	//��ȡ�ɼ�ģ���ʵ��
	public Gather getGather() throws Exception;
	//��ȡ����ģ���ʵ��
	public BackUP getBackUP() throws Exception;
	//��ȡ��¼ģ���ʵ��
	public Login getLogin() throws Exception;
	

}
