package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

public class SearchFrame extends JFrame {
	private JScrollPane scrollPane;
	private PlayFrame playFrame;
	private String[] colunNames = { "序号", "名称", "类型", "上传时间", "时长", "大小",
			"下载次数" };

	public SearchFrame() {
		// TODO Auto-generated constructor stub
		Container container = this.getContentPane();
		// 搜索panel
		JPanel upPanel = new JPanel();
		upPanel.setLayout(new FlowLayout());
		upPanel.add(new JLabel("标签查询"));
		JTextField jTextField = new JTextField("多个标签以空格分开   ");
		
		upPanel.add(jTextField);
		upPanel.add(new JButton("搜索"));
		// 视频列表
		scrollPane = new JScrollPane();

		Object[][] results = { { "1", "", "", "", "", "", "" } };
		JTable jTable = new JTable(results, colunNames);

		scrollPane.setViewportView(jTable);
		// 操作
		JPanel bottomPanel=new JPanel();
		bottomPanel.setLayout(new FlowLayout());
		JButton playButton=new JButton("播放");
		JButton download=new JButton("下载");
		JButton delete=new JButton("删除");
		playButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
			playFrame=new PlayFrame();
			playFrame.setVisible(true);
				
			}
		});
		download.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser file=new JFileChooser();
				file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);//只能选择目录
				int resule=file.showOpenDialog(new JPanel());
				 if(resule==file.APPROVE_OPTION){
				       
				       String dir=file.getSelectedFile().getAbsolutePath();
				       JOptionPane.showConfirmDialog(null,dir+"\\","选择的文件",JOptionPane.YES_OPTION);
				      }
			}
		});
		delete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		bottomPanel.add(playButton);
		bottomPanel.add(download);
		bottomPanel.add(delete);
		container.add(upPanel, BorderLayout.NORTH);
		container.add(scrollPane, BorderLayout.CENTER);
		container.add(bottomPanel,BorderLayout.SOUTH);
		setSize(600, 450);
		setTitle("视频查询 ");
		

		setLocation(200, 150);
		
	}

}
