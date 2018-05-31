import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ${skilly} on 2018/2/24.
 */
public class Main {

    public static void main(String[] args) {
 
    }

    public static void testOptional()
    {

        BigDecimal total = new BigDecimal(21);
        BigDecimal totalProcess = new BigDecimal(2);
        int rate = (int)(Math.pow(10, 4) * totalProcess.divide(total, 4, RoundingMode.HALF_EVEN).doubleValue());
        System.out.println(rate);

//        rate = (int)(totalProcess.divide(total, 4, RoundingMode.HALF_EVEN).doubleValue() * Math.pow(10, coinOrder.getAwaitingCurrencyPrecision()));

    }

    public static void testNum()
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

    public static void testInstant(){

        DateTimeFormatter formatter =
                DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
                        .withLocale( Locale.UK )
                        .withZone( ZoneId.systemDefault() );

        Instant instant = Instant.now();
        String output = formatter.format( instant );
        System.out.println("formatter: " + formatter + " with zone: " + formatter.getZone() + " and Locale: " + formatter.getLocale() );
        System.out.println("instant: " + instant );
        System.out.println("output: " + output );
    }

    public static void testRegx()
    {
        List<String> key = new ArrayList<>();

        Pattern pattern = Pattern.compile("\\$\\{(.*?)\\}");
        Matcher matcher = pattern.matcher("您的验证码是${code}，有效期为${hour}小时，请尽快验证");
        while (matcher.find())
        {
            System.out.println(matcher.group(1));
            key.add(matcher.group(1));
        }
    }
}