package com.skilly.bigdata.itemcf.step1;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 周闽强 1254109699@qq.com
 * @version 1.0.0
 * @since 18/12/18 下午5:41
 */

public class Reducer1 extends Reducer<Text, Text, Text, Text> {

    private Text outKey = new Text();
    private Text outValue = new Text();

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        String itemId = key.toString();

        //uuserId score
        Map<String, Integer> map = new HashMap();
        for (Text value : values) {
            String userId = value.toString().split(",")[0];
            String score = value.toString().split(",")[1];

            if (map.get(userId) == null) {
                map.put(userId, Integer.valueOf(score));
            } else {
                map.put(userId, Integer.valueOf(score) + map.get(userId));
            }
        }

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String userId = entry.getKey();
            String score = String.valueOf(entry.getValue());
            sb.append(userId + "_" + score + ",");
        }
        String line = null;
        if (sb.toString().endsWith(",")) {
            line = sb.substring(0, sb.length() - 1);
        }

        outKey.set(itemId);
        outValue.set(line);
        context.write(outKey, outValue);
    }
}