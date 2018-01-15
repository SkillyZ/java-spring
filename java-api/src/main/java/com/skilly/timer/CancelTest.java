package com.skilly.timer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

/**
 * Created by ${1254109699@qq.com} on 2018/1/15.
 */
public class CancelTest {

    public static void main(String[] args) throws InterruptedException {
        // 创建Timer实例
        Timer timer = new Timer();
        // 创建TimerTask实例
        MyTimerTask task1 = new MyTimerTask("task1");
        MyTimerTask task2 = new MyTimerTask("task2");
        // 获取当前的执行时间并打印
        Date startTime = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("start time is : " + simpleDateFormat.format(startTime));
        // task1首次执行是距离现在时间3秒后执行，之后每隔2秒执行一次
        // task1首次执行是距离现在时间1秒后执行，之后每隔2秒执行一次
        timer.schedule(task1,3000L,2000L);
        timer.schedule(task2,1000L,2000L);
        System.out.println("current canceled task number is : " + timer.purge());
        // 休眠5秒
        Thread.sleep(2000L);
        // 获取当前的执行时间并打印
        Date cancelTime = new Date();
        System.out.println("cancel time is : " + simpleDateFormat.format(cancelTime));

        // 取消所有任务
        // timer.cancel();

        task2.cancel();
        //System.out.println("Tasks all canceled!");

        System.out.println("current canceled task number is : " + timer.purge());
    }

}