package com.briup.environment.server;

import java.util.Collection;

import com.briup.environment.bean.Environment;
import com.briup.environment.util.EmdcModule;

/*
 * DBStore�ṩ�����ģ��Ĺ淶���ýӿ�ʵ����
 * ��Environment���Ͻ��г־û�
 */
public interface DBStore extends EmdcModule{//���нӿڶ�Ҫ��ע��˵��

   public void saveDB(Collection<Environment> coll) throws Exception;
}
