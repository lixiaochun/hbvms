package control;

import javax.swing.JOptionPane;

import service.HBaseService;
import service.IHBaseService;
import util.HDFSUtil;

public class DownloadControl {
	private String pathname;
	private String dest;
	private IHBaseService hBaseService;
public DownloadControl() {
	// TODO Auto-generated constructor stub
}
public DownloadControl(String pathname,String dest) {
	// TODO Auto-generated constructor stub
	this.pathname=pathname;
	this.dest=dest;
	hBaseService=new HBaseService();
}
public void download(){
	System.out.println("pathname:"+pathname+"dest:"+dest);
	HDFSUtil.download(HDFSUtil.getFileSystem(), pathname, dest);
     
	JOptionPane.showMessageDialog(null, "下载成功");
}

}
