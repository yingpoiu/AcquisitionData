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
	static Container container;//����һ����̬��ʾ������
	private static int center;
	private JFrame guanli;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UserLoginTest user=new UserLoginTest();
		user.login();//ʵ�������ķ���

}


	@Override
	public void login() {
		guanli=new JFrame("��������������Ĺ���Ա��¼����");//���
		guanli.setSize(300, 200);//��ܵĳ���
		//guanli.setLayout(new GridLayout(3, 3));//���񲼾� 3��3��
		JLabel text0=new JLabel("�û���¼",center);
		text0.setFont(new Font("����",15,30));//
		text0.setForeground(Color.blue);
		JLabel text=new JLabel("�û�",center);//���ɱ༭�ı�ǩ �û� ������ʾ
		final JTextField b=new JTextField(14);//�ɱ༭���ı��� �����û���
		JLabel text1=new JLabel("����",center);//���ɱ༭�ı�ǩ �û� ������ʾ
		final JPasswordField b1=new JPasswordField(14);//�ɱ༭���ı��� �����û���
		SpringLayout layout = new SpringLayout();
		guanli.setLayout(layout);//���ò���
		JButton left=new JButton("ע��");//��ť �����¼
		guanli.add(left);
		
		JButton right=new JButton("��¼");//���ȡ��
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
	     // ���õ�һ����������������λ��we
			layout.putConstraint(SpringLayout.WEST, right, 70, SpringLayout.WEST,left);
			layout.putConstraint(SpringLayout.NORTH, right, 30, SpringLayout.NORTH, text1);
		final JOptionPane a2=new JOptionPane();//Container���������,��ʾ�����
		
		guanli.add(text0);
		guanli.add(text);//������
		guanli.add(b);
		guanli.add(text1);
		guanli.add(b1);
		
		guanli.setBackground(null);
		guanli.setLocationRelativeTo(null);//����Ļ�м���ʾ(������ʾ) 
		guanli.setVisible(true);//��ʾ��ܲ���
		  
        guanli.setResizable(false); //�������� 
        guanli.setDefaultCloseOperation(3);//�ر�Ӧ�ó���
		right.addActionListener(new ActionListener() {//ʵ�ֵ�¼�¼�
			@Override
			public void actionPerformed(ActionEvent e) {
				
				 String dbURL = "jdbc:oracle:thin:@127.0.0.1:1521:XE";	// �����ַ���
					String userName = "envir";	String userPwd = "envir";
					
					
					try{
						Class.forName("oracle.jdbc.driver.OracleDriver");
						Connection dbConn = DriverManager.getConnection(dbURL, userName, userPwd);
						Statement  stmt = dbConn.createStatement();//�������ݿ�
				  String c=b.getText();//��������û���������
				  String c1=b1.getText();//����������븳����
				  
				    String sql1="SELECT * FROM users where name='"+c+"' and password='"+c1+"'";
				    ResultSet rs=stmt.executeQuery(sql1);//ִ�в�ѯ���
				    int d=0;
					  while (rs.next()) {
						  String no = rs.getString("name");//ƥ������
						  String bc=rs.getString("password");
						  d++;
						  }
					  if(d==0){//����ƥ��Ĵ����ж��û��Ƿ����
						  String msg="�û������ڻ������������������";
						  String title="���������";
						  a2.showMessageDialog(container,msg,title,JOptionPane.INFORMATION_MESSAGE);
						//��ʾ��
					  }
					  else{
						 
						  String msg="�����ѯ����";
						  String title="��¼�ɹ���";
						  a2.showMessageDialog(container,msg,title,JOptionPane.INFORMATION_MESSAGE);
						//��ʾ��
							}
					  stmt.close();
					  dbConn.close();
			 } catch (Exception e1) {
					e1.printStackTrace();
					System.out.print("����ʧ��");
				  }}});
left.addActionListener(new ActionListener() {//ʵ�ֵ�¼�¼�
	@Override
	public void actionPerformed(ActionEvent e) {
		guanli.setVisible(false);
		new Register();
	}
	});
}}


