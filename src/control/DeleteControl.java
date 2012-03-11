package control;

import service.HBaseService;
import service.IHBaseService;
import util.HDFSUtil;

public class DeleteControl {
	private String rowkey;
	private IHBaseService hBaseService;
	private String filepath;
public DeleteControl(String rowkey,String filepath) {
	// TODO Auto-generated constructor stub
	this.rowkey=rowkey;
	this.filepath=filepath;
	hBaseService=new HBaseService();
}
public void delete(){
//	hBaseService.deleteVideo("test", this.rowkey);
	HDFSUtil.DeFile(HDFSUtil.getFileSystem(), filepath);
	
}
}
