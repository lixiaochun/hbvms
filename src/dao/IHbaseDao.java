package dao;

import java.io.IOException;  
import java.util.Map;  
  

public interface IHbaseDao {  
  
    /** 
     * 插入数据或者修改数据.对应于shell操作中的 put 
     *  
     * @param tableName 
     *            要插入的table name 
     * @param row 
     *            行 
     * @param family 
     *            列簇 
     * @param qualifier 
     *            列 
     * @param value 
     *            值 
     * @throws IOException 
     */  
	//插入数据 
    void insertAndUpdate(String tableName, String row, String family,  
            String qualifier, String value) throws IOException;  
  
 
  

    /** 
     * 获取某一行,某一列簇中的某一列的值 
     *  
     * @param tableName 
     *            表名 
     * @param rowID 
     *            行 
     * @param colName 
     *            列簇 
     * @param cluster 
     *            列 
     * @return 
     * @throws IOException 
     */  
    //获得数据
    String getValue(String tableName, String rowID, String colName,  
            String cluster) throws IOException;  
  

  
    //获取某一行所有数据
    Map<String,String> getRowValues(String tableName,String rowkey) throws IOException;
    //删除一行
    void deleteRow(String tableName,String rowkey) throws IOException;
    //获得所有行的标签
//    Map<String,String> getAllRow(String tableName,String colName,String cluster) throws IOException;




    
}  