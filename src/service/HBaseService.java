package service;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HBaseAdmin;

import dao.HbaseDao;
import dao.IHbaseDao;



import model.Video;

public class HBaseService implements IHBaseService {
    private IHbaseDao ihBaseDao;
   
    public HBaseService() {
     ihBaseDao=new HbaseDao();
	}


	@Override
	public void addVideo(String tableName,Video video) {
		// TODO Auto-generated method stub
		try {
			ihBaseDao.insertAndUpdate(tableName, String.valueOf(video.getRowKey()), "info", "name", video.getName());
			String tags="";
			
			for(String s:video.getTags()){
				System.out.println("-------------------");
				System.out.println(s);
				System.out.println("-------------------");
				tags+=s+",";
				}
			ihBaseDao.insertAndUpdate(tableName, String.valueOf(video.getRowKey()), "info", "tags", tags);
			ihBaseDao.insertAndUpdate(tableName, String.valueOf(video.getRowKey()), "info", "pathname", video.getPathname());
			ihBaseDao.insertAndUpdate(tableName, String.valueOf(video.getRowKey()), "info", "uploadDate", Long.toString(video.getUploadDate().getTime()));
			ihBaseDao.insertAndUpdate(tableName, String.valueOf(video.getRowKey()), "info", "videolength", Integer.toString(video.getVideolength()));
			ihBaseDao.insertAndUpdate(tableName, String.valueOf(video.getRowKey()), "info", "downloadnum", Integer.toString(video.getDownloadnum()));
			ihBaseDao.insertAndUpdate(tableName, String.valueOf(video.getRowKey()), "info", "size", Long.toString(video.getSize()));
			ihBaseDao.insertAndUpdate(tableName, String.valueOf(video.getRowKey()), "info", "type", video.getType());


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public Video getVideobyID(String tableName,String rowkey) {
		// TODO Auto-generated method stub
		try {
			Map<String,String> resultMap=ihBaseDao.getRowValues(tableName, rowkey);
			Video video=new Video();
			video.setRowKey(rowkey);
			video.setName(resultMap.get("name"));
			
		    String tags=resultMap.get("tags");
		    System.out.println("---------------");
		    System.out.println(tags);
		    System.out.println("---------------");
		    ArrayList<String> taglist=new ArrayList<String>();
		     String[]  tagArray=tags.split(",");
		     for(String tag:tagArray)
		     {
		    	 if(!tag.equals(""))
		    		 taglist.add(tag);
		     }
		     video.setTags(taglist);
		     video.setPathname(resultMap.get("pathname"));
		     video.setType(resultMap.get("type"));
		     video.setVideolength(Integer.parseInt(resultMap.get("videolength")));
		     video.setSize(Long.parseLong(resultMap.get("size")));
		     video.setDownloadnum(Integer.parseInt(resultMap.get("downloadnum")));
		     
		     video.setUploadDate(new Date(Long.parseLong(resultMap.get("uploadDate"))));
		     return video;
		     } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public ArrayList<Video> getVideobyIDs(String tableName,List<String> rowkeys) {
		// TODO Auto-generated method stub
		ArrayList<Video> videolist=new ArrayList<Video>();
		for(String rowkey:rowkeys){
			videolist.add(getVideobyID(tableName, rowkey));
		}
		return videolist;
	}


	@Override
	public void deleteVideo(String tableName,String rowkey) {
		// TODO Auto-generated method stub
		try {
			ihBaseDao.deleteRow(tableName, rowkey);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void downloadNumPlus(String tableName, String rowkey) {
		// TODO Auto-generated method stub
		try {
			String value=ihBaseDao.getValue(tableName, rowkey, "info", "downloadnum");
			
			String valueNew=String.valueOf((Integer.parseInt(value))+1);
			ihBaseDao.deletevalue(tableName, rowkey, "info", "downloadnum");
			ihBaseDao.insertAndUpdate(tableName, rowkey, "info", "downloadnum", valueNew);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
