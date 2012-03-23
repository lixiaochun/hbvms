package dao;


import java.io.IOException;  
import java.util.ArrayList;
import java.util.HashMap;  
import java.util.List;
import java.util.Map;  
  
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;  
import org.apache.hadoop.hbase.HColumnDescriptor;  
import org.apache.hadoop.hbase.HTableDescriptor;  
import org.apache.hadoop.hbase.KeyValue;  
import org.apache.hadoop.hbase.client.Delete;  
import org.apache.hadoop.hbase.client.Get;  
import org.apache.hadoop.hbase.client.HBaseAdmin;  
import org.apache.hadoop.hbase.client.HTable;  
import org.apache.hadoop.hbase.client.Put;  
import org.apache.hadoop.hbase.client.Result;  
import org.apache.hadoop.hbase.client.ResultScanner;  
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;  
  
 
public class HbaseDao implements IHbaseDao {  
    public final static String COLENDCHAR = String  
            .valueOf(KeyValue.COLUMN_FAMILY_DELIMITER);// ":" 列簇和列之间的分隔符  
  
	private  Configuration  conf = HBaseConfiguration.create();   
   
    
    //构造函数
      public HbaseDao() {
		// TODO Auto-generated constructor stub
    	
	}
    public Configuration getConf() {  
        return conf;  
    }  
  
    public void setConf(Configuration conf) {  
        this.conf = conf;  
    }  
  

   
//  
//    public void deleteColumn(String tableName, String rowID, String colName,  
//            String cluster) throws IOException {  
//        try {  
//            Delete del = new Delete(rowID.getBytes());  
//            if (cluster == null || "".equals(cluster))  
//                del.deleteColumn(colName.getBytes(),null);  
//            else  
//                del.deleteColumn(colName.getBytes(), cluster.getBytes());  
//            HTable hTable = this.getHTable(tableName);  
//            hTable.delete(del);  
//        } catch (IOException e) {  
//            throw e;  
//        }  
//    }  
  
//    public Map<String, String> getColumnValue(String tableName, String colName,  
//            String cluster) throws IOException {  
//        ResultScanner scanner = null;  
//        try {  
//            HTable hTable = this.getHTable(tableName);  
//            scanner = hTable.getScanner(colName.getBytes(), cluster.getBytes());  
//            Result rowResult = scanner.next();  
//            Map<String, String> resultMap = new HashMap<String, String>();  
//            String row;  
//            while (rowResult != null) {  
//                row = new String(rowResult.getRow());  
//                resultMap.put(row, new String(rowResult.getValue(colName  
//                        .getBytes(), cluster.getBytes())));  
//                rowResult = scanner.next();  
//            }  
//            return resultMap;  
//        } catch (IOException e) {  
//            throw e;  
//        } finally {  
//            if (scanner != null) {  
//                scanner.close();// 一定要关闭  
//            }  
//        }  
//    }  
  
    public String getValue(String tableName, String rowID, String colName,  
            String cluster) throws IOException {  
        try {  
            HTable hTable = this.getHTable(tableName);  
            Get get = new Get(rowID.getBytes());  
            Result result = hTable.get(get);  
            byte[] b = result.getValue(colName.getBytes(), cluster.getBytes());  
            if (b == null)  
                return "";  
            else  
                return new String(b);  
        } catch (IOException e) {  
            throw e;  
        }  
    }  
//    //获得所有行
//    @Override
//	public Map<String, String> getAllRow(String tableName, String colName,
//			String cluster) throws IOException {
//		// TODO Auto-generated method stub
//    	HTable table = this.getHTable(tableName);  
//    	Scan s = new Scan();
//    	ResultScanner rs=table.getScanner(s);
//    	Map<String,String> map=new HashMap<String,String>();
//    	for (Result r:rs){
//    		KeyValue[] kv=r.raw();
//    		
//    	}
//		return null;
//	}
	public void insertAndUpdate(String tableName, String row, String family,  
            String qualifier, String value) throws IOException {  
        HTable table = this.getHTable(tableName);  
        Put p = new Put(Bytes.toBytes(row));  
        p.add(Bytes.toBytes(family), Bytes.toBytes(qualifier), Bytes  
                .toBytes(value));  
        table.put(p);  
  
    }  
  
//    public void removeFamily(String tableName, String colName)  
//            throws IOException {  
//        try {  
//            String tmp = this.fixColName(colName);  
//            if (admin.isTableAvailable(tableName))  
//                admin.disableTable(tableName);  
//            this.admin.deleteColumn(tableName, tmp);  
//            this.admin.enableTable(tableName);  
//        } catch (IOException e) {  
//            throw e;  
//        }  
//    }  
  
//    /** 
//     * 给表添加列,此时不带列族 
//     *  
//     * @param htdesc 
//     * @param colName 
//     * @param readonly 
//     *            是否只读 
//     * @throws Exception 
//     */  
//    private void addFamily(HTableDescriptor htdesc, String colName,  
//            final boolean readonly) {  
//        htdesc.addFamily(this.createHCDesc(colName));  
//        htdesc.setReadOnly(readonly);  
//    }  
  

  
    /** 
     * 取得某个表 
     *  
     * @param tableName 
     * @return 
     * @throws Exception 
     */  
    private HTable getHTable(String tableName) throws IOException {  
        try {  
        	return new HTable(conf, tableName);
              
        } catch (IOException e) {  
            throw e;  
        }  
    }
	@Override
	public Map<String, String> getRowValues(String tableName, String rowkey)
			throws IOException {
		// TODO Auto-generated method stub
		HTable  htable=getHTable(tableName);
		Map<String, String> resultMap = new HashMap<String, String>(); 
		Get get=new Get(rowkey.getBytes());
		Result r=htable.get(get);
		for(KeyValue kv:r.raw()){
			resultMap.put(new String(kv.getQualifier()),new String(kv.getValue()));
		}
		return resultMap;
	}
	@Override
	public void deleteRow(String tableName, String rowkey) throws IOException {
		// TODO Auto-generated method stub
		HTable  htable=getHTable(tableName);
	
		Delete d1=new Delete(rowkey.getBytes());
		
		htable.delete(d1);
		System.out.println("删除行成功 ");
	}  
  
}  
