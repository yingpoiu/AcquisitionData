package com.briup.environment.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Properties;

public class BackUPImpl implements BackUP{
      private String back_tmp;
      private static Configuration configuration;
      public static final boolean STORE_APPEND=true;
      public static final boolean STORE_OVERRIDE=false;
      public static final boolean LOAD_REMOVE=true;
      public static final boolean LOAD_UNREMOVE=false;
	@Override
	public void store(String filePath, Object obj, boolean append) {
		File file=new File(back_tmp,filePath);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			ObjectOutputStream ooStream = new ObjectOutputStream(new FileOutputStream(file, append));//追加
			ooStream.writeObject(obj);
			ooStream.flush();
			ooStream.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public Object load(String filePath, boolean del) {

		File file=new File(back_tmp,filePath);
		if (!file.exists()) {
			return null;
		}
		Object data=null;
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois=new ObjectInputStream(fis);
			data=ois.readObject();
			ois.close();
			fis.close();
			if (del==LOAD_REMOVE) {
				file.delete();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return data;
	}
	public static void main(String[] args) throws Exception {
		
        
		configuration=new ConfigurationImpl();
		BackUPImpl backUPImpl=(BackUPImpl) configuration.getBackUP();
		backUPImpl.store("a.txt", "my name is marry", true);
		//false是覆盖，true是追加
		Object object=backUPImpl.load("a.txt", false);
		//true是读取并删除，false是读取并保存文件
		System.out.println(object.toString());
	}

	@Override
	public void init(Properties properties) throws Exception {
		back_tmp=properties.getProperty("backup-file");
		
	}

	@Override
	public void setConfiguration(Configuration configuration) {
		this.configuration=configuration;
        

		
	}
	

}
