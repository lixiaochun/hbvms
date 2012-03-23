package control;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.Video;

import service.HBaseService;
import service.IHBaseService;
import util.HDFSUtil;

public class SearchControl {
	private String tags;
	private String[] rowkeys;
	private String[] pathname;
	private Map<String, String> map;
	private IHBaseService ihBaseService=new HBaseService();
public String[] getPathname() {
		return pathname;
	}
	public void setPathname(String[] pathname) {
		this.pathname = pathname;
	}
public String[] getRowkeys() {
		return rowkeys;
	}
	public void setRowkeys(String[] rowkeys) {
		this.rowkeys = rowkeys;
	}
public SearchControl(String tags) {
	this.tags=tags;
}
public Object[][] search() {
	System.out.println("tags is" +tags);
	Object[][] objects;
//	Object[][] object={{1,"test","avi","2012/3/10 06:04:34","00:10:00","100M",10}};
//    pathname=new String[1];
//    pathname[0]="/file/test.mkv";
//
//	
//	return object;
	SimpleDateFormat dateformat=new SimpleDateFormat("yyyy:mm:dd HH:mm:ss");
	SimpleDateFormat timeformat=new SimpleDateFormat("HH:mm:ss");
	try {
		if(map.isEmpty())
			map=createIndex();
		String rowkeyString=map.get(tags);
		String[] rowkeyArrary=rowkeyString.split(",");
		ArrayList<Video> videolist=ihBaseService.getVideobyIDs("hadoop", rowkeyArrary);
		objects=new Object[videolist.size()][7];
		pathname=new String[videolist.size()];
		rowkeys=new String[videolist.size()];
		for(int i=0;i<videolist.size();i++){
			Video video=videolist.get(i);
            objects[i][0]=i+1;
            objects[i][1]=video.getName();
             objects[i][2]=video.getType();
             objects[i][3]=dateformat.format(video.getUploadDate());
              objects[i][4]=video.getVideolength();
              objects[i][5]=String.valueOf(video.getSize()/1024/1024)+"MB";
              objects[i][6]=video.getDownloadnum();
              pathname[i]=video.getPathname();
			    
		}
		
		
			
	   return objects;
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	return null;
	 
}
 public Map<String,String> createIndex() throws Exception{
	 map=new HashMap<String, String>();
		String tempString=HDFSUtil.read(HDFSUtil.getFileSystem(), "/index/tagindex.txt");
		String[] tagAndRowkeys=tempString.split("\r\n");
		
		for(String tagandkey:tagAndRowkeys){
			
			String[] tag=tagandkey.split("\\s");
			if(tag.length<2)
				continue;
		   map.put(tag[0], tag[1]);
			   
			
		}
	return map;
	 
 }
}
