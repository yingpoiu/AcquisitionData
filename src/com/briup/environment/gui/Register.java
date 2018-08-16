package com.briup.environment.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import com.briup.environment.util.Configuration;
import com.briup.environment.util.ConfigurationImpl;
import com.briup.environment.util.EmdcModule;

public class Register extends JFrame implements ActionListener{
	
	//ע�ᴥ���¼���
		 private static final String ADD     = "ADD";
		 //ȡ�������¼���
		 private static final String CANCEL    = "CANCEL";
		 private JLabel    name_lab;//�����ı�
		 private JLabel    pwd_lab;//�����ı�
		 private JLabel    sex_lab;//�Ա��ı�
		 private JRadioButton  man;//��
		 private JRadioButton  woman;//Ů
		 private JLabel    jLabel0;//�����ı�
		 private JTextArea   info_ta;//������������
		 private JScrollPane   jScrollPane0;//��һ��������
		 private JTextField   name_jf;//���������
		 private JTextField   pwd_jf;//���������
		 private JButton    add;//ע�ᰴť
		 private JButton    cancel;//ȡ����ť
		 private ButtonGroup bg=new ButtonGroup();//��ť��
		 
		 public static void main(String[] args){
			 new Register();
		 }
		 
		 //��ʼ������
		 private void initComponents() {
			  setTitle("ע��");
			  setLayout(null);
			  add(getName_lab());
			  add(getPwd_lab());
			  add(getSex_lab());
			  add(getMan());
			  add(getWoman());
			  add(getJLabel0());
			  add(getJScrollPane0());
			  add(getName_jf());
			  add(getPwd_jf());
			  add(getAdd());
			  add(getCancel());
			  setSize(316, 320);
			  setLocation(300, 200);
			  setVisible(true);
			  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			  this.setResizable(false);
		 }
		 //��ʼ��ȡ����ť
		 private JButton getCancel(){
			 if (cancel == null){
			   cancel = new JButton();
			   cancel.setText("ȡ��");
			   cancel.setActionCommand(CANCEL);
			   cancel.setBounds(177, 203, 81, 28);
			   cancel.addActionListener(this);
			 }
			 return cancel;
		 }
		 //��ʼ��ע�ᰴť
		 private JButton getAdd(){
			 if (add == null){
			   add = new JButton();
			   add.setText("ע��");
			   add.setActionCommand(ADD);
			   add.setBounds(69, 203, 81, 28);
			   add.addActionListener(this);
			 }
			 return add;
		 }
		 //��ʼ�����������
		 private JTextField getPwd_jf(){
			 if (pwd_jf == null){
			   pwd_jf = new JTextField();
			   pwd_jf.setText("");
			   pwd_jf.setBounds(61, 41, 178, 22);
			 }
			 return pwd_jf;
		 }
		 //��ʼ�����������
		 private JTextField getName_jf(){
			 if (name_jf == null){
			   name_jf = new JTextField();
			   name_jf.setText("");
			   name_jf.setBounds(62, 10, 178, 22);
			}
			 return name_jf;
		 }
		 //��ʼ��������
		 private JScrollPane getJScrollPane0(){
			 if (jScrollPane0 == null){
				 jScrollPane0 = new JScrollPane();
				 jScrollPane0.setBounds(63, 103, 210, 80);
				 //����������ӵ���������
				 jScrollPane0.setViewportView(getInfo_ta());
			 }
			 return jScrollPane0;
		 }
		 //��ʼ����������
		 private JTextArea getInfo_ta(){
			  if (info_ta == null){
			   info_ta = new JTextArea();
			   info_ta.setText("");
			  }
			  return info_ta;
		 }
		 //��ʼ�������ı�
		 private JLabel getJLabel0(){
			  if (jLabel0 == null){
			   jLabel0 = new JLabel();
			   jLabel0.setText("����");
			   jLabel0.setBounds(17, 99, 41, 18);
			  }
			  return jLabel0;
		 }
		 //��ʼ��Ů��ť,������ӵ���ť����
		 private JRadioButton getWoman() {
			  if (woman == null) {
			   woman = new JRadioButton();
			   bg.add(woman);
			   woman.setSelected(true);
			   woman.setText("Ů");
			   woman.setBounds(105, 65, 45, 26);
			  }
			  return woman;
		 }
		 //��ʼ���а�ť,������ӵ���ť����
		 private JRadioButton getMan(){
			  if (man == null){
			   man = new JRadioButton();
			   bg.add(man);
			   man.setText("��");
			   man.setBounds(58, 66, 47, 26);
			  }
			  return man;
		 }
		 //��ʼ���Ա��ı�
		 private JLabel getSex_lab(){
			  if (sex_lab == null){
			   sex_lab = new JLabel();
			   sex_lab.setText("�Ա�");
			   sex_lab.setBounds(16, 70, 41, 18);
			  }
			  return sex_lab;
		 }
		 //��ʼ�������ı�
		 private JLabel getPwd_lab(){
			  if (pwd_lab == null){
			   pwd_lab = new JLabel();
			   pwd_lab.setText("����");
			   pwd_lab.setBounds(14, 42, 41, 20);
			  }
			  return pwd_lab;
		 }
		 //��ʼ�������ı�
		 private JLabel getName_lab(){
			  if (name_lab == null){
			   name_lab = new JLabel();
			   name_lab.setText("����");
			   name_lab.setBounds(15, 11, 41, 20);
			  }
			  return name_lab;
		 }
		 public Register(){
			 initComponents();
		 }
		 /*
		  * ���ڽ��ղ����¼����������ӿڡ��Դ�������¼���
		  * ��Ȥ�������ʵ�ִ˽ӿڣ���ʹ�ø��ഴ���Ķ����
		  * ʹ������� addActionListener ����������ע�ᡣ��
		  * ���������¼�ʱ�����øö���� actionPerformed ������
		  */
		 public void actionPerformed(ActionEvent e){
			  String action = e.getActionCommand();
//			  System.out.println(action);
			  //ע�ᰴť�����¼�
			  if (action != null && ADD.equals(action)){
				  UserDao dao = new UserDaoImpl();
				  User u;
				  try {
					  u = dao.find(name_jf.getText());
					  if(u==null) {
					   		User user = new User();
					   		user.setUsername(name_jf.getText());
					   		user.setPwd(pwd_jf.getText());
					   		if(woman.isSelected()){
					   			user.setGender("Ů");
					   		}else{
					   			user.setGender("��");
					   		}
					   		user.setInfo(info_ta.getText());
					   		dao.save(user);
					   		JOptionPane.showMessageDialog(null, "�û�ע��ɹ�", "��Ϣ��ʾ��", JOptionPane.INFORMATION_MESSAGE);
					   		//��Ҫͨ��configuration���󹹽�loginҳ��,��ҳ������������Ϣ
					   		//Configuration configuation = new ConfigurationImpl();
					   		//LoginImpl loginImpl = (LoginImpl) configuation.getLogin();
					   		new LoginImpl().login();
					   		dispose();
					 }else {
						 JOptionPane.showMessageDialog(null, "�û��Ѿ�����,������ע��", "��Ϣ��ʾ��", JOptionPane.ERROR_MESSAGE);
					 }
				  } catch (Exception e2) {
					  e2.printStackTrace();
				  }
				 //ȡ����ť�����¼�
			  }else if (action != null && action.equals(CANCEL)){
				   name_jf.setText("");
				   pwd_jf.setText("");
				   woman.setSelected(true);
				   man.setSelected(false);
				   info_ta.setText("");
			  }
		 }
		
}



