package hadoop;



import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;

import javax.swing.text.html.HTMLDocument.Iterator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.mapred.FileSplit;
import org.apache.hadoop.mapred.InputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.OutputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import util.HDFSUtil;

public class Index {

	public static class IndexMapper extends Mapper<Object, Text, Text, Text> {

		private final static IntWritable one = new IntWritable(1);
		private Text word = new Text();
	
		public void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {
//			FileSplit fileSplit = (FileSplit) context.getInputSplit();
//			String fileName = fileSplit.getPath().getName();
//			Text word = new Text();
//			Text fileName_lineOffset = new Text(fileName);
//			StringTokenizer itr = new StringTokenizer(value.toString());
//			for (; itr.hasMoreTokens();) {
//				word.set(itr.nextToken());
//				context.write(word, fileName_lineOffset);
//			}
			 
			Text tagkey = new Text();
			Text rowkey=new Text();
			
            String tempString=transformText(value, "UTF-8");
              String[] lines=tempString.split("\n");
            for(String line:lines){
            	String[] keyAndtags=line.split("/");
                if(keyAndtags.length>=2){
                	rowkey.set(keyAndtags[0]);
                	String[] tags=keyAndtags[1].split(",");
                	for(String tag:tags){
                		if(tag!=null&&!tag.equals("")){
                			tagkey.set(tag);
                			System.out.println(tag);
                			context.write(tagkey, rowkey);
                		}
                	}
                }
            	
            }
		}
	}
		public static class IndexReducer extends
				Reducer<Text, Text, Text, Text> {
			private Text result = new Text();

			public void reduce(Text key, Iterable<Text> values, Context context)
					throws IOException, InterruptedException {
//				java.util.Iterator<Text> it=values.iterator();
//				StringBuilder all = new StringBuilder();
//				if(it.hasNext())  all.append(it.next().toString());
//				for(; it.hasNext(); ) 
//				{	all.append(";");
//					all.append(it.next().toString());					}
//				context.write(key, new Text(all.toString()));
				Text rowkeys=new Text();
				java.util.Iterator<Text> it=values.iterator();
				StringBuffer all=new StringBuffer();
				if(it.hasNext())  all.append(transformText(it.next(),"UTF-8"));
				for(; it.hasNext(); ) 
				{	all.append(",");
				all.append(transformText(it.next(),"UTF-8"));		
				}
			
				rowkeys.set(all.toString());
			    System.out.println(rowkeys);
			    System.out.println(key);
				context.write(key,rowkeys);
			}
		}
         public void createindex(String srcpath,String despath)throws
 		Exception {
        	 Configuration conf = new Configuration();
            
 		
 			Job job = new Job(conf, "tag index");
 			job.setJarByClass(Index.class);
 			job.setMapperClass(IndexMapper.class);
 			job.setReducerClass(IndexReducer.class);
 			job.setMapOutputKeyClass(Text.class);
 			job.setMapOutputValueClass(Text.class);
 			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(Text.class);
 			
			job.setOutputFormatClass(TextOutputFormat.class);
 		
 			FileInputFormat.addInputPath(job, new Path(srcpath));
 			FileOutputFormat.setOutputPath(job, new Path(despath));

 			System.exit(job.waitForCompletion(true) ? 0 : 1);

        	
         }
//		public static void main(String[] args) throws
//		Exception {
//			Configuration conf = new Configuration();
//			if (args.length != 2) {
//				System.err.println("Usage: wordcount  ");
//				System.exit(2);
//			}
//			conf.setBoolean("mapred.output.compress", true);
//			conf.setClass("mapred.output.compression.codec", GzipCodec.class,
//					CompressionCodec.class);
//			Job job = new Job(conf, "word count");
//			job.setJarByClass(Index.class);
//			job.setMapperClass(TokenizerMapper.class);
//			job.setReducerClass(IntSumReducer.class);
//			job.setMapOutputKeyClass(Text.class);
//			job.setMapOutputValueClass(IntWritable.class);
//			job.setOutputKeyClass(Text.class);
//			job.setOutputValueClass(IntWritable.class);
//           
//			FileInputFormat.addInputPath(job, new Path(args[0]));
//			FileOutputFormat.setOutputPath(job, new Path(args[1]));
//
//			System.exit(job.waitForCompletion(true) ? 0 : 1);
//
//		}
         public static String transformText(Text text, String encoding) {
             String value = null;
             try {
             value = new String(text.getBytes(), 0, text.getLength(), encoding);
             }
             catch (UnsupportedEncodingException e) {
             e.printStackTrace();
             }
             return value;
         }
	}
