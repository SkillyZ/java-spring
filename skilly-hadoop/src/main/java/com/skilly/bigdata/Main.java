package com.skilly.bigdata;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.net.URI;

/**
 * @author 周闽强 1254109699@qq.com
 * @version 1.0.0
 * @since 18/12/14 上午10:11
 */

public class Main {
    //hdfs当前活跃的namenode url
    public static String hdfsUrl="hdfs://node2.hadoop.ptbird.cn:8020";
    //获取hdfs的句柄
    public static FileSystem getHdfs()throws Exception{
        //获取配置文件信息
        Configuration conf=new Configuration();
        //获取文件系统
        FileSystem hdfs=FileSystem.get(URI.create(hdfsUrl),conf);
        return hdfs;
    }
    //读取文件信息
    public static void testRead(Path p) throws Exception{
        FileSystem hdfs=getHdfs();
        //打开文件流
        FSDataInputStream inStream=hdfs.open(p);
        //读取文件内容到控制台显示
        IOUtils.copyBytes(inStream, System.out,4096,false);
        //关闭文件流
        IOUtils.closeStream(inStream);
    }


    public static void main(String[] args) throws Exception {

    }

}
