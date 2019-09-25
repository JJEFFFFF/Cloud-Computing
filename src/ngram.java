
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

import java.io.IOException;
import java.util.StringTokenizer;

public class ngram {


    public static class ngramMapper
            extends Mapper<Object, Text, Text, IntWritable> {
        private static int n;
        private final static IntWritable one = new IntWritable(1);
        private Text word = new Text();
        public void setup(Context context) throws IOException,InterruptedException{
            n = context.getConfiguration().getInt("n",2);
        }
        public void map(Object key, Text value, Context context
        ) throws IOException, InterruptedException {
            StringTokenizer itr = new StringTokenizer(value.toString());
            int i;
            while (itr.hasMoreTokens()) {	
            	String lines = itr.nextToken();
                if(n <= lines.length()) {
                    for (i = 0; i <= lines.length() - n; i++) {
                        word.set(lines.substring(i, i + n));
                        context.write(word, one);
                    }
                }
                else {
                    word.set(lines);
                    context.write(word, one);
                }
            }
        }
    }

    public static class ngramReducer
            extends Reducer<Text, IntWritable, Text, IntWritable> {
        private IntWritable result = new IntWritable();

        public void reduce(Text key, Iterable<IntWritable> values,
                           Context context
        ) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable val : values) {
                sum += val.get();
            }
            result.set(sum);
            context.write(key, result);
        }
    }

    public static void main(String[] args) throws Exception {
        
        Configuration conf = new Configuration();
        conf.set("n",args[0]);
        Job job = Job.getInstance(conf, "ngram");
        job.setJarByClass(ngram.class);
        job.setMapperClass(ngramMapper.class);
        job.setCombinerClass(ngramReducer.class);
        job.setReducerClass(ngramReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job, new Path(args[1]));
        FileOutputFormat.setOutputPath(job, new Path(args[2]));
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}