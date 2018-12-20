package com.skilly.bigdata.usercf.step3;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author 周闽强 1254109699@qq.com
 * @version 1.0.0
 * @since 18/12/18 下午5:40
 */

public class Mapper3 extends Mapper<LongWritable, Text, Text, Text> {

    private Text outKey = new Text();
    private Text outValue = new Text();

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] rowAndLine = value.toString().split("\t");

        String row = rowAndLine[0];
        String[] lines = rowAndLine[1].split(",");
        for (int i = 0; i < lines.length; i++) {
            String column = lines[i].split("_")[0];
            String valueStr = lines[i].split("_")[1];

            outKey.set(column);
            outValue.set(row + "_" + valueStr);
            context.write(outKey, outValue);
        }
    }
}
