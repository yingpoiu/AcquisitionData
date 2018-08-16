package com.briup.environment.util;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.briup.environment.client.Client;
import com.briup.environment.client.Gather;
import com.briup.environment.gui.Login;
import com.briup.environment.server.DBStore;
import com.briup.environment.server.Server;

public class ConfigurationImpl implements Configuration{
	/*
	 * 读取config.xml内容，将标签作为key，类的实例对象作为value存储到map集合中
	 */

	private Map<String, EmdcModule> mapEmdc=new HashMap<>();
	public ConfigurationImpl() throws Exception{
		this("src/config.xml");
	}
public ConfigurationImpl(String filename) throws Exception {
	/*
	 * 使用dom解析config.xml配置文件
	 */
	DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
	DocumentBuilder builder=factory.newDocumentBuilder();
	Document document=builder.parse(new File(filename));
	/*
	 * 1.获取根节点
	 * 2.获取根节点之后的所有子节点NodeList
	 * 3.遍历子节点，获取元素类型的节点
	 * 4.遍历元素节点，获取节点名称和属性节点的值
	 * 5.将class属性节点的值进行实例化
	 * 6.map按照元素节点名称为key，实例化对象为value进行保存
	 */
	NodeList nodeList=document.getElementsByTagName("emdc");
	//Element element=document.getDocumentElement();获取根节点
	NodeList nodeList2=nodeList.item(0).getChildNodes();
	for (int i = 0; i < nodeList2.getLength(); i++) {
		if (nodeList2.item(i).getNodeType()==Node.ELEMENT_NODE) {
			 Element element=(Element) nodeList2.item(i);
			 String nodenameString=element.getNodeName();
				String nodename=element.getAttribute("class");
				EmdcModule emdcModule=(EmdcModule) Class.forName(nodename).newInstance();
				 mapEmdc.put(nodenameString, emdcModule);
				 /*
				  * 1.获取当前节点的子节点，构建properties对象
				  * 2.根据节点类型判断获取元素节点
				  * 3.获取元素节点的名称为key，文本节点值为value
				  * 4.properties按照节点名称为key和文本节点为value保存
				  * 5.EmdcModule调用init方法保存properties对象
				  */
			Properties properties=new Properties();
			NodeList nodeList3=nodeList2.item(i).getChildNodes();
			for(int j=0;j<nodeList3.getLength();j++){
				if (nodeList3.item(j).getNodeType()==Node.ELEMENT_NODE) {
	        		String pk=nodeList3.item(j).getNodeName();
	        		String pv=nodeList3.item(j).getTextContent();
	        		//System.out.println(pk+":"+pv);
	        		properties.put(pk, pv);
				}
			}
			emdcModule.init(properties);//加载第一步
        
          for(Object o:mapEmdc.values()){//对象加载第二步
              if(o instanceof EmdcModule){
                  ((EmdcModule)o).setConfiguration(this); //加载第三步
                  }
             
          }
				}	}}
	//	public ConfigurationImpl(String filepath) {
//		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
//		try {
//			DocumentBuilder builder=factory.newDocumentBuilder();
//			File file=new File(filepath);
//			Document doc=builder.parse(file);
//			NodeList nodeList=doc.getElementsByTagName("server");
//			Element element=(Element) nodeList.item(0);
//			String cls=element.getAttribute("class");
//			EmdcModule emdcModule=(EmdcModule) Class.forName(cls).newInstance();
//			Properties properties=new Properties();
//			NodeList nodeList2=nodeList.item(0).getChildNodes();
//            for(int i=0;i<nodeList2.getLength();i++){
//            	if (nodeList2.item(i).getNodeType()==Node.ELEMENT_NODE) {
//            		String pk=nodeList2.item(i).getNodeName();
//            		String pv=nodeList2.item(i).getTextContent();
//            		properties.put(pk, pv);
//				}
//            }
//            emdcModule.init(properties);
//            mapEmdc.put(element.getNodeName(), emdcModule);
//            for(Object o:mapEmdc.values()){
//                if(o instanceof ConfigurationAware){
//                    ((ConfigurationAware)o).setConfiguration(this);
//
//                }
//            }
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//	}

//public ConfigImpl(String filepath) {
//    SAXReader reader=new SAXReader();
//
//    try {
//        Document document = reader.read(filepath);
//        Element root=document.getRootElement();
//        for (Object object:root.elements()){
//            Element element=(Element)object;
//            String clsname=element.attributeValue("class");
//            WossModule wossModule=(WossModule)Class.forName(clsname).newInstance();
//            Properties properties=new Properties();
//            for(Object object1:element.elements()){
//                Element element1=(Element)object1;
//                String pk=element1.getName();
//                String pv=element1.getText();
//                properties.put(pk,pv);
//            }
//            wossModule.init(properties);
//            mapWoss.put(element.getName(),wossModule);
//            for(Object o:mapWoss.values()){
//                if(o instanceof ConfigurationAWare){
//                    ((ConfigurationAWare)o).setConfiguration(this);
//
//                }
//            }
//        }
//    } catch (DocumentException e) {
//        e.printStackTrace();
//    } catch (IllegalAccessException e) {
//        e.printStackTrace();
//    } catch (InstantiationException e) {
//        e.printStackTrace();
//    } catch (ClassNotFoundException e) {
//        e.printStackTrace();
//    }
//
//
//}

//第二步加载
	@Override
	public Log getLog() throws Exception {
		
		return (Log) mapEmdc.get("log");
	}

	@Override
	public Server getServer() throws Exception {
		// TODO Auto-generated method stub
		return (Server) mapEmdc.get("server");
	}

	@Override
	public Client getClient() throws Exception {
		// TODO Auto-generated method stub
		return (Client) mapEmdc.get("client");
	}

	@Override
	public DBStore getDbStore() throws Exception {
		// TODO Auto-generated method stub
		return (DBStore) mapEmdc.get("dbstore");
	}

	@Override
	public Gather getGather() throws Exception {
		// TODO Auto-generated method stub
		return (Gather) mapEmdc.get("gather");
	}

	@Override
	public BackUP getBackUP() throws Exception {
		// TODO Auto-generated method stub
		return (BackUP) mapEmdc.get("backup");
	}

	@Override
	public Login getLogin() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	public static void main(String[] args) throws Exception {
		ConfigurationImpl config=new ConfigurationImpl();
		 System.out.println(config.getServer());
		 System.out.println(config.getLog());
		 System.out.println(config.getBackUP());
		 System.out.println(config.getClient());
		 System.out.println(config.getDbStore());
		 System.out.println(config.getGather());
	}
	
	

}
