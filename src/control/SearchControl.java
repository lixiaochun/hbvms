package control;

public class SearchControl {
	private String tags;
	private String[] rowkeys;
	private String[] pathname;
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
public Object[][] search(){
	System.out.println("tags is" +tags);
	Object[][] object={{1,"test","avi","2012/3/10 06:04:34","00:10:00","100M",10}};
    pathname=new String[1];
    pathname[0]="/file/test.avi";

	
	return object;
}
}
