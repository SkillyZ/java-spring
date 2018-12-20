package com.skilly.bigdata.contentcf.job;

import com.skilly.bigdata.contentcf.step1.MR1;
import com.skilly.bigdata.contentcf.step2.MR2;
import com.skilly.bigdata.contentcf.step3.MR3;

/**
 * @author 周闽强 1254109699@qq.com
 * @version 1.0.0
 * @since 18/12/19 下午8:45
 */

public class JobRunner {

    public static void main(String[] args) {
        int status1 = -1;
        int status2 = -1;
        int status3 = -1;

        status1 = new MR1().run();
        if (status1 == 1) {
            System.out.println("step1运行成功 ，开始运行step2");
            status2 = new MR2().run();
        } else {
            System.out.println("step1运行失败");
        }

        if (status2 == 1) {
            System.out.println("step2运行成功 ，开始运行step3");
            status3 = new MR3().run();
        } else {
            System.out.println("step2运行失败");
        }

        if (status3 == 1) {
            System.out.println("step3运行成功 ，开始运行step4");
        } else {
            System.out.println("step3运行失败");
        }

    }

}
