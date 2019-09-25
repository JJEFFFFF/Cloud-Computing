import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

public class test1 {

	public static void main(String[] args) {
		class logMapper extends Mapper<Object, Text, Text, IntWritable> {
			private final IntWritable one = new IntWritable(1);
			private Text word = new Text();
			public void map(Object key,Text value,Context context) 
					throws IOException,InterruptedException{
				String line = String.valueOf(value);
	            int firstquote = line.indexOf("\"");
	            int secondquote = line.indexOf("\"",firstquote+1);
	            if(firstquote != -1 && secondquote != -1) {
	            String r = line.substring(firstquote+1,secondquote);
	            String[] r1 = r.split(" ");
	            String path = r1[1];
	            word.set(path);
	            context.write(word, one);
	            System.out.println(path);
	            }
			}
		}
		
	}
	
}
