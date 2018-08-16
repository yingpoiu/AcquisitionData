package com.briup.environment.client;

import java.util.Collection;

import com.briup.environment.bean.Environment;
import com.briup.environment.util.EmdcModule;

public interface Gather extends EmdcModule{
	/*
	 * Gather接口规定了采集模块所应用的方法
	 * 开始对物联网数据中心项目环境信息进行采集，
	 * 将采集的数据封装成Collection<Environment>集合
	 */

	public Collection<Environment> gather()throws Exception;
}
