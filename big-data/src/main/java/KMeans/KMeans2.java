package KMeans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 二维数组计算聚类
 */
public class KMeans2 {
    private int maxClusterTimes = 500;//最大迭代次数
    private double threshold = 0.01; //设置阈值
    private static int ClusterNum = 3; //将要分成的类别个数
    private static ArrayList<ArrayList<double[]>> cluster;
    private static double[][] center = new double[ClusterNum][2];
    private static double[][] lastCenter = new double[ClusterNum][2];
    private ArrayList<double[]> dataSet = new ArrayList<double[]>();

    public static void main(String[] args) throws IOException {
        KMeans2 kmeans = new KMeans2(ClusterNum);
        JfreeChartScatter scatter = new JfreeChartScatter();
        kmeans.ExecuteMethod();
        scatter.Scatter(kmeans.getCluster());
    }

    /*
     * 构造函数
     */
    public KMeans2(int clusterNum) {
        ClusterNum = clusterNum;
    }

    /*
     * 主执行方法
     */
    public void ExecuteMethod() throws IOException {
        LoadDataSet();
        initCenters();
        int curClusterTimes = 0;
        do {
            initCluster();
            AllocateCluster();
            lastCenter = center;
            setNewCenter();
            curClusterTimes++;
        }
        while (this.IsCenterChanged(center) || curClusterTimes > maxClusterTimes);
    }

    /*
     * 获取簇
     */
    public ArrayList<ArrayList<double[]>> getCluster() {
        return cluster;
    }

    /*
     * 装载数据 读取文件|手动生成
     */
    private void LoadDataSet(boolean isFile) throws IOException {
        if (isFile) {
            String fileName = "src/main/java/KMeans/testSet.txt";
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferReader = new BufferedReader(fileReader);
            String line = bufferReader.readLine();
            while (!(line.isEmpty() || line == null)) {
                double[] data = new double[2];
                String[] input = line.split("\t");
                data[0] = Double.parseDouble(input[0]);
                data[1] = Double.parseDouble(input[1]);
                line = bufferReader.readLine();
                dataSet.add(data);
            }
            fileReader.close();
            bufferReader.close();
        } else {
            int width = 3;
            int height = 3;
            for (int i = 0; i < 2000; i++) {
                double[] data = new double[2];
                data[0] = (Math.random() * width) + 1;
                data[1] = (Math.random() * height) + 1;
                dataSet.add(data);
            }
        }
    }


    private void LoadDataSet() throws IOException {
        LoadDataSet(false);
    }


    /*
     * 判断簇中心点是否改变  作为算法结束条件
     */
    private boolean IsCenterChanged(double[][] center) {
        for (int i = 0; i < center.length; i++) {
            if (calDistance(center[i][0], lastCenter[i][0]) <= threshold || calDistance(center[i][1], lastCenter[i][1]) <= threshold) {
                return true;
            }
        }

        return false;
    }

    private double calDistance(double x1, double x2) {
        return Math.abs(x1 - x2);
    }

    /*
     * 初始化簇中心
     *
     * 数据打乱进行聚类
     */
    private void initCenters() {
        //// 中心点的设置会导致分类失败
//		center = new double[][]{{-1,-2},{-3,2},{2,4}};

        //初始K个点为数组中的前K个点
        int size = ClusterNum > dataSet.size() ? dataSet.size() : ClusterNum;
        center = new double[size][];
        //对数据进行打乱
        Collections.shuffle(dataSet);
        for (int i = 0; i < size; i++) {
            center[i] = dataSet.get(i);
        }

    }

    /*
     * 初始化簇容器
     */
    private void initCluster() {
        ArrayList<ArrayList<double[]>> initCluster = new ArrayList<ArrayList<double[]>>();
        for (int i = 0; i < ClusterNum; i++) {
            initCluster.add(new ArrayList<double[]>());
        }

        if (cluster != null) {
            cluster.clear();
        }

        cluster = initCluster;
    }

    /*
     * 计算欧式距离  用以根据距离完成分类
     */
    private double CalcDistance(double[] element, double[] center) {
        double x = element[0] - center[0];
        double y = element[1] - center[1];
        double z = x * x + y * y;
        return Math.sqrt(z);
    }

    /*
     * 获取这个节点属于哪个簇
     */
    private int getClusterIndex(double[] distance) {
        double minDistance = distance[0];
        int clusterIndex = 0;
        for (int i = 0; i < distance.length; i++) {
            if (distance[i] < minDistance) {
                minDistance = distance[i];
                clusterIndex = i;
            }
        }

        return clusterIndex;
    }

    /*
     * 分配簇
     */
    private void AllocateCluster() {
        double[] distance = new double[ClusterNum];
        for (double[] data : dataSet) {
            //这里计算出了所有点到 center的距离 所以下面直接按照距离大小进行分配点
            for (int j = 0; j < ClusterNum; j++) {
                distance[j] = this.CalcDistance(data, center[j]);
            }

            int clusterIndex = this.getClusterIndex(distance);

            /*
             * 如果用ArrayList<double[][]>来描述簇也是可行的 但是在这里会很不好处理   不可能为每一个簇都保存一个索引值
             */
            cluster.get(clusterIndex).add(data);
        }
    }

    /*
     * 设置新的簇中心
     */
    private void setNewCenter() {
        center = new double[ClusterNum][2];
        for (int i = 0; i < center.length; i++) {
            if (cluster.get(i).size() != 0) {
                double[] newCenter = new double[2];
                for (int j = 0; j < cluster.get(i).size(); j++) {
                    newCenter[0] += cluster.get(i).get(j)[0];
                    newCenter[1] += cluster.get(i).get(j)[1];
                }

                center[i][0] = newCenter[0] / cluster.get(i).size();
                center[i][1] = newCenter[1] / cluster.get(i).size();
            }
        }
    }
}