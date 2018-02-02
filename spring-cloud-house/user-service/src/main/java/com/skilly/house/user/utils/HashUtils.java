package com.skilly.house.user.utils;

import com.google.common.base.Throwables;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * Created by ${1254109699@qq.com} on 2018/2/2.
 */
public class HashUtils {


    private static final HashFunction FUNCTION = Hashing.md5();

    private static final HashFunction MURMUR_FUNC = Hashing.murmur3_128();

    private static final String SALT = "skilly.com";

    public static String encryPassword(String password) {
        HashCode code = FUNCTION.hashString(password + SALT, Charset.forName("UTF-8"));
        return code.toString();
    }

    public static String hashString(String input) {
        HashCode code = null;
        try {
            code = MURMUR_FUNC.hashBytes(input.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            Throwables.propagate(e);
        }
        return code.toString();
    }

    public static void main(String[] args) {
        System.out.println(encryPassword("111111"));
    }
}
