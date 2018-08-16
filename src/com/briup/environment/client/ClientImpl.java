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
	
		log.info("�ͻ��˺ͷ����������ڽ������ӡ�����������");
		//System.out.println("�ͻ��˺ͷ����������ڽ������ӡ�����������");
		socket=new Socket(ip,port);
		//System.out.println(ip);
		//System.out.println("���ӳɹ���׼�����͡���������");
		log.info("���ӳɹ���׼�����͡���������");
		/*
		 * �ͻ��˷������ݣ�ͨ��socket���������
		 * ���ڷ��Ͳɼ�ģ���ռ��õ�environment���϶���
		 *���з�װ��Object�����
		 */
		os=socket.getOutputStream();
		BufferedOutputStream bos=new BufferedOutputStream(os);
		oos=new ObjectOutputStream(bos);
		
		 //��֮ǰ����ʧ�ܲ�������clientback�ļ�������oos���У��ڶ�ȡ����ʱ�����������ļ���
        Object object = back.load("clientback", back.LOAD_UNREMOVE);
        if (object != null) {
            Collection<Environment> collection1 = (Collection<Environment>) object;
            log.warn("�ҵ�����");
            col.addAll(collection1);
            log.warn("װ�ر���");
        }
		oos.writeObject(col);
		oos.flush();
		//System.out.println("�ͻ������ݷ������");
		log.info("�ͻ������ݷ������");
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
		log.info("��Դ�Ѿ��ͷ�");
		}catch(Exception e){
			 //����
	        log.error("���ڱ�������");
	        try {
	            back.store("clientback", col, back.STORE_OVERRIDE);
	            log.warn("���ݽ�����");
	        } catch (Exception e1) {
	            log.error("�ͻ��˱������ݴ���");
	        }
	
		}
		
		//System.out.println("��Դ�Ѿ��ͷ�");
		
		
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
