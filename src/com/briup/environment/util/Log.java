package com.briup.environment.util;

import java.util.Properties;
/*
 * 该接口提供了日志模块的规范，日志模块将日志信息划分为五种级别
 * 不同级别的日志记录格式，记录方式不尽相同
 */
public interface Log extends EmdcModule{
	//记录debug级别的日志
	public void debug(String msg);
	//记录info级别的日志
    public void info(String msg);
  //记录warn级别的日志
    public void warn(String msg);
  //记录error级别的日志
    public void error(String msg);
  //记录fatal级别的日志
    public void fatal(String msg);
	
  
}
