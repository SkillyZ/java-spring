import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by ${skilly} on 2018/2/24.
 */
public class Main {

    public static void main(String[] args) {
        com();
    }

    public static void com()
    {

        BigDecimal total = new BigDecimal(21);
        BigDecimal totalProcess = new BigDecimal(2);
        int rate = (int)(Math.pow(10, 4) * totalProcess.divide(total, 4, RoundingMode.HALF_EVEN).doubleValue());
        System.out.println(rate);

//        rate = (int)(totalProcess.divide(total, 4, RoundingMode.HALF_EVEN).doubleValue() * Math.pow(10, coinOrder.getAwaitingCurrencyPrecision()));

    }

    public static void com2()
    {
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