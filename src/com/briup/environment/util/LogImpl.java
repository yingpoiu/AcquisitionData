package com.briup.environment.util;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LogImpl implements Log{
	private static String filepathString;
	private static Configuration configuration;
	private static Logger logger;
//	static{
//		logger=Logger.getRootLogger();
//		PropertyConfigurator.configure(filepathString);
//	}

	@Override
	public void debug(String msg) {
		logger.debug(msg);
		
	}

	@Override
	public void info(String msg) {
		logger.info(msg);
		
	}

	@Override
	public void warn(String msg) {
		logger.warn(msg);
		
	}

	@Override
	public void error(String msg) {
		logger.error(msg);
		
	}

	@Override
	public void fatal(String msg) {
		logger.fatal(msg);
		
	}
	
public static void main(String[] args) {
	LogImpl logger=new LogImpl();
	 logger.debug("debug");
     logger.error("error");
     logger.fatal("fatal");
     logger.info("info");
     logger.warn("warn");
}

@Override
public void init(Properties properties) throws Exception {
	filepathString=properties.getProperty("log-file");
	logger=Logger.getRootLogger();
	PropertyConfigurator.configure(filepathString);
	
}

@Override
public void setConfiguration(Configuration configuration) {
	this.configuration=configuration;
	
}




	
}
