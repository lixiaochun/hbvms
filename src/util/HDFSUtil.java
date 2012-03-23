package util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;  
import java.util.Map.Entry;  

import org.apache.hadoop.conf.Configuration;  
import org.apache.hadoop.fs.FSDataInputStream;  
import org.apache.hadoop.fs.FSDataOutputStream;  
import org.apache.hadoop.fs.FileStatus;  
import org.apache.hadoop.fs.FileSystem;  
import org.apache.hadoop.fs.Path;  
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hdfs.DistributedFileSystem;  
import org.apache.hadoop.hdfs.protocol.DatanodeInfo;  


public class HDFSUtil {  

    public synchronized static FileSystem getFileSystem() {  
        FileSystem fs = null;  
        String url = "hdfs://192.168.69.25:9000";  
        Configuration config = new Configuration();  
        config.set("fs.default.name", url); 
         config.set("dfs.support.append", "true");
        try {  
            fs = FileSystem.get(config);  
        } catch (Exception e) {  
            e.printStackTrace();
        }  
        return fs;  
    }  
    public synchronized static void listNode(FileSystem fs) {  
        DistributedFileSystem dfs = (DistributedFileSystem) fs;  
        try {  
            DatanodeInfo[] infos = dfs.getDataNodeStats();  
            for (DatanodeInfo node : infos) {  
                System.out.println("HostName: " + node.getHostName() + "/n"  
                        + node.getDatanodeReport());  
                System.out.println("--------------------------------");  
            }  
        } catch (Exception e) {  
            e.printStackTrace();
        }  
    }  
    /** 
     * 打印系统配置 
     *  
     * @param fs 
     */  
    public synchronized static void listConfig(FileSystem fs) {  
        Iterator<Entry<String, String>> entrys = fs.getConf().iterator();  
        while (entrys.hasNext()) {  
            Entry<String, String> item = entrys.next();  
          
        }  
    }  
    /** 
     * 创建目录和父目录 
     *  
     * @param fs 
     * @param dirName 
     */  
    public synchronized static void mkdirs(FileSystem fs, String dirName) {  
        // Path home = fs.getHomeDirectory();  
        Path workDir = fs.getWorkingDirectory();  
        String dir = workDir + "/" + dirName;  
        Path src = new Path(dir);  
        // FsPermission p = FsPermission.getDefault();  
        boolean succ;  
        try {  
            succ = fs.mkdirs(src);  
           
        } catch (Exception e) {  
            e.printStackTrace(); 
        }  
    }  
    /** 
     * 删除目录和子目录 
     *  
     * @param fs 
     * @param dirName 
     */  
    public synchronized static void removedirs(FileSystem fs, String dirName) {  
        // Path home = fs.getHomeDirectory();  
        Path workDir = fs.getWorkingDirectory();  
        String dir = workDir + "/" + dirName;  
        Path src = new Path(dir);  
       
        
        boolean succ;  
        try {  
            succ = fs.delete(src, true);  
          
        } catch (Exception e) {  
            e.printStackTrace();
        }  
    }  
    /** 
     * 上传目录或文件 
     *  
     * @param fs 
     * @param local 
     * @param remote 
     */  
    public synchronized static void upload(FileSystem fs, String local,  
            String remote) {  
        // Path home = fs.getHomeDirectory();  
        Path workDir = fs.getWorkingDirectory();  
        Path dst = new Path(workDir + "/" + remote);  
        Path src = new Path(local);  
        try {  
            fs.copyFromLocalFile(false, true, src, dst);  
         
        } catch (Exception e) {  
            e.printStackTrace();
        }  
    }  
    /** 
     * 下载目录或文件 
     *  
     * @param fs 
     * @param local 
     * @param remote 
     */  
    public synchronized static void download(FileSystem fs, String src,  
            String dest) {  
        // Path home = fs.getHomeDirectory();  
        Path workDir = fs.getWorkingDirectory();  
        Path srcPath = new Path(workDir + "/" + src);  
        Path destPath = new Path(dest);  
        try {  
            fs.copyToLocalFile(false, srcPath, destPath);  
          
        } catch (Exception e) {  
            e.printStackTrace();
        }  
    }  
    public synchronized static void DeFile(FileSystem fs,String filepath){
    	 Path workDir = fs.getWorkingDirectory();  
		    Path path = new Path(workDir + "/" + filepath);  
		    try {
				fs.delete(path, false);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }
    /** 
     * 字节数转换 
     *  
     * @param size 
     * @return 
     */  
    public synchronized static String convertSize(long size) {  
        String result = String.valueOf(size);  
        if (size < 1024 * 1024) {  
            result = String.valueOf(size / 1024) + " KB";  
        } else if (size >= 1024 * 1024 && size < 1024 * 1024 * 1024) {  
            result = String.valueOf(size / 1024 / 1024) + " MB";  
        } else if (size >= 1024 * 1024 * 1024) {  
            result = String.valueOf(size / 1024 / 1024 / 1024) + " GB";  
        } else {  
            result = result + " B";  
        }  
        return result;  
    }  
    /** 
     * 遍历HDFS上的文件和目录 
     *  
     * @param fs 
     * @param path 
     */  
    public synchronized static void listFile(FileSystem fs, String path) {  
        Path workDir = fs.getWorkingDirectory();  
        Path dst;  
        if (null == path || "".equals(path)) {  
            dst = new Path(workDir + "/" + path);  
        } else {  
            dst = new Path(path);  
        }  
        try {  
            String relativePath = "";  
            FileStatus[] fList = fs.listStatus(dst);  
            for (FileStatus f : fList) {  
                if (null != f) {  
                    relativePath = new StringBuffer()  
                            .append(f.getPath().getParent()).append("/")  
                            .append(f.getPath().getName()).toString();  
                    if (f.isDir()) {  
                        listFile(fs, relativePath);  
                    } else {  
                        System.out.println(convertSize(f.getLen()) + "/t/t"  
                                + relativePath);  
                    }  
                }  
            }  
        } catch (Exception e) {  
            e.printStackTrace();
        } finally {  
        }  
    }  
    public synchronized static void write(FileSystem fs, String path,  
            String data) {  
        // Path home = fs.getHomeDirectory();  
        Path workDir = fs.getWorkingDirectory();  
        Path dst = new Path(workDir + "/" + path);  
        try {  
            FSDataOutputStream dos = fs.create(dst);  
            dos.writeUTF(data);  
            dos.close();  
         
        } catch (Exception e) {  
            e.printStackTrace();
        }  
    }  
    public synchronized static void append(FileSystem fs, String path,  
            String data) {  
        // Path home = fs.getHomeDirectory();  
        Path workDir = fs.getWorkingDirectory();  
        Path dst = new Path(workDir + "/" + path);  
        try {  
        	OutputStream  out = fs.append(dst);  
            out.write(data.getBytes("UTF-8"));
             out.flush();
             out.close();
          
        } catch (Exception e) {  
            e.printStackTrace();
        }  
    }  
    public synchronized static String read(FileSystem fs, String path) {  
        String content = null;  
        // Path home = fs.getHomeDirectory();  
        Path workDir = fs.getWorkingDirectory();  
        Path dst = new Path(workDir + "/" + path);  
        try {  
            // reading  
            FSDataInputStream dis = fs.open(dst);  
            content = dis.readUTF();  
            dis.close();  
          
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return content;  
    }  
}  