package com.skilly.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by ${1254109699@qq.com} on 2018/1/19.
 */
public class Main {
    static void customer(ProxyDynamicInterface pi) {
        pi.say();
    }

    public static void main(String[] args) {
        RealDynamicObject real = new RealDynamicObject();
        ProxyDynamicInterface proxy = (ProxyDynamicInterface) Proxy.newProxyInstance(
                ProxyDynamicInterface.class.getClassLoader(),
                new Class[]{ProxyDynamicInterface.class},
                new ProxyDynamicObject(real));
        customer(proxy);
    }
}


interface ProxyDynamicInterface {
    void say();
}

//被代理类
class RealDynamicObject implements ProxyDynamicInterface {
    public void say() {
        System.out.println("i'm talking");
    }
}

//代理类，实现InvocationHandler 接口
class ProxyDynamicObject implements InvocationHandler {
    private Object proxied = null;

    public ProxyDynamicObject() {

    }

    public ProxyDynamicObject(Object proxied) {
        this.proxied = proxied;
    }

    public Object invoke(Object arg0, Method arg1, Object[] arg2) throws Throwable {
        System.out.println("hello");
        return arg1.invoke(proxied, arg2);
    }

    ;
}
