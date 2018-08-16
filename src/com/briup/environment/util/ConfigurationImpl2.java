package com.briup.environment.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.briup.environment.client.Client;
import com.briup.environment.client.Gather;
import com.briup.environment.gui.Login;
import com.briup.environment.server.DBStore;
import com.briup.environment.server.Server;

public class ConfigurationImpl2 implements Configuration{
	private Map<String, EmdcModule> map=new HashMap<String, EmdcModule>();
public static void main(String[] args) throws Exception {
	ConfigurationImpl2 configurationImpl2=new ConfigurationImpl2();
	System.out.println(configurationImpl2.getGather());
	System.out.println(configurationImpl2.getServer());
	System.out.println(configurationImpl2.getClient());
	System.out.println(configurationImpl2.getDbStore());
	System.out.println(configurationImpl2.getBackUP());
	System.out.println(configurationImpl2.getLog());
	
	
	
}
	public ConfigurationImpl2() throws Exception{
		this("src/config.xml");
	}
	public ConfigurationImpl2(String filename) throws Exception {
		SAXReader reader=new SAXReader();
			Document document=reader.read(filename);
			Element rootElement=document.getRootElement();
		    for (Object o : rootElement.elements()) {
		    	Element element=(Element) o;
		    	String nmString=element.getName();
		    	String value=element.attributeValue("class");
		    	EmdcModule eModule=(EmdcModule) Class.forName(value).newInstance();
		    	map.put(nmString, eModule);
		    	Properties properties=new Properties();
		    	for (Object ob : element.elements()) {
		    		Element element2=(Element) ob;
		    		String name=element2.getName();
		    		String value1=element2.getText();
		    		//System.out.println(name+":"+value1);
		    		properties.put(name, value1);
		    		
				}
		    	eModule.init(properties);
		    	
				
			}
		
	}
	@Override
	public Log getLog() throws Exception {
		// TODO Auto-generated method stub
		return (Log) map.get("log");
	}

	@Override
	public Server getServer() throws Exception {
		// TODO Auto-generated method stub
		return (Server) map.get("server");
	}

	@Override
	public Client getClient() throws Exception {
		// TODO Auto-generated method stub
		return (Client) map.get("client");
	}

	@Override
	public DBStore getDbStore() throws Exception {
		// TODO Auto-generated method stub
		return (DBStore) map.get("dbstore");
	}

	@Override
	public Gather getGather() throws Exception {
		// TODO Auto-generated method stub
		return (Gather) map.get("gather");
	}

	@Override
	public BackUP getBackUP() throws Exception {
		// TODO Auto-generated method stub
		return (BackUP) map.get("backup");
	}

	@Override
	public Login getLogin() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
