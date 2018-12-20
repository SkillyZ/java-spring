package com.skilly.bigdata.usercf.step5;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author 周闽强 1254109699@qq.com
 * @version 1.0.0
 * @since 18/12/18 下午5:41
 */

public class Reducer5 extends Reducer<Text, Text, Text, Text> {

    private Text outKey = new Text();
    private Text outValue = new Text();

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        StringBuilder sb = new StringBuilder();
        for (Text text : values) {
            sb.append(text + ",");
        }

        String line = null;
        if (sb.toString().endsWith(",")) {
            line = sb.substring(0, sb.length() - 1);
        }

        outKey.set(key);
        outValue.set(line);

        context.write(outKey, outValue);
    }
}