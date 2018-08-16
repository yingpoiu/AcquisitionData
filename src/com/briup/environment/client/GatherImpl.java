package com.briup.environment.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.w3c.dom.Document;

import com.briup.environment.bean.Environment;
import com.briup.environment.util.BackUPImpl;
import com.briup.environment.util.Configuration;
import com.briup.environment.util.ConfigurationImpl;
import com.briup.environment.util.LogImpl;

public class GatherImpl implements Gather{
	  //Environment���󼯺��������ֻ�ȡ�Ķ�������
	Collection<Environment> collection=new ArrayList<Environment>();
	//�ɼ���ԭʼ�ļ���
	private String path;
	//������һ�ζ�ȡ���ֽ����ļ�
	private String path2;
	private static LogImpl log;
	//private BackUPImpl backUPImpl;
	private static Configuration configuration;

	@Override
	public Collection<Environment> gather() throws Exception {
		/*
		 * 1.��path2ָ����·����ȡ������һ�ζ�ȡ�����ֽ������ļ�����һ�θ��ļ������ڣ���Ҫ������ж�
		 * 2.��ȡ��radwtmp�ļ�����Ч�ֽ����������ص�ֵ���浽path2ָ�����ļ���
		 * 3.���Թ���һ�ζ�ȡ���ֽ������ڽ��б��ν���
		 */
		File file=new File(path2);
		long num=0;
		DataInputStream dis=null;
		if (file.exists()) {
			dis=new DataInputStream(new FileInputStream(file));
			num=dis.readLong();	
		}
		
		
		//RandomAccessFile��ʵ�����Թ�����
		//����ʱ�ṩ������������һ��������ʾ��ȡ���ļ�·�����ڶ���������ʾ��ʲô��ʽ��ȡ��r����ֻ��.
		//������ζ�ȡ�������ظ����ݡ�
		RandomAccessFile raf=new RandomAccessFile(path, "r");
		long num2=raf.length();//ֱ�Ӷ��ļ���С
		raf.seek(num);
		DataOutputStream dos=new DataOutputStream(new FileOutputStream(path2));
		dos.writeLong(num2);
		/*
		 * 1.���������ַ������ж�ȡ����
		 * 2.����|�ָ��ַ��������ݵ��ĸ��ֶεĲ�ͬ�ֱ����¶ȣ�ʪ�ȣ�������̼������ǿ�ȵĻ�������
		 * 3.���߸��ֶα�ʾ16���ƻ���ֵ����16����ת����10����
		 * ������¶Ⱥ�ʪ�����ݣ�ǰ�����ֽ����¶����ݣ��м������ֽ���ʪ�����ݣ�
		 * ����Ƕ�����̼�͹���ǿ��ǰ�����ֽھ��Ƕ�Ӧ������
		 * 4.�¶Ⱥ�ʪ����ͬһ����¼����ȡһ����Ҫ��������Environment���󣬲��ҷֱ�ֵ
		 * 5.��װ�õĶ�����ӵ�coll�����У�
		 */
		
		String s=null;
		String[] str=null;
		Environment environment=null;
		//countͳ���¶Ⱥ�ʪ��������str[3]:16)
		int count=0;
		//count2ͳ�ƹ���ǿ������(str[3]:256)
		int count2=0;
		//count3ͳ�ƶ�����̼����(str[3]:1280)
		int count3=0;
		while((s=raf.readLine())!=null){
			environment=new Environment();
			str=s.split("[|]");
			environment.setSrcId(str[0]);
			environment.setDesId(str[1]);
			environment.setDevId(str[2]);
			environment.setSersorAddress(str[3]);
			environment.setCount(Integer.parseInt(str[4]));
			environment.setCmd(str[5]);
			environment.setStatus(Integer.parseInt(str[7]));
			Long time=new Long(str[8]);
			Timestamp gather_date=new Timestamp(time);
			environment.setGather_date(gather_date);
			//environment.setGather_date(new Timestamp(new Long(str[8])));
			if ("16".equals(str[3])) {
				//�����¶�ת����ʽ��16����ת����10����  substringȡ��ȡ��
				float value=(float)(((Integer.parseInt(str[6].substring(0,4),16))*0.00268127)-46.85);
				environment.setName("�¶�");
				environment.setData(value);
				collection.add(environment);
//				Environment environment2=new Environment();????
//				environment2=environment;
//				float value1=(float) (((Integer.parseInt(str[6].substring(4,8),16))*0.00190735)-6);
//				environment2.setName("ʪ��");
//				environment2.setData(value1);
				environment=new Environment();
				environment.setSrcId(str[0]);
				environment.setDesId(str[1]);
				environment.setDevId(str[2]);
				environment.setSersorAddress(str[3]);
				environment.setCount(Integer.parseInt(str[4]));
				environment.setCmd(str[5]);
				environment.setStatus(Integer.parseInt(str[7]));
				environment.setGather_date(gather_date);
				float value2=(float) (((Integer.parseInt(str[6].substring(4,8),16))*0.00190735)-6);
				environment.setName("ʪ��");
				environment.setData(value2);
				collection.add(environment);
				count++;
			}else  {
				float value3=Integer.valueOf(str[6].substring(0,4), 16);
				if ("256".equals(str[3])){
				environment.setName("����ǿ��");
				environment.setData(value3);
				collection.add(environment);
				count2++;
				}
				if ("1280".equals(str[3])) {
					environment.setName("������̼");
					environment.setData(value3);
					collection.add(environment);
					count3++;
					
				}
			}
		}
		log.debug("�ɼ��������ݣ�"+collection.size());
		log.info("�¶ȣ�ʪ��:"+count);
		log.info("����ǿ��:"+count2);
		log.info("������̼:"+count3);
//		System.out.println("�ɼ��������ݣ�"+collection.size());
//		System.out.println("�¶ȣ�ʪ��:"+count);
//		System.out.println("����ǿ��:"+count2);
//		System.out.println("������̼:"+count3);
	
		return collection;
	}
	public static void main(String[] args) throws Exception {
		configuration=new ConfigurationImpl();
		GatherImpl gatherImpl=(GatherImpl) configuration.getGather();
		gatherImpl.gather();
	}
	@Override
	public void init(Properties properties) throws Exception {
		path=properties.getProperty("src-file");
		path2=properties.getProperty("record-file");
		
		
	}
	@Override
	public void setConfiguration(Configuration configuration) {
		this.configuration=configuration;
		try {
            log=(LogImpl)configuration.getLog();
            //backUPImpl=(BackUPImpl)configuration.getBackUP();
        } catch (Exception e) {
            e.printStackTrace();
        }
        

		
	}

}
