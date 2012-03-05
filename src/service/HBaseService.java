package service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HBaseAdmin;

import dao.HbaseDao;
import dao.IHbaseDao;



import model.Video;

public class HBaseService implements IHBaseService {
    private IHbaseDao ihBaseDao;
    public HBaseService(HBaseConfiguration conf,HBaseAdmin admin) {
		// TODO Auto-generated constructor stub
    	ihBaseDao=new HbaseDao(conf,admin);
	}
	@Override
	public ArrayList<Video> getVideobyTag(String Tag) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addVideo(Video video) {
		// TODO Auto-generated method stub
		
	}
	

}
