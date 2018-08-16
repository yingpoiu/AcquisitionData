package com.briup.environment.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class UserLoginTest implements Login{
	static Container container;//创建一个静态提示框容器
	private static int center;
	private JFrame guanli;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UserLoginTest user=new UserLoginTest();
		user.login();//实现这个类的方法

}


	@Override
	public void login() {
		guanli=new JFrame("环境监控数据中心管理员登录界面");//框架
		guanli.setSize(300, 200);//框架的长宽
		//guanli.setLayout(new GridLayout(3, 3));//网格布局 3行3列
		JLabel text0=new JLabel("用户登录",center);
		text0.setFont(new Font("楷体",15,30));//
		text0.setForeground(Color.blue);
		JLabel text=new JLabel("用户",center);//不可编辑的标签 用户 居中显示
		final JTextField b=new JTextField(14);//可编辑的文本框 输入用户名
		JLabel text1=new JLabel("密码",center);//不可编辑的标签 用户 居中显示
		final JPasswordField b1=new JPasswordField(14);//可编辑的文本框 输入用户名
		SpringLayout layout = new SpringLayout();
		guanli.setLayout(layout);//调用布局
		JButton left=new JButton("注册");//按钮 点击登录
		guanli.add(left);
		
		JButton right=new JButton("登录");//点击取消
		guanli.add(right);
		layout.putConstraint(SpringLayout.WEST, text0, 80, SpringLayout.WEST, guanli);
	    layout.putConstraint(SpringLayout.NORTH, text0,10, SpringLayout.NORTH, guanli);
	    
		layout.putConstraint(SpringLayout.WEST, text, 50, SpringLayout.WEST, guanli);
	    layout.putConstraint(SpringLayout.NORTH, text,60, SpringLayout.NORTH, text0);
	    
	    layout.putConstraint(SpringLayout.WEST, b, 40, SpringLayout.WEST, text);
	    layout.putConstraint(SpringLayout.NORTH, b,60, SpringLayout.NORTH, text0);
	    
	    layout.putConstraint(SpringLayout.WEST, text1, 50, SpringLayout.WEST, guanli);
	    layout.putConstraint(SpringLayout.NORTH, text1,25, SpringLayout.NORTH, text);
	    
	    layout.putConstraint(SpringLayout.WEST, b1, 40, SpringLayout.WEST,text1);
	    layout.putConstraint(SpringLayout.NORTH,b1,25, SpringLayout.NORTH, b);
	    
		layout.putConstraint(SpringLayout.WEST, left, 100, SpringLayout.WEST, guanli);
	    layout.putConstraint(SpringLayout.NORTH, left, 30, SpringLayout.NORTH, text1);
	     // 设置第一个组件相对于容器的位置we
			layout.putConstraint(SpringLayout.WEST, right, 70, SpringLayout.WEST,left);
			layout.putConstraint(SpringLayout.NORTH, right, 30, SpringLayout.NORTH, text1);
		final JOptionPane a2=new JOptionPane();//Container容器的组件,提示框组件
		
		guanli.add(text0);
		guanli.add(text);//添加组件
		guanli.add(b);
		guanli.add(text1);
		guanli.add(b1);
		
		guanli.setBackground(null);
		guanli.setLocationRelativeTo(null);//在屏幕中间显示(居中显示) 
		guanli.setVisible(true);//显示框架布局
		  
        guanli.setResizable(false); //锁定窗体 
        guanli.setDefaultCloseOperation(3);//关闭应用程序
		right.addActionListener(new ActionListener() {//实现登录事件
			@Override
			public void actionPerformed(ActionEvent e) {
				
				 String dbURL = "jdbc:oracle:thin:@127.0.0.1:1521:XE";	// 连接字符串
					String userName = "envir";	String userPwd = "envir";
					
					
					try{
						Class.forName("oracle.jdbc.driver.OracleDriver");
						Connection dbConn = DriverManager.getConnection(dbURL, userName, userPwd);
						Statement  stmt = dbConn.createStatement();//连接数据库
				  String c=b.getText();//给输入的用户名赋变量
				  String c1=b1.getText();//给输入的密码赋变量
				  
				    String sql1="SELECT * FROM users where name='"+c+"' and password='"+c1+"'";
				    ResultSet rs=stmt.executeQuery(sql1);//执行查询语句
				    int d=0;
					  while (rs.next()) {
						  String no = rs.getString("name");//匹配数据
						  String bc=rs.getString("password");
						  d++;
						  }
					  if(d==0){//根据匹配的次数判定用户是否存在
						  String msg="用户不存在或密码错误！请重新输入";
						  String title="错误操作！";
						  a2.showMessageDialog(container,msg,title,JOptionPane.INFORMATION_MESSAGE);
						//提示框
					  }
					  else{
						 
						  String msg="进入查询界面";
						  String title="登录成功！";
						  a2.showMessageDialog(container,msg,title,JOptionPane.INFORMATION_MESSAGE);
						//提示框
							}
					  stmt.close();
					  dbConn.close();
			 } catch (Exception e1) {
					e1.printStackTrace();
					System.out.print("连接失败");
				  }}});
left.addActionListener(new ActionListener() {//实现登录事件
	@Override
	public void actionPerformed(ActionEvent e) {
		guanli.setVisible(false);
		new Register();
	}
	});
}}


