package view;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

public class UpdateFrame extends JFrame{

public UpdateFrame() {
	// TODO Auto-generated constructor stub
	Container container = this.getContentPane();
	 container.setLayout(new GridLayout(8,1));
	 
	 //选择文件
	 JPanel panel1=new JPanel();
	 panel1.add(new JLabel("文件目录"));
	 JTextField path=new JTextField(10);
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
		       String fileName=file.getSelectedFile().getName();
		       String dir=file.getSelectedFile().getName();
		       JOptionPane.showConfirmDialog(null,dir+"\\"+fileName,"选择的文件",JOptionPane.YES_OPTION);
		      }
			
		}
	});
	 panel1.add(selectfile);
	 container.add(panel1);
	setSize(600, 450);
	setTitle("视频上传");
	setLocation(200, 150);
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
