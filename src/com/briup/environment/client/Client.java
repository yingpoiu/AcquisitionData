package com.briup.environment.client;

import java.util.Collection;

import com.briup.environment.bean.Environment;
import com.briup.environment.util.EmdcModule;


public interface Client extends EmdcModule{
	/*
	 * 负责把采集好的数据发给服务器
	 * Client接口是物联网数据中心项目网络模块客户端的规范
	 * client的作用就是与服务器进行通信，传递信息
	 */
	public void send(Collection<Environment> col) throws Exception;
}
