/**
 * Created by ${skilly} on 2018/2/24.
 */
public class Main {

    public static void main(String[] args) {
        Long ll = 1L;
        Long lll = 1213123L;
        double tt = (ll.doubleValue() / lll);
        System.out.println(tt);
        tt = tt * Math.pow(10, 8);
        System.out.println(tt);
        Long ttt = Math.round(tt);
        System.out.println(ttt);
    }
}