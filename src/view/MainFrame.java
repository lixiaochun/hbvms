package view;


import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


import javax.swing.JButton;
import javax.swing.JFrame;

public class MainFrame extends JFrame{
	JButton button1;//视频点播
	JButton button2;//视频管理
public MainFrame() {
	// TODO Auto-generated constructor stub
	button1=new JButton("视频点播");

	button2=new JButton("视频管理");
   Container container=this.getContentPane();
   container.setLayout(new FlowLayout());
   this.add(button1);
   this.add(button2);
	this.addWindowListener(new WindowAdapter() {
	 
		@Override
		public void windowClosing(WindowEvent arg0) {
			// TODO Auto-generated method stub
			dispose();
		   System.exit(0);
		}

	});
	this.setSize(700, 500);
	setTitle("基于hadoop的视频管理平台");
	setLocation(200, 150);
	this.show();
}
public static void main(String[] args) {
	new MainFrame();
	
}
}
