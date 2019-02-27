package com.anshare.project.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MyTask {
    private static final Logger logger = LoggerFactory.getLogger(MyTask.class);

    /**
     * 检查状态1
     */
    //@Scheduled(cron = "0 30 12 * * ?")
    public void checkState1() {

    }

    /**
     * 检查状态2
     */
    //@Scheduled(cron = "0 0 18 * * ?")
    public void checkState2() {
        logger.info(">>>>> cron晚上18:00上传检查开始....");
        logger.info(">>>>> cron晚上18:00上传检查完成....");
    }

}
