package com.briup.environment.gui;

/*
 * �ýӿڵ�ʵ������Գ־û�user����Ͱ������Ʋ��Ҹ��û��Ƿ����
 */
public interface UserDao {
//��user����־û�����
	public void save(User user)throws Exception;
	//�������Ʋ���user����
	public User find(String name)throws Exception;
}
