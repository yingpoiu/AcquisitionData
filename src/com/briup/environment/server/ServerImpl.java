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
	private DBStoreImpl dbStoreImpl;//���ǰ�����¶���;
	private BackUPImpl back;
	private static Configuration configuration;
	
	@Override
	public void reciver()  {
//ServerImpl.MyHandler handler=new ServerImpl().new MyHandler();
		log.info("�������Ѿ��������ȴ��ͻ�������");
		try {
			ss=new ServerSocket(port);
			 while(true){
					s=ss.accept();//���������ܵ����׽��ֵ����ӡ�
					MyHandler handler=new MyHandler(s);
					handler.start();
					}
		} catch (Exception e) {
			 try {
					shutdown();
					log.error("�����쳣������Ͽ�");
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

class MyHandler extends Thread{//�ڲ���
	private Socket s;
	
	public MyHandler(Socket s) {
	this.s = s;
    }

	@Override
	public void run() {

		String hostAddress=s.getInetAddress().getHostAddress();
		log.info("�������Ѿ��������ȴ��ͻ�������");
		log.info("ipΪ"+hostAddress+"�����ӡ�������");
		//System.out.println("�������Ѿ��������ȴ��ͻ�������");
		Collection<Environment> collection=null;
		try {
			
			
			//System.out.println("�ͻ��˺ͷ����������ӳɹ���������׼���������ݡ�����������");
			is=s.getInputStream();
			bs=new BufferedInputStream(is);
			ois=new ObjectInputStream(bs);
			collection=(Collection<Environment>) ois.readObject();//��ȡ������
			//System.out.println("���ݽ�����ϣ�һ������"+collection.size()+"����¼");
			log.info("���ݽ��գ�һ������"+collection.size()+"����¼��׼����ʼ��⡣��������");
//			for (Environment environment : collection) {
//				System.out.println(environment);
//			}
			// ���ر���
	        //��֮ǰ����ʧ�ܲ�������serverback'�ļ�������obj���У��ڶ�ȡ���ݵ�ͬʱ�����������ļ���
	        Object obj = back.load("serverback", back.LOAD_UNREMOVE);
	        if (obj != null) {
	            @SuppressWarnings("unchecked")
				Collection<Environment> col = (Collection<Environment>) obj;
	            log.warn("���������ڼ��ر�������");
	            collection.addAll(col);
	            log.warn("�������ݼ������");
	        }
	        
	        	log.info("��ʼ��⡣������");
				dbStoreImpl.saveDB(collection);
				log.info("���ɹ�");
			
	        
	        
			//System.out.println("��Դ�ر�");
		} catch (Exception e) {
			// ����
            log.error("�������󣬷��������ڱ�������");
            try {
                //�ѷ���ʧ�ܵ����ݱ��ݵ�serverback�ļ����ڱ�������ʱ������ԭ�����ļ���
                back.store("serverback", collection, back.STORE_OVERRIDE);
                log.warn("���ݱ��ݳɹ�");

            } catch (Exception e1) {
                log.error("���ݱ���ʧ��");
                
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
	

