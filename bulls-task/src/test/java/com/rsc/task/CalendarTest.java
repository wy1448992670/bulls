package com.rsc.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.AnnualCalendar;

public class CalendarTest {
    public static void simpleTriggerTest() throws SchedulerException {
        //创建一个jobDetail的实例，将该实例与HelloJob Class绑定
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class).withIdentity("myJob").build();
        //创建一个Trigger触发器的实例，定义该job立即执行，并且每2秒执行一次，一直执行
        SimpleTrigger trigger = TriggerBuilder.newTrigger().withIdentity("myTrigger").startNow().withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).repeatForever()).build();
        //创建schedule实例
        StdSchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();
        scheduler.start();
        scheduler.scheduleJob(jobDetail,trigger);

    }
    public static void cronTriggerTest() throws SchedulerException, InterruptedException {
        //jobDetail
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class).withIdentity("cronJob").build();
        //cronTrigger
        //每日的9点40触发任务
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("cronTrigger").withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ? ")).build();
        //1.每日10点15分触发      0 15 10 ？* *
        //2.每天下午的2点到2点59分（正点开始，隔5分触发）       0 0/5 14 * * ?
        //3.从周一到周五每天的上午10点15触发      0 15 10 ? MON-FRI
        //4.每月的第三周的星期五上午10点15触发     0 15 10 ? * 6#3
        //5.2016到2017年每月最后一周的星期五的10点15分触发   0 15 10 ? * 6L 2016-2017
        //Scheduler实例
        StdSchedulerFactory stdSchedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = stdSchedulerFactory.getScheduler();
        scheduler.start();
        scheduler.scheduleJob(jobDetail,cronTrigger);
    }
	public static void main2() throws SchedulerException {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 创建一个JobDetail的实例，将该实例与HelloJob绑定
		JobDetail jobDetail = JobBuilder.newJob(HelloJob.class).withIdentity("zhlJob").build();
		AnnualCalendar holidays = new AnnualCalendar();
		GregorianCalendar nationalDay = new GregorianCalendar(2019, 03, 14); // 排除今天的时间2017年11月27日（月份是从0～11的）
		//holidays.setDayExcluded(nationalDay, true); // 排除的日期，如果为false则为包含*/
		// 创建Scheduler实例
		StdSchedulerFactory stdSchedulerFactory = new StdSchedulerFactory();
		Scheduler scheduler = stdSchedulerFactory.getScheduler();
		// 向Scheduler注册日历
		//scheduler.addCalendar("holidays", holidays, false, false);
		Trigger simpleTrigger = TriggerBuilder.newTrigger().withIdentity("zhlTrigger")
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).repeatForever()) // 每一秒执行一次job
				//.modifiedByCalendar("holidays") // 将我们设置好的Calander与trigger绑定
				.build();
		// 让trigger应用指定的日历规则
		// scheduler.scheduleJob(jobDetail,simpleTrigger);
		System.out.println("现在的时间 ：" + sf.format(new Date()));
		System.out.println("最近的一次执行时间 ：" + sf.format(scheduler.scheduleJob(jobDetail, simpleTrigger))); // scheduler与jobDetail、trigger绑定，并打印出最近一次执行的事件
		scheduler.start();
	}
	
	class HelloJob implements Job{
		@Override
		public void execute(JobExecutionContext context) throws JobExecutionException {
			System.out.println("execute");
			
		}
	}
	public static void main(String[] args) throws SchedulerException, InterruptedException {
		System.out.println("main");
		cronTriggerTest();
	}
}
