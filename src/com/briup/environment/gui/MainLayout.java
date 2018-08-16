package com.briup.environment.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MainLayout extends JFrame implements ActionListener {
	 private JButton searchButton;//��ѯ��ť
	    private DefaultTableModel defaultModel = null;//Ĭ�ϵı����ģ�� ������������ JTBALE 
	    private JTable table = null;//��ʾ�ͱ༭�����ά��Ԫ��
	    private static Connection conn ;//ͨ�����ӹ����������ݿ����Ӷ���
	    private Statement statement = null;//����statement
	    private ResultSet rs = null;//���������
	    private String time = null;//������ʱ��ֵ
	    private String envir = null;//�����򻷾�����ֵ
	    private JScrollPane s = null;//������
	    JComboBox comboBox = null;//ʱ��������
	    JComboBox comboBox2 = null;//��������������
//	    Dialog dig = null;
	    JButton jb = null;//ͳ�����ݰ�ť��
	    //��ʼ�����岼��
	    static{
	    	String driver="oracle.jdbc.driver.OracleDriver";
	    	String url="jdbc:oracle:thin:@127.0.0.1:1521:XE";
	    	String username="envir";
	    	String pwd="envir";
	    	try {
				Class.forName(driver);
				conn=DriverManager.getConnection(url,username,pwd);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    public MainLayout() {
	    	setTitle("�û���ѯ");
	    	setBounds(100, 100, 650, 600);
	    	setResizable(false);
	    	setDefaultCloseOperation(EXIT_ON_CLOSE);
	    	init();
	    	setVisible(true);
	    }
	    //��ʼ���������
	    public void init() {
	         JLabel label=new JLabel("��ѯ����:");
	         comboBox=new JComboBox();  //������
	        comboBox.addItem("19��");  
	        comboBox.addItem("20��");  
	        comboBox.addItem("21��");  
	        comboBox.addItem("22��");  
	        comboBox.addItem("23��");  
	        comboBox.addItem("24��");  
	        comboBox.addItem("25��");  
	        comboBox.addItem("26��");  
	        comboBox.addItem("29��");  
	         JLabel label2=new JLabel("��������:"); 
	         comboBox2=new JComboBox(); 
	        comboBox2.addItem("�¶�");
	        comboBox2.addItem("ʪ��");
	        comboBox2.addItem("����ǿ��");
	        comboBox2.addItem("������̼");
	        searchButton = new JButton("��ѯ");
	        jb = new JButton("ͳ������");
	        JPanel north = new JPanel();
	        north.add(label);
	        north.add(comboBox);
	        north.add(label2);
	        north.add(comboBox2);
	        north.add(searchButton);
	        north.add(jb);
	        add(north, BorderLayout.NORTH);
	        //������ͷ
	        String[] name = {"����","���ͷ�id","��ݮ��id","��������ַ","����","ʱ��"};
	        //������ʾ����
	        String[][] data = new String[0][6];
	    	/*
	    	 * ����һ��Ĭ�ϵı��ģ��,���� TableModel ��һ��ʵ��
	    	 * ��ʹ��һ�� Vector ���洢��Ԫ���ֵ���󣬸� Vector 
	    	 * �ɶ�� Vector(�̰߳�ȫ,��arrayList����.Ч�ʵ�,��ȫ) ���
	    	 * DefaultTableModel(Object[][] data, Object[] columnNames) 
	    	 * ����һ�� DefaultTableModel
	    	 */
	    	defaultModel = new DefaultTableModel(data,name);
	    	/*
	    	 * JTable����չʾ�ͱ༭���ɵ�ƽ����
	    	 * setPreferredScrollableViewportSize���ô˱��ӿڵ���ѡ��С��
	    	 */
	    	table=new JTable(defaultModel);
	     	table.setPreferredScrollableViewportSize(new Dimension(400, 80));
	        /*
	         *  Create the scroll pane and add the table to it.
	         *  ��Ҳ�ǹٷ�����ʹ�õķ�ʽ�������ͷ������ʾ��
	         *  ��Ҫ������ȡ��TableHeader�Լ��ֶ��������ʾ
	         *  ���˱������ģ������ΪdefaultModel��������ע
	         *  ���Ի�ȡ����������ģ�͵�������֪ͨ
	         *  ����:defaultModel - �˱��������Դ��
	         *  TableModel �ӿ�ָ���� JTable ����ѯ�ʱ��ʽ��
	         *  ��ģ�͵ķ����������˱��Ҫչʾ�����ݣ�������
	         *  �Ƿ������޸ģ����ݸ��µļ�����
	         */
	     	s = new JScrollPane(table);
	     	table.setModel(defaultModel);
	     	table.setBackground(Color.yellow);//��ʾ���ݵı�����ɫ
	     	add(new JScrollPane(s));
	     	/*
	     	 * �ֱ��ʱ��������,��������������,��ѯ��ť,ͳ�ư�ť
	     	 * ����¼�����
	     	 */
	     	searchButton.addActionListener(this);
	     	comboBox.addActionListener(this);
	     	comboBox2.addActionListener(this);
	     	jb.addActionListener(this);
	    }
	    
	    public static void main(String[] args) {
	        new MainLayout();
	    }
	    
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			if(source == jb) {
				//��ȡ������ʱ��ֵ,��������ֵ
				time = (String) comboBox.getSelectedItem();
	        	envir = (String) comboBox2.getSelectedItem();
	        	try {
	        		//����statement����
					statement = conn.createStatement();
					//ʱ��ֵ�õ��ǰ���'��'.����16��,��Ҫ��ȡ16
					time = time.substring(0,2);
					//sql����ѯ����,���ֵ,��Сֵ,ƽ��ֵ,������������,�������ƽ��з����ٲ�ѯ
					String sql = "select count(*),max(data),min(data),avg(data),name from e_detail_"
							+time+" where name = '"+envir+"' group by name";
					System.out.println(sql);
					//ִ��sql���
					rs=statement.executeQuery(sql);
					 /*
					  * ����StringBuffer,���õ�������,���ֵ,��Сֵ,ƽ��ֵ׷�ӵ�
					  * StringBuffer������,ͨ��\n���д���
					  */
					StringBuffer sb = null;
	    			while(rs.next()){
	    				float count = rs.getFloat(1);
	    				float max = rs.getFloat(2);
	    				float min = rs.getFloat(3);
	    				float avg = rs.getFloat(4);
	    				 sb=new StringBuffer();
	    				   sb.append(envir+time+"������Ϊ\n����:").append(count).append("\n���ֵ:")
	    				   .append(max).append("\n��Сֵ:").append(min).append("\nƽ��ֵ:").append(avg);
	    			}
	    			//��ʾ����ʾ��ȡ��������
	    			JOptionPane.showMessageDialog(this, sb.toString());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			if(source == searchButton) {
				/*
				 * ��ɾ�����������,ʵ��ˢ��Ч��
				 * getDataVector()����һ��java.util.Vector����
				 * �ٵ���removeAllElements()�Ӵ��������Ƴ�ȫ
				 * ��������������С����Ϊ�㡣 �˷����Ĺ���
				 * �� clear() �����Ĺ�����ͬ.
				 * void clear():Removes all of the elements from this Vector.
				 * void removeAllElements():Removes all components from this vector and sets its size to zero.
				 * 	removeAllElements()������һ�㡣
				 */
				defaultModel.getDataVector().removeAllElements();
				time = (String) comboBox.getSelectedItem();
	        	envir = (String) comboBox2.getSelectedItem();
	        	try {
	    			statement = conn.createStatement();
	    			time = time.substring(0,2);
	    			/*
	    			 * ��Ҫ��ʾ��ʱ�������,��Ҫʹ��to_char���и�ʽ����.����
	    			 * ��ѯ����ʱ��ֻ��������.
	    			 * ���û������,��ʾ�û�.���ҷ���
	    			 * ���������.û����һ�����ݹ���һ��Vector����
	    			 * ��ӵ�defaultModel��
	    			 */
	    			String sql = "select name,srcid,dstid,sersoraddress,data,to_char(gather_date,'mm-dd hh24:mm:ss') from e_detail_"
	    						+time+" where name = '"+envir+"'";
	    			rs=statement.executeQuery(sql);
	    			if(!rs.next()) {
	    				JOptionPane.showMessageDialog(null, "����:"+envir+time+"��û������!", "��Ϣ��ʾ��", JOptionPane.ERROR_MESSAGE);
	    				return;
	    			}
	            	while(rs.next()){
	    	            	Vector v=new Vector();
	    	            	v.addElement(rs.getObject(1));
	    	            	v.addElement(rs.getObject(2));
	    	            	v.addElement(rs.getObject(3));
	    	            	v.addElement(rs.getObject(4));
	    	            	v.addElement(rs.getObject(5));
	    	            	v.addElement(rs.getObject(6)); 
	    	            	System.out.println(v);
	    	            	defaultModel.addRow(v);
	                }
	        	} catch (SQLException e1) {
	    			e1.printStackTrace();
	    		}
			}
		}
}
