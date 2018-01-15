package com.skilly.timer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimerTask;

/**
 * Created by ${1254109699@qq.com} on 2018/1/15.
 * 需定时调度的业务逻辑类
 * Created by ChangComputer on 2017/5/24.
 */
public class MyTimerTask extends TimerTask{
    private String name;
    private int count = 3;

    public MyTimerTask(String name){
        this.name = name;
    }

    // 重写 run 方法
    @Override
    public void run() {
        if (count < 3) {
            // 以yyyy-MM-dd HH:mm:ss的格式打印当前执行时间
            // 如2016-11-11 00:00:00
            Calendar calendar = Calendar.getInstance();
            // 定义日期格式
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("Current exec time is : " + simpleDateFormat.format(calendar.getTime()));

            // 打印当前 name 的内容
            System.out.println("Current exec name is : " + name);
            count--;
        } else {

            System.out.println("cancel");
            cancel();
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}