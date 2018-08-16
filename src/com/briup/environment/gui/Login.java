package com.briup.environment.gui;

/*
 * Login接口定义了登录模块所应有的方法，当login执行方法login时
 * 出现登录页面客户端，管理员信息验证成通过可以进入物联网数据中心后台操作界面
 */
public interface Login {
	/*
	 * 按照时间和环境类型查询和统计数据
	 */
	public void login() throws Exception;

}
