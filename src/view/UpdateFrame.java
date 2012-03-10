package view;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

import control.UploadControl;

public class UpdateFrame extends JFrame{
	private JTextField path;
	private  JTextField name;
	private JTextField tags;
	private JTextField time;
	private JTextField type;
public UpdateFrame() {
	// TODO Auto-generated constructor stub
	Container container = this.getContentPane();
	
	GridLayout layout=new GridLayout(8,2);
	
	container.setLayout(layout);
	 
	 //选择文件
	 JPanel panel1=new JPanel();
	 panel1.add(new JLabel("文件目录"));
	  path=new JTextField(10);
	  panel1.add(path);
	  JButton selectfile=new JButton("选择文件");
	  selectfile.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JFileChooser file=new JFileChooser();
			file.setFileFilter(new VideoFilter());
		      int resule=file.showOpenDialog(new JPanel());
		      if(resule==file.APPROVE_OPTION){
//		       String fileName=file.getSelectedFile().getName();
//		       String dir=file.getSelectedFile().getName();
//		       JOptionPane.showConfirmDialog(null,dir+"\\"+fileName,"选择的文件",JOptionPane.YES_OPTION);
		    	  path.setText(file.getSelectedFile().getAbsolutePath());
		      }
			
		}
	});
	
	 
	 container.add(panel1);
	 
	 
	 //文件名称
	 JPanel panel2=new JPanel();
	 JLabel jlabel2=new JLabel("文件名称");
	  panel2.add(jlabel2);
	  jlabel2.setAlignmentX(Component.LEFT_ALIGNMENT);
	  name=new JTextField(10);
	  panel2.add(name);
		
	  container.add(panel2);
	  
	  //标签
	  JPanel panel3=new JPanel();
	  panel3.add(new JLabel("标签组合"));
	  tags=new JTextField(10);
	  panel3.add(tags);
	  container.add(panel3);
	  //时长
	  JPanel panel5=new JPanel();
	  panel5.add(new JLabel("视频时长"));
	  time=new JTextField(10);
	  time.setText("00:00:00");
	  panel5.add(time);
	  container.add(panel5);
	  //保存的文件类型
	  JPanel panel6=new JPanel();
	  panel6.add(new JLabel("文件类型 "));
	  type=new JTextField(10);
	  type.setText("avi,rm,flv,mkv");
	  panel6.add(type);
	  container.add(panel6);
	  
	  //上传 选择文件
	  JPanel panel4=new JPanel();
	  JButton upload=new JButton("上传");
	  
	  upload.setSize(20, 20);
	  panel4.add(selectfile);
	  panel4.add(upload);
	  container.add(panel4);
	  //上传处理 
	  upload.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			try {
				new UploadControl(path.getText(), name.getText(), time.getText(), tags.getText(), type.getText()).upload();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("path:"+path.getText()+"name"+name.getText()+"time"+time.getText()+"type"+type.getText());
		}
	});
	setSize(300, 450);
	setTitle("视频上传");
	setLocation(400, 350);
}
//视频文件过滤 
class VideoFilter extends FileFilter{
     
	@Override
	public boolean accept(File f) {
		// TODO Auto-generated method stub
		if(f.isDirectory()){
			return true;
		}
			String fileName=f.getName();
			int index=fileName.lastIndexOf('.');
			if(index>0 && index <fileName.length()-1){
				String ext=fileName.substring(index+1).toLowerCase();
				if(ext.equals("avi")||ext.equals("rm")||ext.equals("flv")
						||ext.equals("mkv"))
					return true;
			}
		
		return false;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "*.flv,*.rm,*.mkv,*.avi";
	}
	
}
}
