package com.skilly.spark

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SQLContext

/**
  *
  */
object SQLContextApp {
    def main(args: Array[String]): Unit = {

        var path = args(0);

        //创建context
        var sparkConf = new SparkConf();
        sparkConf.setAppName("object SQLContextApp {").setMaster("local[2]");
        var sc = new SparkContext(sparkConf);
        var sqlContext = new SQLContext(sc);

        //相关处理
        var people = sqlContext.read.format("json").load(path);
        people.printSchema();
        people.show();

        //关闭资源
        sc.stop();
    }

}
