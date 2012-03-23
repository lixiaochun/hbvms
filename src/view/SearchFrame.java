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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


import control.DeleteControl;
import control.DownloadControl;
import control.SearchControl;

public class SearchFrame extends JFrame {
	private JScrollPane scrollPane; //结果 显示界面
	private PlayFrame playFrame;
	private String[] colunNames = { "序号", "名称", "类型", "上传时间", "时长", "大小",
			"下载次数" };
	private SearchControl searchControl;
 private JTextField jTextField;// 标签输入框
 private JTable jTable;// 搜索结果列表
 private Object[][] results = { { "", "", "", "", "", "", "" } };
 private String[] rowkeys;
 private String[] pathnames;
	public SearchFrame() {
		// TODO Auto-generated constructor stub
		Container container = this.getContentPane();
		// 搜索panel
		JPanel upPanel = new JPanel();
		upPanel.setLayout(new FlowLayout());
		
		upPanel.add(new JLabel("标签查询"));
		jTextField = new JTextField(10);
		
		upPanel.add(jTextField);
		JButton searchButton=new JButton("搜索");
		searchButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				searchControl=new SearchControl(jTextField.getText());
				results=searchControl.search();
				rowkeys=searchControl.getRowkeys();
				pathnames=searchControl.getPathname();
				DefaultTableModel model = new DefaultTableModel(results, colunNames) {
                   
					  public boolean isCellEditable(int row, int column) {
					    return false;
					  }
				
				};
				jTable=new JTable(model);
				scrollPane.setViewportView(jTable);
				
			}
		});
		upPanel.add(searchButton);
		
		// 视频列表
		scrollPane = new JScrollPane();

		
		 jTable = new JTable(results, colunNames);
		
		scrollPane.setViewportView(jTable);
		// 操作
		JPanel bottomPanel=new JPanel();
		bottomPanel.setLayout(new FlowLayout());
		JButton playButton=new JButton("播放");
		JButton download=new JButton("下载");
		JButton delete=new JButton("删除");
		//播放视频
		playButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
			playFrame=new PlayFrame();
			playFrame.setVisible(true);
				
			}
		});
		// 下载
		download.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser file=new JFileChooser();
				file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);//只能选择目录
				
				int resule=file.showOpenDialog(new JPanel());
				int selectRows=jTable.getSelectedRows().length;// 取得用户所选行的行数
				 TableModel tableModel = jTable.getModel();
				 if(selectRows==1){
				 int selectedRowIndex = jTable.getSelectedRow(); // 取得用户所选单行 
				 if(resule==file.APPROVE_OPTION){
				       
				       String des=file.getSelectedFile().getAbsolutePath();
//				       JOptionPane.showConfirmDialog(null,dir+"\\","选择的文件",JOptionPane.YES_OPTION);
				       new DownloadControl(pathnames[selectedRowIndex], des+"/"+results[selectedRowIndex][1]+"."+results[selectedRowIndex][2]).download();
				      }
			}
			}
		});
		//删除指定行
		delete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int selectRows=jTable.getSelectedRows().length;// 取得用户所选行的行数
				 TableModel tableModel = jTable.getModel();
				if(selectRows==1){
					  int selectedRowIndex = jTable.getSelectedRow(); // 取得用户所选单行  
					 // rowIndex是要删除的行序号
					  ((DefaultTableModel) tableModel).removeRow(selectedRowIndex);
//					  new DeleteControl(rowkeys[selectedRowIndex],pathnames[selectedRowIndex]);//在数据库中删除 
				}
				
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
