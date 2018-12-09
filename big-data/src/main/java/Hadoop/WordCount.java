package Hadoop;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class WordCount {

    public static class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable> {

        private final static IntWritable one = new IntWritable(1);// 初始的单词都是1次，即使重复
        private Text word = new Text();// word表示单词

        /*
         * 重写map方法，读取初试划分的每一个键值对，即行偏移量和一行字符串，key为偏移量，value为该行字符串
         */
        @Override
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            /*
             * 因为每一行就是一个spilt，并会为之生成一个mapper，所以我们的参数，key就是偏移量，value就是一行字符串
             */
            StringTokenizer itr = new StringTokenizer(value.toString());// value是一行的字符串，这里将其切割成多个单词
            while (itr.hasMoreTokens()) {// 多个单词
                word.set(itr.nextToken());// 每个word
                context.write(word, one);// one代表1，最开始每个单词都是1次，context直接将<word,1>写到本地磁盘上
                // write函数直接将两个参数封装成<key,value>
            }
        }
    }

    public static class IntSumReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
        private IntWritable result = new IntWritable();

        /*
         * 重写reduce函数，key为单词，values是reducer从多个mapper中得到数据后进行排序并将相同key组
         * 合成<key.list<V>>中的list<V>，也就是说明排序这些工作都是mapper和reducer自己去做的，
         * 我们只需要专注与在map和reduce函数中处理排序处理后的结果
         */
        @Override
        public void reduce(Text key, Iterable<IntWritable> values, Context context)
                throws IOException, InterruptedException {
            /*
             * 因为在同一个spilt对应的mapper中，会将其进行combine，使得其中单词（key）不重复，然后将这些键值对按照
             * hash函数分配给对应的reducer，reducer进行排序，和组合成list，然后再调用的用户自定义的这个函数，
             * 所以有values
             * 这一Iterable对象，说明，这个reducer排序后有多少个键值对，就会有多少次调用这个算法，每一次都会进行写，
             * 并且key在整个 并行的多个节点中是唯一的
             *
             */
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
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        if (otherArgs.length < 2) {
            System.err.println("Usage: wordcount <in> [<in>...] <out>");
            System.exit(2);
        }
        @SuppressWarnings("deprecation")
        Job job = new Job(conf, "word count");
        job.setJarByClass(WordCount.class);// 本次作业的job
        job.setMapperClass(TokenizerMapper.class);// map函数
        job.setCombinerClass(IntSumReducer.class);// combine的实现个reduce函数一样，都是将相同的单词组合成一个键值对
        job.setReducerClass(IntSumReducer.class);// reduce函数
        job.setOutputKeyClass(Text.class);// 键key的类型，
        job.setOutputValueClass(IntWritable.class);// value的类型
        for (int i = 0; i < otherArgs.length - 1; ++i) {
            FileInputFormat.addInputPath(job, new Path(otherArgs[i]));//输入输出参数的获取，说明可以是多个输入文件
        }
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[otherArgs.length - 1]));//参数的最后一个是输出文件
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}