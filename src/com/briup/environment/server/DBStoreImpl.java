package com.briup.environment.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.Properties;

import com.briup.environment.bean.Environment;
import com.briup.environment.client.GatherImpl;
import com.briup.environment.util.BackUPImpl;
import com.briup.environment.util.Configuration;
import com.briup.environment.util.ConfigurationImpl;
import com.briup.environment.util.Log;
import com.briup.environment.util.LogImpl;

public class DBStoreImpl implements DBStore{
	private Log log;
	private BackUPImpl back;
	private static Configuration configuration;
    private String driver;
    private String url;
    private String username;
    private String password;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private int batchSize;
	@Override
	public void saveDB(Collection<Environment> coll) throws Exception {
		
		Class.forName(driver);
		connection=DriverManager.getConnection(url, username, password);
		log.info("�ȴ����ڴ������ݿ�");
	      //���ر���
	      Collection<Environment> co = (Collection<Environment>) back.load("dbstoreBack", back.LOAD_REMOVE);
	      if (co != null) {
	          log.info("����װ�ر�������");
	          coll.addAll(co);
	          log.info("װ�����");
	      }
		//connection.setAutoCommit(false);
		//��ʾ��ǰ�������е�sql�����
		int count=0;
		//��¼��ǰ������Ĭ��Ϊ0
		int day=0;
		for (Environment environment : coll) {
			/*
			 * �������������Ҫ�����µ�ps����
			 * 1.ps==null��һ�ν��뵽forѭ����
			 * 2.�����ڷ����仯ʱ��day��=��ǰ����Ĳɼ�����
			 * environment.getGather_date�������ص���Timestamp����
			 * Timestamp�����getDate������������day of month.���ص�ֵ�� 1 �� 31 ֮�䣬��ʾ������ʼ�ڴ� Date �����ʾ��ʱ����·��е�ĳһ�죨�ñ���ʱ�����н��ͣ��� 
			 * Timestamp�����getDay������������0-6��Ӧ������-����
			 */
			if (preparedStatement==null||day!=environment.getGather_date().getDate()) {
				day=environment.getGather_date().getDate();
				log.debug("������������"+day);
				//System.out.println("������������"+day);
				/*
				 * ���ڷ����仯 eg��1�ű�2��
				 * Ϊ��ֹ1���д���û�д����sql���
				 * eg������������500����¼��1��������300����2����200������Ҫ�ֿ����벻ͬ�����ݿ��
				 * ��ʱ����û�дﵽ�����С����Ҫ�ֶ��ύ����
				 */
				if (preparedStatement!=null) {
					//����ǰһ����������sql���
					preparedStatement.executeBatch();
					//��ջ���
					preparedStatement.clearBatch();
					//�ر�ps�������µ�sql����ps����
					preparedStatement.close();
					}
				//�������ڲ�ͬ��������ͬ��sql���
				String sql="insert into e_detail_"+day+" values(?,?,?,?,?,?,?,?,?)";
				preparedStatement=connection.prepareStatement(sql);
				
			}
			preparedStatement.setString(1, environment.getName());
			preparedStatement.setString(2, environment.getSrcId());
			preparedStatement.setString(3, environment.getDesId());
			preparedStatement.setString(4, environment.getSersorAddress());
			preparedStatement.setInt(5, environment.getCount());
			preparedStatement.setString(6, environment.getCmd());
			preparedStatement.setInt(7, environment.getStatus());
			preparedStatement.setFloat(8, environment.getData());
			preparedStatement.setTimestamp(9, environment.getGather_date());
			//��sql�����뵽��������
			preparedStatement.addBatch();
			/*
			 *��¼��ǰ��������sql�������������������������Ҫ��ʱ�ύ�������forѭ����
			 *����δ������ϵ�sql��䣬Ҳ������forѭ������ʱ������������Ȼ��δ�����sql���ͬ���ύ
			 */
			count++;
			if (count%batchSize==0) {	
				preparedStatement.executeBatch();
				preparedStatement.clearBatch();
				//connection.commit();
			}
				
			}
		try{
		if (preparedStatement!=null) {
			preparedStatement.executeBatch();
			preparedStatement.clearBatch();
			preparedStatement.close();
		}
		}catch(Exception e){
		 // ����
	      log.error("�����������ݻع�");
	      back.store("dbstoreBack",coll,back.STORE_APPEND);
	      if(connection!=null){
	          connection.rollback();
	      }
		}
			
			//System.out.println("�����ɣ�");
		}
		
		

	public static void main(String[] args) throws Exception {
		configuration=new ConfigurationImpl();
		DBStoreImpl store=(DBStoreImpl) configuration.getDbStore();
		store.saveDB(((GatherImpl)configuration.getGather()).gather());
	}



	@Override
	public void init(Properties properties) throws Exception {
		driver=properties.getProperty("driver");
		url=properties.getProperty("url");
		username=properties.getProperty("username");
		password=properties.getProperty("password");
		batchSize=Integer.parseInt(properties.getProperty("batchSize"));
		
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
