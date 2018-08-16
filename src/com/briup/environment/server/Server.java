package com.briup.environment.server;

import java.util.Collection;

import com.briup.environment.bean.Environment;
import com.briup.environment.util.EmdcModule;
/*
 * Server用于启动这个server服务器，开始接受客户端
 * 发送信息并且调用dbstore将接收到的数据持久化
 */

public interface Server extends EmdcModule{

	public void reciver() throws Exception;
	//该方法用于使Server安全的停止运行
    public void shutdown() throws Exception;
}
