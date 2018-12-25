package com.skilly.spark

import org.apache.spark.sql.SparkSession

/**
  *
  */
object DataFrameApp {
    def main(args: Array[String]): Unit = {
        var spark = SparkSession.builder().appName("DataFrameApp").master("local[2]").getOrCreate();

        var people = spark.read.json("/Users/skilly/Project/Person/java-spring/skilly-spark/data/data.json");

        people.printSchema();

        //输出前20条
        people.show();

        //
        people.select("name").show();

        //查询某几列所有数据 并对咧进行计算
        people.select(people.col("name"), (people.col("age") + 10).as("age2")).show();

        people.filter(people.col("age") > 10);

        people.groupBy("age").count().show();

        spark.stop();
    }
}
