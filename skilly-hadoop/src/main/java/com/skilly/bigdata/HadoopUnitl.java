package com.skilly.bigdata;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author 周闽强 1254109699@qq.com
 * @version 1.0.0
 * @since 18/12/14 上午10:39
 */

public class HadoopUnitl {

    /**
     * 删除指定目录
     *
     * @param conf
     * @param dirPath
     *
     * @throws IOException
     */
    public static boolean removeFile(Configuration conf, String dirPath) throws IOException {
        boolean delResult = false;
//        FileSystem fs = FileSystem.get(conf);
        Path targetPath = new Path(dirPath);
        FileSystem fs = targetPath.getFileSystem(conf);
        if (fs.exists(targetPath)) {
            delResult = fs.delete(targetPath, true);
            if (delResult) {
                System.out.println(targetPath + " has been deleted sucessfullly.");
            } else {
                System.out.println(targetPath + " deletion failed.");
            }
        }
        return delResult;
    }

    /**
     * 输出指定文件内容
     *
     * @param conf     HDFS配置
     * @param filePath 文件路径
     *
     * @return 文件内容
     *
     * @throws IOException
     */
    public static void cat(Configuration conf, String filePath) throws IOException {
//        FileSystem fileSystem = FileSystem.get(conf);
        InputStream in = null;
        Path file = new Path(filePath);
        FileSystem fileSystem = file.getFileSystem(conf);
        try {
            //todo job一起运行为了方便先补关闭
            in = fileSystem.open(file);
            IOUtils.copyBytes(in, System.out, 4096, false);
        } finally {
            if (in != null) {
//                IOUtils.closeStream(in);
            }
        }
    }
}
