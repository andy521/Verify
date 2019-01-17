package com.orange.verify.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisImpl {

    private final String PREFIX = "orange.verify:admin:";

    @Autowired
    private RedisTemplate<String,Object> template;

    public void save10Minutes(String key, Object value) {
        template.opsForValue().set(PREFIX + key,value,10, TimeUnit.MINUTES);
    }

    public void save1Hours(String key, Object value) {
        template.opsForValue().set(PREFIX + key,value,1, TimeUnit.HOURS);
    }

    public void save(String key, Object value) {
        template.opsForValue().set(PREFIX + key,value);
    }

    public Object getByKey(String key) {
        return template.opsForValue().get(PREFIX + key);
    }

    public boolean deleteByKey(String key) {
        return template.delete(PREFIX + key);
    }

}
