package com.skilly.bigdata.matrix.step2;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        super.setup(context);
        FileReader fr = new FileReader("Matrix2");
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
        String[] columnValues = rowAndLine[1].split(",");
        for(String line : cacheList) {
            String rowMatrix2 = line.toString().split("\t")[0];
            String[] columns2 = line.toString().split("\t")[1].split(",");

            //矩阵相乘
            int result = 0;
            //遍历左侧矩阵每一列

            for (String columnValue:columnValues) {
                String column1 = columnValue.split("_")[0];
                String value1 = columnValue.split("_")[1];

                //遍历右侧矩阵每一列
                for (String column2:columns2) {
                    if (column2.startsWith(column1 + "_")) {
                        String value2 = column2.split("_")[1];
                        //相加
                        result += Integer.valueOf(value1) * Integer.valueOf(value2);
                    }
                }
            }

            outKey.set(rowMatrix1);
            outValue.set(rowMatrix2 + "_" + result);
            context.write(outKey, outValue);
        }
    }
}
