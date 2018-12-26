package com.skilly.spark

import java.sql.DriverManager


/**
  * 通过jdbc 方式
  */
object SparkSQLThriftServerApp {
    def main(args: Array[String]): Unit = {
        Class.forName("org.apache.hive.jdbc.HiveDriver");

        val conn = DriverManager.getConnection("jdbc:hive2://hadoop-master:10000", "hadoop", "");
        val pstmt = conn.prepareStatement("select empno, ename, sal from emp");
        val rs = pstmt.executeQuery();
        while (rs.next()) {
            println("empno :" + rs.getInt("empno")
            + "ename :" + rs.getString("ename")
            + "sal :" + rs.getDouble("sal") );
        }

        rs.close();
        pstmt.close();
        conn.close();
    }
}
