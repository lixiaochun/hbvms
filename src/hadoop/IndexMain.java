package hadoop;

import service.HBaseService;
import service.IHBaseService;
import util.HDFSUtil;
import hadoop.Index;
public class IndexMain {
	private static IHBaseService ihBaseService=new HBaseService();
public static void main(String[] args) {
	Index indexUntil=new Index();
	String parpath="hdfs://192.168.69.25:9000/user/hadoop/index/";
	
	try {
//		 HDFSUtil.DeFile(HDFSUtil.getFileSystem(), "/index/tagindex");
		indexUntil.createindex(parpath+"indextemp.txt", parpath+"tagindex");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
