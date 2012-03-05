package model;
//倒排索引对象
public class TagIndex {
private String tag;//索引标签
private int[] rowIds;//对应的视频ids
public String getTag() {
	return tag;
}
public void setTag(String tag) {
	this.tag = tag;
}
public int[] getRowIds() {
	return rowIds;
}
public void setRowIds(int[] rowIds) {
	this.rowIds = rowIds;
}

}
