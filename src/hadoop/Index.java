package hadoop;



import java.io.IOException;
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
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Index {
	public static class TokenizerMapper extends Mapper<Text, Text, Text, Text> {

		private final static IntWritable one = new IntWritable(1);
		private Text word = new Text();

		public void map(Text key, Text value, Context context)
				throws IOException, InterruptedException {
			FileSplit fileSplit = (FileSplit) context.getInputSplit();
			String fileName = fileSplit.getPath().getName();
			Text word = new Text();
			Text fileName_lineOffset = new Text(fileName);
			StringTokenizer itr = new StringTokenizer(value.toString());
			for (; itr.hasMoreTokens();) {
				word.set(itr.nextToken());
				context.write(word, fileName_lineOffset);
			}

		}

		public static class IntSumReducer extends
				Reducer<Text, Text, Text, Text> {
			private IntWritable result = new IntWritable();

			public void reduce(Text key, Iterable<Text> values, Context context)
					throws IOException, InterruptedException {
				java.util.Iterator<Text> it=values.iterator();
				StringBuilder all = new StringBuilder();
				if(it.hasNext())  all.append(it.next().toString());
				for(; it.hasNext(); ) 
				{	all.append(";");
					all.append(it.next().toString());					}
				context.write(key, new Text(all.toString()));
			}
		}

		public static void main(String[] args) throws
		Exception {
			Configuration conf = new Configuration();
			if (args.length != 2) {
				System.err.println("Usage: wordcount  ");
				System.exit(2);
			}
			conf.setBoolean("mapred.output.compress", true);
			conf.setClass("mapred.output.compression.codec", GzipCodec.class,
					CompressionCodec.class);
			Job job = new Job(conf, "word count");
			job.setJarByClass(Index.class);
			job.setMapperClass(TokenizerMapper.class);
			job.setReducerClass(IntSumReducer.class);
			job.setMapOutputKeyClass(Text.class);
			job.setMapOutputValueClass(IntWritable.class);
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(IntWritable.class);
           
			FileInputFormat.addInputPath(job, new Path(args[0]));
			FileOutputFormat.setOutputPath(job, new Path(args[1]));

			System.exit(job.waitForCompletion(true) ? 0 : 1);

		}
	}
}