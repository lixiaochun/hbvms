package control;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.Video;

import service.HBaseService;
import service.IHBaseService;
import util.HDFSUtil;

public class UpdateControl {
	private String path;
	private String name;
	private String time;
	private String type;
	private IHBaseService hbaseService;
	private String tags;

public UpdateControl() {
	// TODO Auto-generated constructor stub
}
public UpdateControl(String path,String name,String time,String tags, String type){
	
	this.path=path;
	this.name=name;
//	try {
//		Date date=dateformat.parse(time);
//		this.time=date.getHours()*3600+date.getMinutes()*60+date.getSeconds();
//	} catch (ParseException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
	this.tags=tags;
	this.time=time;
	this.type=type;
	hbaseService=new HBaseService();
}
public void upload() throws ParseException{
	SimpleDateFormat dateformat=new SimpleDateFormat("HH:mm:ss");
	Video video=new Video();
	Date date=dateformat.parse(this.time);
	int videotime=date.getHours()*3600+date.getMinutes()*60+date.getSeconds();
	video.setName(name);
	String[] tagArray=this.tags.split(",");
	ArrayList<String> taglist=new ArrayList<String>();
	for(String tag:tagArray){
		if(!tag.equals(""))
			taglist.add(tag);
	}
	video.setVideolength(videotime);
	video.setTags(taglist);
	video.setType(this.type);
	video.setUploadDate(new Date());
	video.setPathname("/file/"+name+"."+type);
	File file=new File(path);
	long size=file.length();
	video.setSize(size);
	HDFSUtil.upload(HDFSUtil.getFileSystem(), path, "/file/"+name+"."+type);
	hbaseService.addVideo("test", video);
}
}
