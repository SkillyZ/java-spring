package com.skilly.timer;

import java.util.Calendar;
import java.util.TimerTask;

/**
 * Created by ${1254109699@qq.com} on 2018/1/15.
 */
/**
 * 需定时调度的业务逻辑类
 * Created by ChangComputer on 2017/5/24.
 */
// 继承 TimerTask 类
public class MyTimerTask extends TimerTask{
    private String name;

    public MyTimerTask(String name){
        this.name = name;
    }

    // 重写 run 方法
    @Override
    public void run() {

        // 打印当前 name 的内容
        System.out.println("Current exec name is : " + name);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
