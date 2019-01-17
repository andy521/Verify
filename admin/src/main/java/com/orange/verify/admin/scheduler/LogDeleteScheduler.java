package com.orange.verify.admin.scheduler;

import com.orange.verify.admin.mapper.AccountLoginLogMapper;
import com.orange.verify.admin.mapper.AccountRegisterLogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 定时执行删除登陆 注册日志 ，10天前的分段删除
 * 10天前的数据比如1万条，每次只删除100条
 */
@Configuration
@EnableScheduling
public class LogDeleteScheduler {

    private static final Logger log = LoggerFactory.getLogger(LogDeleteScheduler.class);

    @Autowired
    private AccountLoginLogMapper accountLoginLogMapper;

    @Autowired
    private AccountRegisterLogMapper accountRegisterLogMapper;

    @Scheduled(fixedRate = 3600000)
    public void logDeleteTask() {

        int accountLoginLog = accountLoginLogMapper.deleteLog();

        int accountRegisterLog = accountRegisterLogMapper.deleteLog();

        log.info("登陆日志(本次删除日志条数): " + accountLoginLog);
        log.info("注册日志(本次删除日志条数): " + accountRegisterLog);

    }

}
