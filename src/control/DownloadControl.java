package control;

import javax.swing.JOptionPane;

import service.HBaseService;
import service.IHBaseService;
import util.HDFSUtil;

public class DownloadControl {
	private String pathname;
	private String dest;
	private IHBaseService hBaseService;
	private String rowkey;
public DownloadControl() {
	// TODO Auto-generated constructor stub
}
public DownloadControl(String pathname,String dest,String rowkey) {
	// TODO Auto-generated constructor stub
	this.pathname=pathname;
	this.dest=dest;
	this.rowkey=rowkey;
	hBaseService=new HBaseService();
}
public void download(){
	System.out.println("pathname:"+pathname+"dest:"+dest);
	HDFSUtil.download(HDFSUtil.getFileSystem(), pathname, dest);
    hBaseService.downloadNumPlus("test2", getRowkey());
	JOptionPane.showMessageDialog(null, "下载成功");
}
public String getRowkey() {
	return rowkey;
}
public void setRowkey(String rowkey) {
	this.rowkey = rowkey;
}

}
