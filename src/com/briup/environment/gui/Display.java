package com.briup.environment.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.briup.environment.util.Configuration;

public class Display {
	private static String driverString="oracle.jdbc.driver.OracleDriver";
	 private static String dbURL = "jdbc:oracle:thin:@127.0.0.1:1521:XE";	// 连接字符串
	 private static String userName = "envir";	
	 private static String userPwd = "envir";
	 private Configuration configuration;

	public static void main(String[] args) throws Exception {
		final JFrame frame =  new JFrame("物联网数据中心");
        frame.setDefaultCloseOperation(3);
		final DefaultTableModel model=new DefaultTableModel();
		model.setColumnIdentifiers(new Object[]{"环境名称","发送端id ","树莓派id","传感器地址","传感器个数","指令标号","状态标示","环境值","采集时间"});
		
				Class.forName(driverString);
				Connection dbConn = DriverManager.getConnection(dbURL, userName, userPwd);
				Statement  stmt = dbConn.createStatement();
				
				//for(int i=1;i<32;i++){
				String sql="select * from e_detail_24";
				//+i+"";
				ResultSet rSet=stmt.executeQuery(sql);
				while(rSet.next())
				{
				String dt=rSet.getString("name");
				String same=rSet.getString("srcId");
				String dname=rSet.getString("dstId");
				String sename=rSet.getString("sersorAddress");
				int count=rSet.getInt("count");
				String cnaString=rSet.getString("cmd");
				int j=rSet.getInt("status");
				float data=rSet.getFloat("data");
				Timestamp gather_date=rSet.getTimestamp("gather_date");
				model.addRow(new Object[]{dt,same,dname,sename,count,cnaString,j,data,gather_date});
				}
				//}
				
			
				JTable table=new JTable(model);
				JScrollPane pane = new JScrollPane(table);
				//frame.add(label,BorderLayout.NORTH);
		        frame.add(pane, BorderLayout.CENTER);
		         frame.setSize(800, 400);
		         frame.setVisible(true);
		        final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
                 table.setRowSorter(sorter);
		         JPanel panel = new JPanel(new FlowLayout());
		         Label label =  new Label("物联网数据控制中心");
		         panel.add(label);
		         JPanel panel2=new JPanel();
		         final JTextField filterText = new JTextField(5);
		         panel2.add(new Label("按日期查询"));
		         panel2.add(filterText, BorderLayout.CENTER);
		         frame.add(panel, BorderLayout.NORTH);
		         frame.add(panel2,BorderLayout.WEST);
		        
		         JButton button =  new JButton("查询");
		         frame.add(button, BorderLayout.SOUTH);
		         button.addActionListener(new ActionListener() {//实现查询事件
		 			@Override
		 			public void actionPerformed(ActionEvent e) {
		 				
		 				
		 					String text = filterText.getText();
		 	                 if (text.length() == 0)
		 	                 {
		 	                     sorter.setRowFilter(null);
		 	                 }
		 	                 else
		 	                 {
		 	                     sorter.setRowFilter (RowFilter.regexFilter(text));
		 	                 }
		 			}
		 				
		 			
});
		         
		        
	
}
}
