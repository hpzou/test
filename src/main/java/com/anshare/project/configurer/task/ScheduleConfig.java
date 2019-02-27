package com.anshare.project.configurer.task;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
* @ClassName ScheduleConfig
* @Description 定时任务配置类
* @Author wukunfan
* @Date 2018/11/17 18:19
* @UpdateUser:
* @UpdateDate:     2018/11/17 18:19
* @UpdateRemark:   修改内容
* @Version 1.0
**/
@Configuration
@EnableScheduling
public class ScheduleConfig implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
    }

    @Bean(destroyMethod="shutdown")
    public ExecutorService taskExecutor() {
        return Executors.newScheduledThreadPool(5);
    }
}
