package control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Video;

import service.HBaseService;
import service.IHBaseService;
import util.HDFSUtil;

public class SearchControl {
	private String tags;
	private String[] rowkeys;
	private String[] pathname;
	private Map<String, List<String>> map=new HashMap<String, List<String>>();
	private IHBaseService ihBaseService;
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
	ihBaseService=new HBaseService();
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
	SimpleDateFormat dateformat=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	try {
		if(map.isEmpty())
			map=createIndex();
		String[] tagArray=tags.split(",");
		if(tagArray.length<1){
			System.out.println("请输入标签");
			return null;
		}
		List<String> rowkeyList=map.get(tagArray[0].trim());
		for(int m=1;m<tagArray.length;m++)
			rowkeyList.retainAll(map.get(tagArray[m].trim()));
		

		ArrayList<Video> videolist=ihBaseService.getVideobyIDs("hadoop", rowkeyList);
		objects=new Object[videolist.size()][7];
		pathname=new String[videolist.size()];
		rowkeys=new String[videolist.size()];
		for(int i=0;i<videolist.size();i++){
			Video video=videolist.get(i);
            objects[i][0]=i+1;
            objects[i][1]=video.getName();
             objects[i][2]=video.getType();
             objects[i][3]=dateformat.format(video.getUploadDate());
                int hour=video.getVideolength()/3600;
                int minute=(video.getVideolength()-hour*3600)/60;
                int second=video.getVideolength()-hour*3600-minute*60;
              objects[i][4]=new String(hour+"小时"+minute+"分"+second+"秒");
              objects[i][5]=String.valueOf(video.getSize()/1024/1024)+"MB";
              objects[i][6]=video.getDownloadnum();
              pathname[i]=video.getPathname();
			    rowkeys[i]=video.getRowKey();
		}
		
		
			
	   return objects;
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	return null;
	 
}
 public Map<String,List<String>> createIndex() throws Exception{
	 
		List<String> lines=HDFSUtil.read(HDFSUtil.getFileSystem(), "/index/tagindex/part-r-00000");
		for(String line:lines){
			String[] tokens = line.split("\\s+");
			if (tokens.length < 2)
			{
				continue;
			}
			String tag=tokens[0];
			List<String> rowkeyList=new ArrayList<String>();
			String[] rowkeys=tokens[1].split(",");
			for(String rowkey:rowkeys){
				if(!rowkey.equals("")&&rowkey!=null)
				rowkeyList.add(rowkey);
			}
			map.put(tag, rowkeyList);
		}
	
//		System.out.println("------------------");
//		System.out.println(tempString);
//		System.out.println("------------------");
//		String[] tagAndRowkeys=tempString.split(";");
//		System.out.println(tagAndRowkeys.length);
//		System.out.println(tagAndRowkeys[0]+"/"+tagAndRowkeys[1]);
//		for(String tagandkey:tagAndRowkeys){
//			
//			String[] tag=tagandkey.split("\\s+");
//			if(tag.length<2)
//				continue;
//		   map.put(tag[0], tag[1]);
//		   System.out.println(tag[0]+tag[1]);
//			   
//			
//		}
		
		
	return map;
	 
 }
}
