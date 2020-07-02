package com.yanzhen.staservice.schedule;

import com.yanzhen.staservice.service.StatisticsDailyService;
import com.yanzhen.staservice.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduledTask {

    @Autowired
    private StatisticsDailyService statisticsDailyService;

//    @Scheduled(cron = "0/2 * * * * ?")
//    public void task1(){
//        System.out.println("Task1执行了");
//    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void task2(){
        statisticsDailyService.countRegister(DateUtil.formatDate(DateUtil.addDays(new Date(),-1)));
    }
}
