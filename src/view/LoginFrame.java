package view;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;



public class LoginFrame extends JFrame {
	private MainFrame mainFrame;

	public LoginFrame() {
		Container container = this.getContentPane();
		GridLayout layout = new GridLayout(15, 10);

		container.setLayout(layout);

		JPanel panel1 = new JPanel();
		panel1.add(new JLabel("帐号 "));
		final JTextField name = new JTextField(6);
		panel1.add(name);
		container.add(panel1);

		JPanel panel2 = new JPanel();
		panel2.add(new JLabel("密码"));
		final JTextField passw = new JTextField(6);
		panel2.add(passw);
		container.add(panel2);

		JPanel panel3 = new JPanel();
		JButton j1 = new JButton("登陆");
		JButton j2 = new JButton("退出");
		j1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (name.getText().equals("admin")
						&& passw.getText().equals("123")) {

					mainFrame = new MainFrame();
					LoginFrame.this.setVisible(false);
					mainFrame.setVisible(true);
				} else {
				}
			}
		});

		j2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// System.exit(0);
			
			}
		});
		panel3.add(j1);
		panel3.add(j2);
		container.add(panel3);
		setSize(800, 700);
		setTitle("基于hadoop的视频管理系统");

		setLocation(200, 150);
	}

}
