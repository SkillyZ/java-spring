package com.skilly.timer;

/**
 * Created by ${1254109699@qq.com} on 2018/1/15.
 */
import java.util.Timer;

/**
 * 定时调度类
 * Created by ChangComputer on 2017/5/24.
 */
public class MyTimer {

    public static void main(String[] args){

        // 创建一个 Timer 实例
        Timer timer = new Timer();

        // 创建一个 MyTimerTask 实例
        MyTimerTask myTimerTask = new MyTimerTask("No.1");

        // 通过 Timer 定时定频率调用 MyTimerTask 的业务逻辑
        // 即第一次执行是在当前时间的两秒之后，之后每隔一秒钟执行一次
        timer.schedule(myTimerTask,2000L,1000L);
    }
}