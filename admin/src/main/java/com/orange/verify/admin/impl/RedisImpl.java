package com.orange.verify.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class RedisImpl {

    private final String PREFIX = "com.orange.verify.";

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

    public boolean shortVerificationTime(String key, long millisecond) {
        Map<String,Object> v = (HashMap<String, Object>) this.getByKey(key);
        if (v != null) {
            Long createDate = (Long) v.get("createDate");
            long totalTime = (System.currentTimeMillis() - createDate);
            //小于多少毫秒 不能通过
            if (totalTime < millisecond) {
                return false;
            }
        }
        return true;
    }
    public void saveToShortVerificationTime(String key) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("createDate",System.currentTimeMillis());
        this.save10Minutes(key,map);
    }

    public Object getByKey(String key) {
        return template.opsForValue().get(PREFIX + key);
    }

    public boolean deleteByKey(String key) {
        return template.delete(PREFIX + key);
    }

}
