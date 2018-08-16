package com.briup.environment.gui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.briup.environment.util.Configuration;

public class UserDaoImpl implements UserDao{
	private static Connection con;
	static{
		String driverString="oracle.jdbc.driver.OracleDriver";
		String dbURL = "jdbc:oracle:thin:@127.0.0.1:1521:XE";	// Á¬½Ó×Ö·û´®
		String userName = "envir";	
		String userPwd = "envir";
			try {
				Class.forName(driverString);
				con =  DriverManager.getConnection(dbURL, userName, userPwd);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}

	@Override
	public void save(User user) throws Exception {
		String sql="insert into userss values(userss_id.nextval,?,?,?,?)";
		PreparedStatement pStatement=con.prepareStatement(sql);
		pStatement.setString(1, user.getUsername());
		pStatement.setString(2, user.getPwd());
		pStatement.setString(3, user.getGender());
		pStatement.setString(4, user.getInfo());
		pStatement.executeUpdate();
		
	}

	@Override
	public User find(String name) throws Exception {
		String sqlString="select * from userss where username=?";
		PreparedStatement pStatement=con.prepareStatement(sqlString);
		pStatement.setString(1, name);
		ResultSet rSet=pStatement.executeQuery();
		User user=null;
		while(rSet.next()){
			user=new User();
			user.setUsername(rSet.getString("username"));
			user.setPwd(rSet.getString("pwd"));
			user.setGender(rSet.getString("gender"));
			user.setInfo(rSet.getString("info"));
		}
		return user;
	
	}

}
