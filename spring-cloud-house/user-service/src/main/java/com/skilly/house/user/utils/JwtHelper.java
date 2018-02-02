package com.skilly.house.user.utils;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.collect.Maps;

//commons-lang包中对我们有用的类主要有:
//        1.StringUtils 该类主要提供对字符串的操作,对null是安全的,主要提供了字符串查找,替换,分割,去空白,去掉非法字符等等操作
//        2.ObjectUtils 主要是对null进行安全处理,可以设置为null时的默认返回值,比较相等时是调用对象的equals方法,因此需要对对象进行方法进行覆盖
//        3.SystemUtils 主要获取一些系统属性,例如工作目录等等
//        4.DateUtils/CalendarUtils 主要提供了对日期的操作,包括日期加减,日期格式化,日期比较,一定时间范围内日期的迭代等等
//        5.StopWatch 提供秒表的计时,暂停等功能
//        6. EqualsBuilder/HashCodeBuilder提供了方便的方法来覆盖equals() 和hashCode()方法
//        7.以Range结尾的类主要提供一些范围的操作,包括判断某些字符,数字等是否在这个范围以内
//        8.ArrayUtils 提供了数组的复制,查找,获取子数组,反转等功能
public class JwtHelper {

    private static final String SECRET = "session_secret";

    private static final String ISSUER = "skilly_user";


    public static String genToken(Map<String, String> claims) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTCreator.Builder builder = JWT.create().withIssuer(ISSUER).withExpiresAt(DateUtils.addDays(new Date(), 1));
            claims.forEach((k, v) -> builder.withClaim(k, v));
            return builder.sign(algorithm).toString();
        } catch (IllegalArgumentException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, String> verifyToken(String token) {
        Algorithm algorithm = null;
        try {
            algorithm = Algorithm.HMAC256(SECRET);
        } catch (IllegalArgumentException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISSUER).build();
        DecodedJWT jwt = verifier.verify(token);
        Map<String, Claim> map = jwt.getClaims();
        Map<String, String> resultMap = Maps.newHashMap();
        map.forEach((k, v) -> resultMap.put(k, v.asString()));
        return resultMap;
    }

}
