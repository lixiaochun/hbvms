package service;

import java.util.ArrayList;

import model.Video;

public interface IHBaseService {

 void addVideo(String tableName,Video video);
 Video getVideobyID(String tableName,String rowkey);//根据rowkey获得Video
 ArrayList<Video> getVideobyIDs(String tableName,String[] rowkeys);//根据rowkey数组获得Video集合
 void deleteVideo(String tableName,String rowkey);
}
