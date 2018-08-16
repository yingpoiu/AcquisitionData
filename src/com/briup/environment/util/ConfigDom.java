package com.briup.environment.util;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ConfigDom {

	public static void main(String[] args) throws Exception {
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		DocumentBuilder builder=factory.newDocumentBuilder();
		Document document=builder.parse(new File("src/emdc.xml"));
		NodeList nodeList=document.getElementsByTagName("emdc");
		NodeList nodeList2=nodeList.item(0).getChildNodes();
		for (int i = 0; i < nodeList2.getLength(); i++) {
			
			if (nodeList2.item(i).getNodeType()==Node.ELEMENT_NODE) {
				String nodenameString=nodeList2.item(i).getNodeName();
				//System.out.println(nodenameString);
				Element element=(Element) nodeList2.item(i);
				String nodename=element.getAttribute("class");
				if ("server".equals(nodenameString)) {
					System.out.println(nodename);
				}else if ("dbstore".equals(nodenameString)) {
					
					System.out.println(nodename);
				}else if ("client".equals(nodenameString)) {

					System.out.println(nodename);
				}else if ("gather".equals(nodenameString)) {

					System.out.println(nodename);
				}else if ("log".equals(nodenameString)) {

					System.out.println(nodename);
					
				}else if ("backup".equals(nodenameString)) {

					System.out.println(nodename);
				}
				NodeList nodeList3=nodeList2.item(i).getChildNodes();
				for(int j=0;j<nodeList3.getLength();j++){
					if (nodeList3.item(j).getNodeType()==Node.ELEMENT_NODE) {
		        		String pk=nodeList3.item(j).getNodeName();
		        		String pv=nodeList3.item(j).getTextContent();
		        		System.out.println(pk+":"+pv);
		        		//properties.put(pk, pv);
					}
				}
				
				
			}
			
			
		}
		
		
		
	}
}
