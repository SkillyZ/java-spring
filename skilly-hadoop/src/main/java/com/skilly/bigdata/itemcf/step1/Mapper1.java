package com.skilly.bigdata.itemcf.step1;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author 周闽强 1254109699@qq.com
 * @version 1.0.0
 * @since 18/12/18 下午5:40
 */

public class Mapper1 extends Mapper<LongWritable, Text, Text, Text> {

    private Text outKey = new Text();
    private Text outValue = new Text();

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] values = value.toString().split(",");

        String userId = values[0];
        String itemId = values[1];
        String score = values[2];

        outKey.set(itemId);
        outValue.set(userId + "_" + score);

        context.write(outKey, outValue);
    }
}
