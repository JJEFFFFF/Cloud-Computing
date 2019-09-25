package CCProject2.CCProject2;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

import com.google.re2j.Pattern;

import scala.Tuple2;

import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;

public class logQ3 {

	public static void getUrl(String s) {
		class getHits implements Function<String, Tuple2<String, Integer>>{
		public Tuple2<String, Integer> apply(String s){
			Pattern p = Pattern.compile("\"\\w+ ([^ ]*) ");
			Matcher
			
		}
		}
	}
	
	public static void main(String[] args) throws IOException {
		String inputFile = "access_log";
		String outputFile = "hw2/output/part3/q3res";
		Configuration fileConf = new Configuration();
		FileSystem fs = FileSystem.get(fileConf);
		if(!fs.exists(new Path(inputFile))) {
			System.out.println("File don't exist, please check your path!");
		}
		if(fs.exists(new Path(outputFile))) {
			fs.delete(new Path(outputFile));
		}
		long startTime = System.currentTimeMillis();
		
		SparkConf sConf = new SparkConf().setAppName("logQ3");
		JavaSparkContext jSC = new JavaSparkContext(sConf);
		JavaRDD<String> input = jSC.textFile(inputFile).repartition(3);
		JavaRDD<String> term = input.flatMap(s -> Arrays.asList(a))
		
		long endTime = System.currentTimeMillis();
		long totalTime = (endTime - startTime)/1000;
		System.out.println("----------------------");
		System.out.println("Timetaken:" + totalTime);
	}
	
	
}
