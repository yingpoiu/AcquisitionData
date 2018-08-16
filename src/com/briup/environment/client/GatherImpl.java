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
	  //Environment对象集合用来保持获取的对象数据
	Collection<Environment> collection=new ArrayList<Environment>();
	//采集的原始文件；
	private String path;
	//保存上一次读取的字节数文件
	private String path2;
	private static LogImpl log;
	//private BackUPImpl backUPImpl;
	private static Configuration configuration;

	@Override
	public Collection<Environment> gather() throws Exception {
		/*
		 * 1.从path2指定的路径读取保存上一次读取到的字节数的文件，第一次该文件不存在，需要分情况判断
		 * 2.读取到radwtmp文件的有效字节数，将返回的值保存到path2指定的文件中
		 * 3.先略过上一次读取的字节数，在进行本次解析
		 */
		File file=new File(path2);
		long num=0;
		DataInputStream dis=null;
		if (file.exists()) {
			dis=new DataInputStream(new FileInputStream(file));
			num=dis.readLong();	
		}
		
		
		//RandomAccessFile流实现了略过功能
		//创建时提供两个参数，第一个参数表示读取的文件路径，第二个参数表示以什么形式读取，r代表只读.
		//避免二次读取，加载重复数据。
		RandomAccessFile raf=new RandomAccessFile(path, "r");
		long num2=raf.length();//直接读文件大小
		raf.seek(num);
		DataOutputStream dos=new DataOutputStream(new FileOutputStream(path2));
		dos.writeLong(num2);
		/*
		 * 1.构建缓存字符流按行读取数据
		 * 2.根据|分割字符串，根据第四个字段的不同分别赋予温度，湿度，二氧化碳，光照强度的环境名称
		 * 3.第七个字段表示16进制环境值，将16进制转换成10进制
		 * 如果是温度和湿度数据，前两个字节是温度数据，中间两个字节是湿度数据：
		 * 如果是二氧化碳和光照强度前两个字节就是对应的数据
		 * 4.温度和湿度是同一条记录，读取一行需要创建两个Environment对象，并且分别赋值
		 * 5.封装好的对象添加到coll集合中，
		 */
		
		String s=null;
		String[] str=null;
		Environment environment=null;
		//count统计温度和湿度条数（str[3]:16)
		int count=0;
		//count2统计光照强度条数(str[3]:256)
		int count2=0;
		//count3统计二氧化碳条数(str[3]:1280)
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
				//根据温度转换公式将16进制转换成10进制  substring取左不取右
				float value=(float)(((Integer.parseInt(str[6].substring(0,4),16))*0.00268127)-46.85);
				environment.setName("温度");
				environment.setData(value);
				collection.add(environment);
//				Environment environment2=new Environment();????
//				environment2=environment;
//				float value1=(float) (((Integer.parseInt(str[6].substring(4,8),16))*0.00190735)-6);
//				environment2.setName("湿度");
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
				environment.setName("湿度");
				environment.setData(value2);
				collection.add(environment);
				count++;
			}else  {
				float value3=Integer.valueOf(str[6].substring(0,4), 16);
				if ("256".equals(str[3])){
				environment.setName("光照强度");
				environment.setData(value3);
				collection.add(environment);
				count2++;
				}
				if ("1280".equals(str[3])) {
					environment.setName("二氧化碳");
					environment.setData(value3);
					collection.add(environment);
					count3++;
					
				}
			}
		}
		log.debug("采集环境数据："+collection.size());
		log.info("温度，湿度:"+count);
		log.info("光照强度:"+count2);
		log.info("二氧化碳:"+count3);
//		System.out.println("采集环境数据："+collection.size());
//		System.out.println("温度，湿度:"+count);
//		System.out.println("光照强度:"+count2);
//		System.out.println("二氧化碳:"+count3);
	
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
