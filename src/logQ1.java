import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class logQ1 {

	public static class logMapper extends Mapper<Object, Text, Text, IntWritable> {
		private final static IntWritable one = new IntWritable(1);
		private Text word = new Text();
		
		public void map(Object key,Text value,Context context) 
				throws IOException,InterruptedException{
			String line = value.toString();
			if(line.contains("/assets/img/home-logo.png")) {
				context.write(word, one);
			}
		}
	}
	
	public static class logReducer extends Reducer<Text, IntWritable, Text, IntWritable>{
		private IntWritable result = new IntWritable();
		public void reduce(Text key, Iterable<IntWritable> values, Context context) 
				throws IOException,InterruptedException {
		int sum = 0;
		for (IntWritable val : values) {
		sum += val.get();
		}
		result.set(sum);
		context.write(key, result);
		}
	}
	public static void main(String[] args) throws Exception{
        
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "logQ1");
        job.setJarByClass(logQ1.class);
        job.setMapperClass(logMapper.class);
        job.setCombinerClass(logReducer.class);
        job.setReducerClass(logReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
