package com.skilly.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class HelloScheduler {
	public static void main(String[] args) throws SchedulerException, InterruptedException {

		// 创建一个JobDetail实例，将该实例与HelloJob Class绑定
		JobDetail jobDetail = JobBuilder
				.newJob(HelloJob.class)
				.withIdentity("myjob", "jobgroup1")// 定义标识符
				.usingJobData("message", "hello myjob1")// 传入自定义参数
				.usingJobData("floatJobValue", 3.14F)// 传入自定义参数
				.build();

		System.out.println("jobDetail's name : " + jobDetail.getKey().getName());
		System.out.println("jobDetail's group : " + jobDetail.getKey().getGroup());
		System.out.println("jobDetail's jobClass : " + jobDetail.getJobClass().getName());

		CronTrigger trigger = (CronTrigger) TriggerBuilder
				.newTrigger()
				.withIdentity("myTrigger","trigroup1")// 定义标识符
				.usingJobData("message", "hello mytrigger1")// 传入自定义参数
				.usingJobData("doubleTriggerValue", 2.0D)// 传入自定义参数
//				.startNow()// 定义立即执行
//				.withSchedule(SimpleScheduleBuilder
//						.simpleSchedule()
//						.withIntervalInSeconds(2)
//						.repeatForever())// 定义执行频度
				.withSchedule(
						CronScheduleBuilder.cronSchedule("* * * * * ?"))
				.build();
		// 创建 Scheduler 实例
		SchedulerFactory sfact = new StdSchedulerFactory();
		Scheduler scheduler = sfact.getScheduler();

		// 绑定 JobDetail 和 trigger
		scheduler.scheduleJob(jobDetail, trigger);

		// 执行任务
		scheduler.start();

		// 打印当前的执行时间，格式为2017-01-01 00:00:00
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("Current Time Is : " + sf.format(date));
	}
}
