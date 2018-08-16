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
	//登录图片路径
	private String path="src/a.jpg";
	//登录页面背景图片路径
	private String path2="src/background.jpg";

	Dialog dig;
	@Override
	public void login() throws Exception {
		final Frame f = new Frame("用户 Login!");
		f.setBackground(new Color(235, 242, 249));// 设置窗体背景颜色
		f.setLocation(300, 200);//布局的开始偏移量
		/*
		 * java默认为flowLayout布局的，设置为null即为清空布局管理器
		 * 之后添加组件，常常是设置组件左上角坐标相对于容器左上角
		 * （0，0）的x,y值来确定组件的位置，即使更改容器大小也不会
		 * 改变位置。这种方式常常用于窗体大小固定的容器里
		 */
		f.setLayout(null); // 手工布局
		f.setSize(420, 330); // 设置窗口的大小
		JLabel labelImage = new JLabel(new ImageIcon(path));// 添加图片
		labelImage.setSize(80, 80);// 设置图片的大小
		labelImage.setLocation(40, 190);// 设置图片的绝对位置
		JLabel ac = new JLabel("注册账号");
		ac.setForeground(Color.blue);// 设置字体颜色
		ac.setSize(60, 30);// 大小
		ac.setLocation(335, 195);// 绝对位置
		Label pwd = new Label("找回密码");
		pwd.setForeground(Color.blue);// 字体颜色
		pwd.setSize(60, 30);// 大小
		pwd.setLocation(335, 225);// 绝对位置
		final TextField username = new TextField();
		username.setSize(195, 25);// 大小
		username.setLocation(130, 195);// 绝对位置
		final JPasswordField password = new JPasswordField();
		password.setSize(195, 30);// 大小
		password.setLocation(130, 225);// 绝对位置
		Checkbox cb1 = new Checkbox("记住密码");
		cb1.setSize(75, 20);// 大小
		cb1.setLocation(130, 260);// 绝对位置
		Checkbox cb2 = new Checkbox("自动登录");
		cb2.setSize(75, 20);// 大小
		cb2.setLocation(260, 260);// 绝对位置
		JButton jbt = new JButton();
		jbt.setText("登录");
		jbt.setSize(195, 30);// 大小
		jbt.setLocation(130, 285);// 位置
		JLabel reportPane = new JLabel(new ImageIcon(path2));// 添加图片
		reportPane.setSize(420, 170);//大小
		reportPane.setLocation(0, 0);//位置
		f.add(reportPane);//添加组件
		f.add(ac);
		f.add(pwd);
		f.add(jbt);
		f.add(username);
		f.add(password);
		f.add(labelImage);
		f.add(cb1);
		f.add(cb2);
		f.setVisible(true);// 设置可见
		/*
		 * 给窗口注册一个事件监听器,当你点了窗口右上方的
		 * 叉号时,此方法被调用.出现想要退出吗和是,否两个按钮
		 */
		f.addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent e) {
					dig = new Dialog(f, "确认退出", true);
			    	Panel p = new Panel();
			    	Button yes = new Button("是");
			    	Button no = new Button("否");
			    	p.add(yes);
			    	p.add(no);
			    	//设置窗口相对于指定组件的位置,null则此窗口将置于屏幕的中央
			    	dig.setLocationRelativeTo(null);
			    	yes.addActionListener(new ActionListener() {// 内部类形式注册及重写事件处理方法
				    	public void actionPerformed(ActionEvent e) {
				    		//关闭窗体,释放资源
				    		f.dispose();
				    	}
			    	});
			    	no.addActionListener(new ActionListener() {
			    		public void actionPerformed(ActionEvent e) {
			    			dig.dispose();
			    		}
			    	});
			    	dig.add(new Label("想要退出吗？"));
			    	dig.add(p, "South");
			    	dig.setSize(200, 100);
			    	dig.setVisible(true);
				}
		});
		/*
		 * 添加鼠标监听,当点击注册字体时,重新创建一个布局
		 * 并且将原来的布局释放
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
		 * 登录按钮注册监听,如果用户名和密码正确,跳转到
		 * 环境控制主界面否则还在当前页面
		 */
		jbt.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				UserDao dao = new UserDaoImpl();
				try {
					User user = dao.find(username.getText());
					if(user!=null) {
						if(user.getPwd().equals(password.getText())) {
							/*
							 * 消息提示框
							 * 第一个参数是控制弹出对话框相对的中心位置，如果是null，则是在屏幕中间
							 * 第二个参数是输出的内容信息
							 * 第三个参数是输出的标题信息
							 * 第四个参数是输出时左边显示的图表样式
							 */
							JOptionPane.showMessageDialog(null, "恭喜您,登录成功", "信息提示！",JOptionPane.PLAIN_MESSAGE);
							MainLayout ml=new MainLayout();//为跳转的界面
							f.dispose();
						}else {
							JOptionPane.showMessageDialog(null, "用户名或密码错误！", "信息提示！", JOptionPane.ERROR_MESSAGE);
						}
					}else {
						JOptionPane.showMessageDialog(null, "该用户不存在！", "信息提示！", JOptionPane.ERROR_MESSAGE);
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
