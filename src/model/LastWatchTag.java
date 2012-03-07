package model;

public class LastWatchTag {
private int rowid;//记录id，也是视频id
private int lasttimetag;//上次观看到的时间点
public int getRowid() {
	return rowid;
	
}
public void setRowid(int rowid) {
	this.rowid = rowid;
}
public int getLasttimetag() {
	return lasttimetag;
}
public void setLasttimetag(int lasttimetag) {
	this.lasttimetag = lasttimetag;
}

}
