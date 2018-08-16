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
		log.info("等待正在存入数据库");
	      //加载备份
	      Collection<Environment> co = (Collection<Environment>) back.load("dbstoreBack", back.LOAD_REMOVE);
	      if (co != null) {
	          log.info("正在装载备份数据");
	          coll.addAll(co);
	          log.info("装载完成");
	      }
		//connection.setAutoCommit(false);
		//表示当前批处理中的sql语句数
		int count=0;
		//记录当前天数，默认为0
		int day=0;
		for (Environment environment : coll) {
			/*
			 * 在两种情况下需要创建新的ps对象
			 * 1.ps==null第一次进入到for循环中
			 * 2.当日期发生变化时，day！=当前对象的采集天数
			 * environment.getGather_date（）返回的是Timestamp类型
			 * Timestamp对象的getDate（）方法返回day of month.返回的值在 1 和 31 之间，表示包含或开始于此 Date 对象表示的时间的月份中的某一天（用本地时区进行解释）。 
			 * Timestamp对象的getDay（）方法返回0-6对应的周日-周六
			 */
			if (preparedStatement==null||day!=environment.getGather_date().getDate()) {
				day=environment.getGather_date().getDate();
				log.debug("数据入库的天数"+day);
				//System.out.println("数据入库的天数"+day);
				/*
				 * 日期发生变化 eg：1号变2号
				 * 为防止1号中存在没有处理的sql语句
				 * eg：批处理缓存中500条记录，1号数据有300条，2号有200条，需要分开插入不同的数据库表
				 * 此时由于没有达到缓存大小，需要手动提交数据
				 */
				if (preparedStatement!=null) {
					//处理前一天留下来的sql语句
					preparedStatement.executeBatch();
					//清空缓存
					preparedStatement.clearBatch();
					//关闭ps，构建新的sql语句的ps对象
					preparedStatement.close();
					}
				//根据日期不同，产生不同的sql语句
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
			//将sql语句放入到批处理中
			preparedStatement.addBatch();
			/*
			 *记录当前批处理中sql语句数量，当满足批处理条数要求时提交或者如果for循环中
			 *存在未处理完毕的sql语句，也就是在for循环结束时，批处理中仍然有未处理的sql语句同样提交
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
		 // 备份
	      log.error("发生错误，数据回滚");
	      back.store("dbstoreBack",coll,back.STORE_APPEND);
	      if(connection!=null){
	          connection.rollback();
	      }
		}
			
			//System.out.println("入库完成！");
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
