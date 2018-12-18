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
import org.apache.hadoop.util.GenericOptionsParser;

import java.io.IOException;
import java.util.StringTokenizer;

import static com.skilly.bigdata.HadoopUnitl.cat;
import static com.skilly.bigdata.HadoopUnitl.removeFile;

/**
 * Created by 1254109699@qq.com on 2018/12/9.
 */
public class WordCount {
    public static void main(String[] args) throws Exception {
//        File file = new File("src/main/java/Apriori/test.dat");

//        System.setProperty("hadoop.home.dir", "E:\\javaws\\hadoop-2.6.0");
        Configuration conf = new Configuration();

        conf.set("fs.default.name", "hdfs://hadoop-master:9000");
//        conf.set("fs.defaultFS", "hdfs://"+namenode+":8020");// 指定namenode
        conf.set("mapreduce.app-submission.cross-platform", "true"); // 配置使用跨平台提交任务
        conf.set("mapreduce.framework.name", "yarn"); // 指定使用yarn框架
        conf.set("mapred.jar","target/skilly-hadoop-1.0-SNAPSHOT");
//        conf.addResource("classpath:/hadoop/core-site.xml");
//        conf.set("yarn.resourcemanager.address", resourcenode+":8032"); // 指定resourcemanager
//        conf.set("yarn.resourcemanager.scheduler.address", schedulernode+":8030");// 指定资源分配器
//        conf.set("mapreduce.jobhistory.address", jobhistorynode+":10020");// 指定historyserver

        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        if (otherArgs.length < 2) {
            System.err.println("Usage: wordcount <in> [<in>...] <out>");
            System.exit(2);
        }

        removeFile(conf, otherArgs[otherArgs.length - 1]);

        Job job = new Job(conf);
        job.setJarByClass(WordCount.class);
        job.setJobName("wordcount");
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        job.setMapperClass(WordCountMap.class);
        job.setReducerClass(WordCountReduce.class);
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);
//      job.setCacheFiles(new URI[]{new Path("/20180312/cache_for_111.txt#cache111").toUri()});
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
//        FileInputFormat.addInputPath(job, new Path("src/main/java/Apriori/test.dat"));
//        FileOutputFormat.setOutputPath(job, new Path("src/main/java/Apriori/test.dat"));

        if (job.waitForCompletion(true)) {
            cat(conf, otherArgs[1] + "/part-r-00000");
            System.out.println("success");
        } else {
            System.out.println("fail");
        }

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
