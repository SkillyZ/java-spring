package com.skilly.bigdata.itemcf.step5;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 周闽强 1254109699@qq.com
 * @version 1.0.0
 * @since 18/12/18 下午5:40
 */

public class Mapper5 extends Mapper<LongWritable, Text, Text, Text> {

    private Text outKey = new Text();
    private Text outValue = new Text();
    private List<String> cacheList = new ArrayList();

    private DecimalFormat decimalFormat = new DecimalFormat("0.00");

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        super.setup(context);
        FileReader fr = new FileReader("itemUserScore3");
        BufferedReader br = new BufferedReader(fr);
        String line = null;
        while((line = br.readLine()) != null) {
            cacheList.add(line);
        }

        fr.close();
        br.close();
    }

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String itemMatrix1 = value.toString().split("\t")[0];
        String[] userScoreArrayMatrix1 = value.toString().split("\t")[1].split(",");

        for (String line : cacheList) {
            String itemMatrix2 = line.toString().split("\t")[0];
            String[] userScoreArrayMatrix2 = line.toString().split("\t")[1].split(",");
            //如果物品id相同
            if (itemMatrix1.equalsIgnoreCase(itemMatrix2)) {
                //遍历matrix1列
                for (String userScoreMatrix1 : userScoreArrayMatrix1) {
                    boolean flag = false;
                    String userMatrix1 = userScoreMatrix1.split("_")[0];
                    String scoreMatrix1 = userScoreMatrix1.split("_")[1];

                    //遍历matrix2列
                    for (String userScoreMatrix2 : userScoreArrayMatrix2) {
                        String userMatrix2 = userScoreMatrix2.split("_")[0];
                        if (userMatrix1.equalsIgnoreCase(userMatrix2)) {
                            flag = true;
                        }
                    }
                    if (!flag) {
                        outKey.set(userMatrix1);
                        outValue.set(itemMatrix1 + "_" + scoreMatrix1);
                    }
                }
            }
        }

        context.write(outKey, outValue);
    }
}
