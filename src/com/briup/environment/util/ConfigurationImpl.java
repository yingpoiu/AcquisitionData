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
	 * ��ȡconfig.xml���ݣ�����ǩ��Ϊkey�����ʵ��������Ϊvalue�洢��map������
	 */

	private Map<String, EmdcModule> mapEmdc=new HashMap<>();
	public ConfigurationImpl() throws Exception{
		this("src/config.xml");
	}
public ConfigurationImpl(String filename) throws Exception {
	/*
	 * ʹ��dom����config.xml�����ļ�
	 */
	DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
	DocumentBuilder builder=factory.newDocumentBuilder();
	Document document=builder.parse(new File(filename));
	/*
	 * 1.��ȡ���ڵ�
	 * 2.��ȡ���ڵ�֮��������ӽڵ�NodeList
	 * 3.�����ӽڵ㣬��ȡԪ�����͵Ľڵ�
	 * 4.����Ԫ�ؽڵ㣬��ȡ�ڵ����ƺ����Խڵ��ֵ
	 * 5.��class���Խڵ��ֵ����ʵ����
	 * 6.map����Ԫ�ؽڵ�����Ϊkey��ʵ��������Ϊvalue���б���
	 */
	NodeList nodeList=document.getElementsByTagName("emdc");
	//Element element=document.getDocumentElement();��ȡ���ڵ�
	NodeList nodeList2=nodeList.item(0).getChildNodes();
	for (int i = 0; i < nodeList2.getLength(); i++) {
		if (nodeList2.item(i).getNodeType()==Node.ELEMENT_NODE) {
			 Element element=(Element) nodeList2.item(i);
			 String nodenameString=element.getNodeName();
				String nodename=element.getAttribute("class");
				EmdcModule emdcModule=(EmdcModule) Class.forName(nodename).newInstance();
				 mapEmdc.put(nodenameString, emdcModule);
				 /*
				  * 1.��ȡ��ǰ�ڵ���ӽڵ㣬����properties����
				  * 2.���ݽڵ������жϻ�ȡԪ�ؽڵ�
				  * 3.��ȡԪ�ؽڵ������Ϊkey���ı��ڵ�ֵΪvalue
				  * 4.properties���սڵ�����Ϊkey���ı��ڵ�Ϊvalue����
				  * 5.EmdcModule����init��������properties����
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
			emdcModule.init(properties);//���ص�һ��
        
          for(Object o:mapEmdc.values()){//������صڶ���
              if(o instanceof EmdcModule){
                  ((EmdcModule)o).setConfiguration(this); //���ص�����
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

//�ڶ�������
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
