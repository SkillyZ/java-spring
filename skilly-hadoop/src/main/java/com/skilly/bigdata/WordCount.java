package com.skilly.bigdata;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
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

/**
 * Created by 1254109699@qq.com on 2018/12/9.
 */
public class WordCount {
    public static void main(String[] args) throws Exception {
//        File file = new File("src/main/java/Apriori/test.dat");
        Configuration conf = new Configuration();
        Job job = new Job(conf);
        job.setJarByClass(WordCount.class);
        job.setJobName("wordcount");
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        job.setMapperClass(WordCountMap.class);
        job.setReducerClass(WordCountReduce.class);
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
//        FileInputFormat.addInputPath(job, new Path("src/main/java/Apriori/test.dat"));
//        FileOutputFormat.setOutputPath(job, new Path("src/main/java/Apriori/test.dat"));
        job.waitForCompletion(true);
    }

    public static class WordCountMap extends Mapper<LongWritable, Text, Text, IntWritable> {
        private final IntWritable one = new IntWritable(1);
        private Text word = new Text();

        @Override
        public void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {
            String line = value.toString();
            StringTokenizer token = new StringTokenizer(line);
            while (token.hasMoreTokens()) {
                word.set(token.nextToken());
                context.write(word, one);
            }
        }
    }

    public static  class WordCountReduce extends Reducer<Text, IntWritable, Text, IntWritable> {

        @Override
        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable val : values) {
                sum += val.get();
            }
            context.write(key, new IntWritable(sum));
        }
    }

}

//其实呢很浅显的道理，你只需要为windows下的hadoop配置环境变量告诉系统你的hadoop在那就行了。
//
//        为HADOOP_HOME指定你的hadoop目录所在位置，为你的path添加hadoop的bin目录即可
//
//        此外，你也可以实例化conf以后设定该路径，例如：
//
//        Configuration conf = new Configuration();  // 构造一个用于封装配置参数的对象
//                conf.set("fs.defaultFS", "hdfs://hdp21:9000");
//                conf.set("hadoop.home.dir", "D:/Developer/hadoop-2.8.4");
//
//        即：conf.set("hadoop.home.dir", "D:/Developer/hadoop-2.8.4");设定你的hadoop的目录