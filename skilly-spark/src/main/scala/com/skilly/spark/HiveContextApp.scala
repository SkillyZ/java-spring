package com.skilly.spark

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.hive.HiveContext

/**
  *
  */
object HiveContextApp {
    def main(args: Array[String]): Unit = {

        var path = args(0);

        //创建context
        var sparkConf = new SparkConf();
        sparkConf.setAppName("object SQLContextApp {").setMaster("local[2]");
        var sc = new SparkContext(sparkConf);
        var hiveContext = new HiveContext(sc);

        //相关处理 注意mysql驱动包
        hiveContext.table("emp").show()

        //关闭资源
        sc.stop();
    }

}
