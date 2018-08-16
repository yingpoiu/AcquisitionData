package com.briup.environment.server;

import java.awt.List;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Properties;

import com.briup.environment.bean.Environment;
import com.briup.environment.util.BackUPImpl;
import com.briup.environment.util.Configuration;
import com.briup.environment.util.ConfigurationImpl;
import com.briup.environment.util.Log;
import com.briup.environment.util.LogImpl;

public class ServerImpl implements Server{

	private Log log;
	private int port;
	private ServerSocket ss;
	private Socket s;
	private InputStream is;
	private BufferedInputStream bs;
	private ObjectInputStream ois;
	private DBStoreImpl dbStoreImpl;//入库前创建新对象;
	private BackUPImpl back;
	private static Configuration configuration;
	
	@Override
	public void reciver()  {
//ServerImpl.MyHandler handler=new ServerImpl().new MyHandler();
		log.info("服务器已经开启，等待客户端连接");
		try {
			ss=new ServerSocket(port);
			 while(true){
					s=ss.accept();//侦听并接受到此套接字的连接。
					MyHandler handler=new MyHandler(s);
					handler.start();
					}
		} catch (Exception e) {
			 try {
					shutdown();
					log.error("发生异常，网络断开");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
       
		
//		ss=new ServerSocket(port);
//		while(true){
//			new MyHandler(socket).start();
//		}
	}
	@Override
	public void shutdown() throws IOException {
		if (ois!=null) {
			ois.close();	
		}
		if (bs!=null) {
			bs.close();
		}
		if(is!=null){
			is.close();
		}
		if (s!=null) {
			s.close();	
		}
		if (ss!=null) {
			ss.close();
			
		}
		
		
		
	}
	public static void main(String[] args) throws Exception {
		configuration=new ConfigurationImpl();
		ServerImpl serverImpl=(ServerImpl) configuration.getServer();
		serverImpl.reciver();	
	}

class MyHandler extends Thread{//内部类
	private Socket s;
	
	public MyHandler(Socket s) {
	this.s = s;
    }

	@Override
	public void run() {

		String hostAddress=s.getInetAddress().getHostAddress();
		log.info("服务器已经开启，等待客户端连接");
		log.info("ip为"+hostAddress+"已连接。。。。");
		//System.out.println("服务器已经开启，等待客户端连接");
		Collection<Environment> collection=null;
		try {
			
			
			//System.out.println("客户端和服务器端连接成功，服务器准备接收数据。。。。。。");
			is=s.getInputStream();
			bs=new BufferedInputStream(is);
			ois=new ObjectInputStream(bs);
			collection=(Collection<Environment>) ois.readObject();//读取输入流
			//System.out.println("数据接收完毕，一共接收"+collection.size()+"条记录");
			log.info("数据接收，一共接收"+collection.size()+"条记录，准备开始入库。。。。。");
//			for (Environment environment : collection) {
//				System.out.println(environment);
//			}
			// 加载备份
	        //把之前发送失败并保存在serverback'文件，读到obj类中（在读取数据的同时，保留备份文件）
	        Object obj = back.load("serverback", back.LOAD_UNREMOVE);
	        if (obj != null) {
	            @SuppressWarnings("unchecked")
				Collection<Environment> col = (Collection<Environment>) obj;
	            log.warn("服务器正在加载备份数据");
	            collection.addAll(col);
	            log.warn("备份数据加载完成");
	        }
	        
	        	log.info("开始入库。。。。");
				dbStoreImpl.saveDB(collection);
				log.info("入库成功");
			
	        
	        
			//System.out.println("资源关闭");
		} catch (Exception e) {
			// 备份
            log.error("发生错误，服务器正在备份数据");
            try {
                //把发送失败的数据备份到serverback文件（在保存数据时，覆盖原来得文件）
                back.store("serverback", collection, back.STORE_OVERRIDE);
                log.warn("数据备份成功");

            } catch (Exception e1) {
                log.error("数据备份失败");
                
            }
           
		}
		
			
		
		
	}

	}

@Override
public void init(Properties properties) throws Exception {
	port=Integer.parseInt(properties.getProperty("port"));
	
}
@Override
public void setConfiguration(Configuration configuration) {
	this.configuration=configuration;
    try {
        log=(LogImpl)configuration.getLog();
        back=(BackUPImpl)configuration.getBackUP();
        dbStoreImpl=(DBStoreImpl) configuration.getDbStore();
    } catch (Exception e) {
        e.printStackTrace();
    }
	
}

}
	

