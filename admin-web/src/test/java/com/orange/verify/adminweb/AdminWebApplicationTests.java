package com.orange.verify.adminweb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminWebApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void contextLoads() throws InterruptedException {

        long count = redisTemplate.opsForValue().increment("1", 1);
        if (count == 1) {
            redisTemplate.expire("1", 3, TimeUnit.MINUTES);
        }

        Thread.sleep(1000);
        redisTemplate.opsForValue().increment("1", 1);
        Thread.sleep(5000);
        redisTemplate.opsForValue().increment("1", 1);

        Object o = redisTemplate.opsForValue().get("1");
        System.out.println(o);

        Long expire = redisTemplate.getExpire("1");
        System.out.println(expire);

    }

}
