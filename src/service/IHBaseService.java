package service;

import java.util.ArrayList;

import model.Video;

public interface IHBaseService {
 ArrayList<Video> getVideobyTag(String Tag);
 void addVideo(Video video);
 
	

}
