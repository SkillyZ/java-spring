package com.skilly.spark

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{SQLContext, SparkSession}

object SparkSessionApp {
    def main(args: Array[String]): Unit = {

        val spark = SparkSession
                .builder()
                .appName("Spark SQL basic example")
                .master("local[2]")
//                .config("spark.some.config.option", "some-value")
                .getOrCreate()

        var people = spark.read.json("/Users/skilly/Project/Person/java-spring/skilly-spark/data/data.json");
        people.show();

        spark.stop();
    }
}
