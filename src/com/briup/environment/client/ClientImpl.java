package com.briup.environment.client;

import java.awt.List;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import com.briup.environment.bean.Environment;
import com.briup.environment.util.BackUPImpl;
import com.briup.environment.util.Configuration;
import com.briup.environment.util.ConfigurationImpl;
import com.briup.environment.util.Log;
import com.briup.environment.util.LogImpl;

public class ClientImpl implements Client{
	private String ip;
	private int port;
	private Log log;
	private BackUPImpl back;
	private static Configuration configuration;
	private Socket socket;
	private OutputStream os;
	private ObjectOutputStream oos;
	
	public void send(Collection<Environment> col) throws Exception {
	
		log.info("客户端和服务器端正在建立连接。。。。。。");
		//System.out.println("客户端和服务器端正在建立连接。。。。。。");
		socket=new Socket(ip,port);
		//System.out.println(ip);
		//System.out.println("连接成功，准备发送。。。。。");
		log.info("连接成功，准备发送。。。。。");
		/*
		 * 客户端发送数据，通过socket创建输出流
		 * 由于发送采集模块收集好的environment集合对象
		 *所有封装成Object输出流
		 */
		os=socket.getOutputStream();
		BufferedOutputStream bos=new BufferedOutputStream(os);
		oos=new ObjectOutputStream(bos);
		
		 //把之前发送失败并保存在clientback文件，读到oos类中（在读取数据时，保留备份文件）
        Object object = back.load("clientback", back.LOAD_UNREMOVE);
        if (object != null) {
            Collection<Environment> collection1 = (Collection<Environment>) object;
            log.warn("找到备份");
            col.addAll(collection1);
            log.warn("装载备份");
        }
		oos.writeObject(col);
		oos.flush();
		//System.out.println("客户端数据发送完成");
		log.info("客户端数据发送完成");
		try{
		if (oos!=null) {
			oos.close();	
		}
		if (bos!=null) {
			bos.close();
			
		}
		if(os!=null){
			os.close();
		}
		if (socket!=null) {
			socket.close();
		}
		log.info("资源已经释放");
		}catch(Exception e){
			 //备份
	        log.error("正在备份数据");
	        try {
	            back.store("clientback", col, back.STORE_OVERRIDE);
	            log.warn("备份结束！");
	        } catch (Exception e1) {
	            log.error("客户端备份数据错误");
	        }
	
		}
		
		//System.out.println("资源已经释放");
		
		
	}
	public static void main(String[] args) throws Exception {
		configuration=new ConfigurationImpl();
		GatherImpl gatherImpl= (GatherImpl) configuration.getGather();
		//Collection<Environment> collection=gatherImpl.gather()
		ClientImpl clientImpl= (ClientImpl) configuration.getClient();
		//clientImpl.send(collection);
		clientImpl.send(gatherImpl.gather());
	}
	
	@Override
	public void init(Properties properties) throws Exception {
		// TODO Auto-generated method stub
				ip=properties.getProperty("ip");
				port=Integer.parseInt(properties.getProperty("port"));
		
	}
	@Override
	public void setConfiguration(Configuration configuration) {
		this.configuration=configuration;
		try {
            log=(LogImpl)configuration.getLog();
            back=(BackUPImpl)configuration.getBackUP();
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		
	}
	

}
