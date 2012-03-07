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
    void insertAndUpdate(String tableName, String row, String family,  
            String qualifier, String value) throws IOException;  
  
    /** 
     * 删除列名(也就是删除family.属于修改表结构的操作.调用的时候请慎重) 
     *  
     * @param tableName 
     * @param colName 
     * @throws IOException 
     */  
    void removeFamily(String tableName, String colName) throws IOException;  
  
    /** 
     * 删除某个family下的某个列的某行 
     *  
     * @param tableName 
     *            表名 
     * @param rowID 
     *            行名 
     * @param colName 
     *            family name(列簇) 
     * @param cluster 
     *            列名 
     * @throws IOException 
     */  
    void deleteColumn(String tableName, String rowID, String colName,  
            String cluster) throws IOException;  
  
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
    String getValue(String tableName, String rowID, String colName,  
            String cluster) throws IOException;  
  
    /** 
     * 获取某一列的所有行值 
     *  
     * @param tableName 
     *            表名 
     * @param colName 
     *            列簇名 
     * @param cluster 
     *            列名 
     * @return 
     * @throws IOException 
     */  
    Map<String, String> getColumnValue(String tableName, String colName,  
            String cluster) throws IOException;  
  
}  