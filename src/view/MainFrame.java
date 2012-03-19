package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {
	private SearchFrame searchFrame;
	private UpdateFrame manaFrame;

	public MainFrame() {

		Container c = this.getContentPane();

		JPanel jPanel = new JPanel();
		JButton button1 = new JButton("视频查询");
		JButton button2 = new JButton("视频上传");
		button1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				searchFrame = new SearchFrame();
				searchFrame.setVisible(true);
			}
		});
		button2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				manaFrame = new UpdateFrame();
				manaFrame.setVisible(true);
			}
		});
		jPanel.add(button1);

		jPanel.add(button2);
		c.add(jPanel);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				dispose();
				System.exit(0);
				
			}

		});
       
		setSize(800, 700);
		setTitle("基于hadoop的视频管理系统");

		setLocation(200, 150);
	}

	public static void main(String[] args) {
		new MainFrame().setVisible(true);
	}
}
