package com.briup.environment.util;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LogTest {

	public static void main(String[] args) {
		Logger logger=Logger.getRootLogger();
		PropertyConfigurator.configure("src/log4j.properties");
		logger.debug("This is debug");
		//��־����      
        //debug(����)<info(��Ϣ)<warn(����)<error(����)<fatal(����) 
		//�������˼�����ߵģ�����͵Ĳ�����ʾ
		logger.info("This is info");

	}
}
