package com.briup.environment.test;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.briup.environment.bean.Environment;
import com.briup.environment.client.Client;
import com.briup.environment.client.Gather;
import com.briup.environment.server.DBStore;
import com.briup.environment.server.ServerImpl;
import com.briup.environment.util.BackUP;
import com.briup.environment.util.BackUPImpl;
import com.briup.environment.util.Configuration;
import com.briup.environment.util.ConfigurationImpl;

public class EMDCTest {
	private Configuration configuration;
    @Before
    public void setUP() throws Exception{
    	configuration=new ConfigurationImpl();
    }
    @Test
    public void client_test() throws Exception{
    	Client client=configuration.getClient();
    	Gather gather=configuration.getGather();
    	Collection<Environment> collection=gather.gather();
    	client.send(collection);
    }
    @Test
    public void server_test() throws Exception{
    	
		ServerImpl serverImpl=(ServerImpl) configuration.getServer();
		serverImpl.reciver();	
    }
    @Test
    public void gather_test() throws Exception{
    	
    	Gather gather=configuration.getGather();
		gather.gather();
    }
    @Test
    public void dbstore_test() throws Exception{
    	
    	DBStore store=configuration.getDbStore();
		store.saveDB(configuration.getGather().gather());;
    }
    @Test
    public void back_test() throws Exception{
    	
		BackUP backUPImpl=configuration.getBackUP();
		backUPImpl.store("b.txt", "my name is marry", true);
		//false是覆盖，true是追加
		Object object=backUPImpl.load("a.txt", false);
		//true是读取并删除，false是读取并保存文件
		System.out.println(object.toString());
    }
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
