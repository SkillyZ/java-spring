package KMeans;

import java.util.List;

public class KMeans extends KMeansClustering<XYbean> {

    public static void main(String[] args) {

        int width = 600;
        int height = 400;
        int K = 34;
        KMeans xyCluster = new KMeans();
        for (int i = 0; i < 200000; i++) {
            int x = (int) (Math.random() * width) + 1;
            int y = (int) (Math.random() * height) + 1;
            xyCluster.addRecord(new XYbean(x, y));
        }
        xyCluster.setK(K);
        long a = System.currentTimeMillis();
        List<List<XYbean>> cresult = xyCluster.clustering();
        List<XYbean> center = xyCluster.getClusteringCenterT();
        System.out.println(center);
        long b = System.currentTimeMillis();
        System.out.println("耗时：" + (b - a) + "ms");
//        new ImgUtil().drawXYbeans(width, height, cresult, "d:/2.png", 0, 0);
    }

        @Override
    public double similarScore(XYbean o1, XYbean o2) {
        double distance = Math.sqrt((o1.getX() - o2.getX()) * (o1.getX() - o2.getX()) + (o1.getY() - o2.getY()) * (o1.getY() - o2.getY()));
        return distance * -1;
    }

    @Override
    public boolean equals(XYbean o1, XYbean o2) {
        return o1.getX() == o2.getX() && o1.getY() == o2.getY();

    }

    @Override
    public XYbean getCenterT(List<XYbean> list) {
        int x = 0;
        int y = 0;

        for (XYbean xy : list) {
            x += xy.getX();
            y += xy.getY();
        }
        x = x / list.size();
        y = y / list.size();

        return new XYbean(x, y);

    }
}
