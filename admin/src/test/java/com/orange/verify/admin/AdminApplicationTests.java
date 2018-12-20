package com.orange.verify.admin;

import com.orange.verify.admin.impl.RedisImpl;
import com.orange.verify.api.bean.Account;
import lombok.extern.java.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log
public class AdminApplicationTests {

    @Autowired
    private RedisImpl redis;

    @Test
    public void contextLoads() {

        Account account = new Account();

        account.setId("32424243");

        redis.save10Minutes("44",account);

        Account o = (Account)redis.getByKey("44");

        System.out.println(o);


    }

}
