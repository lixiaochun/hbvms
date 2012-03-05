package model;

//视频对象
import java.util.ArrayList;
import java.util.Date;

public class Video {
private int rowId;//视频id
private String name;//视频名称
private ArrayList<String> tags;//视频标签
private String pathname;//视频的存储路径
private Date  uploadDate;//视频上传时间
private int videolength;//视频时长，单位为秒
private int downloadnum;//视频下载次数
private int size;//视频大小
private String type;//视频类型
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public int getRowId() {
	return rowId;
}
public void setRowId(int rowId) {
	this.rowId = rowId;
}
public int getSize() {
	return size;
}
public void setSize(int size) {
	this.size = size;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public ArrayList<String> getTags() {
	return tags;
}
public void setTags(ArrayList<String> tags) {
	this.tags = tags;
}
public String getPathname() {
	return pathname;
}
public void setPathname(String pathname) {
	this.pathname = pathname;
}
public Date getUploadDate() {
	return uploadDate;
}
public void setUploadDate(Date uploadDate) {
	this.uploadDate = uploadDate;
}
public int getVideolength() {
	return videolength;
}
public void setVideolength(int videolength) {
	this.videolength = videolength;
}
public int getDownloadnum() {
	return downloadnum;
}
public void setDownloadnum(int downloadnum) {
	this.downloadnum = downloadnum;
}

}
