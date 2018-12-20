package com.skilly.bigdata.usercf.step2;

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
 * @since 18/12/18 下午8:44
 */

public class Mapper2 extends Mapper<LongWritable, Text, Text, Text> {

    private Text outKey = new Text();
    private Text outValue = new Text();

    private List<String> cacheList = new ArrayList();

    private DecimalFormat decimalFormat = new DecimalFormat("0.00");

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        super.setup(context);
        FileReader fr = new FileReader("itemUserScore1");
        BufferedReader br = new BufferedReader(fr);
        String line = null;
        while((line = br.readLine()) != null) {
            cacheList.add(line);
        }

        fr.close();
        br.close();
    }

    @Override
    public void map(LongWritable key, Text text, Context context) throws IOException, InterruptedException {
        String[] rowAndLine = text.toString().split("\t");

        String rowMatrix1 = rowAndLine[0];
        String[] columnValues1 = rowAndLine[1].split(",");
        //计算左侧矩阵行的空间距离
        double denomination1 = 0;
        for (String columnValue : columnValues1) {
            String score = columnValue.split("_")[1];
            denomination1 += Double.valueOf(score) * Double.valueOf(score);
        }
        denomination1 = Math.sqrt(denomination1);

        for(String line : cacheList) {
            String rowMatrix2 = line.toString().split("\t")[0];
            String[] columnValues2 = line.toString().split("\t")[1].split(",");

            double denomination2 = 0;
            for (String columnValue : columnValues2) {
                String score = columnValue.split("_")[1];
                denomination2 += Double.valueOf(score) * Double.valueOf(score);
            }
            denomination2 = Math.sqrt(denomination2);

            //矩阵相乘
            int numerator = 0;
            //遍历左侧矩阵每一列
            for (String columnValue:columnValues1) {
                String column1 = columnValue.split("_")[0];
                String value1 = columnValue.split("_")[1];

                //遍历右侧矩阵每一列
                for (String column2:columnValues2) {
                    if (column2.startsWith(column1 + "_")) {
                        String value2 = column2.split("_")[1];
                        //相加
                        numerator += Integer.valueOf(value1) * Integer.valueOf(value2);
                    }
                }
            }

            double cos = numerator / (denomination1 * denomination2);
            if (cos == 0) {
                continue;
            }

            outKey.set(rowMatrix1);
            outValue.set(rowMatrix2 + "_" + decimalFormat.format(cos));
            context.write(outKey, outValue);
        }
    }
}
