import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;


public class logQ31 {

	public static class logMapper extends Mapper<Object, Text, Text, IntWritable> {
		private final static IntWritable one = new IntWritable(1);
		private Text word = new Text();
		public void map(Object key,Text value,Context context) 
				throws IOException,InterruptedException{
			String line = value.toString();
		    String r = line.split("\"")[1];
		    String path = r.split(" ")[1];
            word.set(path);
            context.write(word, one);
            
		}
	}
	
	public static class logReducer extends Reducer<Text, IntWritable, Text, IntWritable>{
		static int max = 0;
		private final static IntWritable result = new IntWritable(max);
		private Text maxpath = new Text();
		public void reduce(Text key, Iterable<IntWritable> values, Context context) 
				throws IOException,InterruptedException {
		int sum = 0;
		for (IntWritable val : values) {
		sum += val.get();
		}
		if(max < sum) {
			max = sum;
			maxpath.set(key);
		}
		}
		public void tidy(Context context) throws IOException,InterruptedException{
			context.write(maxpath, result);
		}
	}
	public static void main(String[] args) throws Exception{
        
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "logQ31");
        job.setJarByClass(logQ31.class);
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
