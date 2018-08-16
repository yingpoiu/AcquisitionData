package com.briup.environment.util;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LogTest {

	public static void main(String[] args) {
		Logger logger=Logger.getRootLogger();
		PropertyConfigurator.configure("src/log4j.properties");
		logger.debug("This is debug");
		//日志级别      
        //debug(调试)<info(信息)<warn(警告)<error(错误)<fatal(奔溃) 
		//当设置了级别更高的，级别低的不能显示
		logger.info("This is info");

	}
}
