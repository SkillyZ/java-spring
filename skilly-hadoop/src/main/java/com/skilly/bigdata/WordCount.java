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

        System.setProperty("hadoop.home.dir", "E:\\javaws\\hadoop-2.6.0");
        Configuration conf = new Configuration();

        conf.set("fs.default.name", "hdfs://master:9000");
        conf.set("mapreduce.app-submission.cross-platform", "true");
        conf.set("mapreduce.framework.name", "yarn");
        conf.set("mapred.jar","E:\\javaws\\WordC\\WordC-1.0-SNAPSHOT-jar-with-dependencies.jar");

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

//System.setProperty("hadoop.home.dir", "E:\\javaws\\hadoop-2.6.0");//如果没有这句话，会报找不到winutils.exe的错误，也不知道是不是由于我修改了环境变量之后没有重启的原因。
//        Configuration conf = new Configuration();
//        conf.set("fs.default.name", "hdfs://master:9000");
//        conf.set("mapreduce.app-submission.cross-platform", "true");//意思是跨平台提交，在windows下如果没有这句代码会报错 "/bin/bash: line 0: fg: no job control"，去网上搜答案很多都说是linux和windows环境不同导致的一般都是修改YarnRunner.java，但是其实添加了这行代码就可以了。
//        conf.set("mapreduce.framework.name", "yarn");//集群的方式运行，非本地运行。
//        conf.set("mapred.jar","E:\\javaws\\WordC\\WordC-1.0-SNAPSHOT-jar-with-dependencies.jar");
