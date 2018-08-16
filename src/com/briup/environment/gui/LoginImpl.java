package com.briup.environment.gui;

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import com.briup.gui.FrameTest2;

public class LoginImpl implements Login{
	//��¼ͼƬ·��
	private String path="src/a.jpg";
	//��¼ҳ�汳��ͼƬ·��
	private String path2="src/background.jpg";

	Dialog dig;
	@Override
	public void login() throws Exception {
		final Frame f = new Frame("�û� Login!");
		f.setBackground(new Color(235, 242, 249));// ���ô��屳����ɫ
		f.setLocation(300, 200);//���ֵĿ�ʼƫ����
		/*
		 * javaĬ��ΪflowLayout���ֵģ�����Ϊnull��Ϊ��ղ��ֹ�����
		 * ֮��������������������������Ͻ�����������������Ͻ�
		 * ��0��0����x,yֵ��ȷ�������λ�ã���ʹ����������СҲ����
		 * �ı�λ�á����ַ�ʽ�������ڴ����С�̶���������
		 */
		f.setLayout(null); // �ֹ�����
		f.setSize(420, 330); // ���ô��ڵĴ�С
		JLabel labelImage = new JLabel(new ImageIcon(path));// ���ͼƬ
		labelImage.setSize(80, 80);// ����ͼƬ�Ĵ�С
		labelImage.setLocation(40, 190);// ����ͼƬ�ľ���λ��
		JLabel ac = new JLabel("ע���˺�");
		ac.setForeground(Color.blue);// ����������ɫ
		ac.setSize(60, 30);// ��С
		ac.setLocation(335, 195);// ����λ��
		Label pwd = new Label("�һ�����");
		pwd.setForeground(Color.blue);// ������ɫ
		pwd.setSize(60, 30);// ��С
		pwd.setLocation(335, 225);// ����λ��
		final TextField username = new TextField();
		username.setSize(195, 25);// ��С
		username.setLocation(130, 195);// ����λ��
		final JPasswordField password = new JPasswordField();
		password.setSize(195, 30);// ��С
		password.setLocation(130, 225);// ����λ��
		Checkbox cb1 = new Checkbox("��ס����");
		cb1.setSize(75, 20);// ��С
		cb1.setLocation(130, 260);// ����λ��
		Checkbox cb2 = new Checkbox("�Զ���¼");
		cb2.setSize(75, 20);// ��С
		cb2.setLocation(260, 260);// ����λ��
		JButton jbt = new JButton();
		jbt.setText("��¼");
		jbt.setSize(195, 30);// ��С
		jbt.setLocation(130, 285);// λ��
		JLabel reportPane = new JLabel(new ImageIcon(path2));// ���ͼƬ
		reportPane.setSize(420, 170);//��С
		reportPane.setLocation(0, 0);//λ��
		f.add(reportPane);//������
		f.add(ac);
		f.add(pwd);
		f.add(jbt);
		f.add(username);
		f.add(password);
		f.add(labelImage);
		f.add(cb1);
		f.add(cb2);
		f.setVisible(true);// ���ÿɼ�
		/*
		 * ������ע��һ���¼�������,������˴������Ϸ���
		 * ���ʱ,�˷���������.������Ҫ�˳������,��������ť
		 */
		f.addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent e) {
					dig = new Dialog(f, "ȷ���˳�", true);
			    	Panel p = new Panel();
			    	Button yes = new Button("��");
			    	Button no = new Button("��");
			    	p.add(yes);
			    	p.add(no);
			    	//���ô��������ָ�������λ��,null��˴��ڽ�������Ļ������
			    	dig.setLocationRelativeTo(null);
			    	yes.addActionListener(new ActionListener() {// �ڲ�����ʽע�ἰ��д�¼�������
				    	public void actionPerformed(ActionEvent e) {
				    		//�رմ���,�ͷ���Դ
				    		f.dispose();
				    	}
			    	});
			    	no.addActionListener(new ActionListener() {
			    		public void actionPerformed(ActionEvent e) {
			    			dig.dispose();
			    		}
			    	});
			    	dig.add(new Label("��Ҫ�˳���"));
			    	dig.add(p, "South");
			    	dig.setSize(200, 100);
			    	dig.setVisible(true);
				}
		});
		/*
		 * ���������,�����ע������ʱ,���´���һ������
		 * ���ҽ�ԭ���Ĳ����ͷ�
		 */
		ac.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseClicked(MouseEvent arg0) {
				Register registLayout = new Register();
				f.dispose();
			}
		});
		/*
		 * ��¼��ťע�����,����û�����������ȷ,��ת��
		 * ������������������ڵ�ǰҳ��
		 */
		jbt.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				UserDao dao = new UserDaoImpl();
				try {
					User user = dao.find(username.getText());
					if(user!=null) {
						if(user.getPwd().equals(password.getText())) {
							/*
							 * ��Ϣ��ʾ��
							 * ��һ�������ǿ��Ƶ����Ի�����Ե�����λ�ã������null����������Ļ�м�
							 * �ڶ��������������������Ϣ
							 * ����������������ı�����Ϣ
							 * ���ĸ����������ʱ�����ʾ��ͼ����ʽ
							 */
							JOptionPane.showMessageDialog(null, "��ϲ��,��¼�ɹ�", "��Ϣ��ʾ��",JOptionPane.PLAIN_MESSAGE);
							MainLayout ml=new MainLayout();//Ϊ��ת�Ľ���
							f.dispose();
						}else {
							JOptionPane.showMessageDialog(null, "�û������������", "��Ϣ��ʾ��", JOptionPane.ERROR_MESSAGE);
						}
					}else {
						JOptionPane.showMessageDialog(null, "���û������ڣ�", "��Ϣ��ʾ��", JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
	}
	public static void main(String[] args) throws Exception {
		Login login=new LoginImpl();
		login.login();
	}

}
