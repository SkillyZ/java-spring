import java.util.Random;

/**
 * Created by ${skilly} on 2018/3/5.
 */
public class Main2 {

    public static void main(String[] args) {

//        Integer[] randomArr = getRandomArray();

        Integer[] randomArr = new Integer[]{1, 2, 3, 4};
        int maxlength = getMaxLength(randomArr);

        System.out.println("最长的递增序列是：" + maxlength);

    }

    //生成一个0~9随机数1000位的数据；
    private static Integer[] getRandomArray() {
        Random random = new Random();
        Integer[] a = new Integer[1000];
        for (int i = 0; i < 1000; i++) {
            a[i] = random.nextInt(10);
        }
        return a;
    }

    //获得数组连续增长的长度
    private static Integer getMaxLength(Integer[] a) {
        int maxlength = 0;
        int l = 0;
        for (int i = 1; i < a.length; i++) {
            if (a[i] > a[i - 1]) {
                l++;
            } else {
                if (maxlength < l) {
                    maxlength = l;
                }
                l = 0;
            }
        }

        return maxlength == 0 ? 0 : (maxlength + 1);
    }
}
