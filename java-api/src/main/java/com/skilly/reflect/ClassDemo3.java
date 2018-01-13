package com.skilly.reflect;

/**
 * Created by ${1254109699@qq.com} on 2018/1/12.
 */
public class ClassDemo3 {
    public static void main(String[] args) {
        String s = "hello";
        ClassUtil.printClassMethodMessage(s);

        Integer n1 = 1;
        ClassUtil.printClassMethodMessage(n1);
    }
}
