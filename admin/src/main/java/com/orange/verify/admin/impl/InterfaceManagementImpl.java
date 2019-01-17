package com.orange.verify.admin.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.orange.verify.api.bean.InterfaceManagement;
import com.orange.verify.api.service.InterfaceManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class InterfaceManagementImpl implements InterfaceManagementService {

    @Autowired
    private RedisTemplate<String,Object> template;

    private static final String PREFIX = "orange.verify:open.interface:";

    @Override
    public List<InterfaceManagement> getAll() throws Exception {

        List<InterfaceManagement> interfaceManagements = new ArrayList<>();

        Set<String> keys = template.keys(PREFIX + "*");
        for (String key : keys) {

            InterfaceManagement interfaceManagement = (InterfaceManagement) template.opsForValue().get(key);
            interfaceManagements.add(interfaceManagement);
        }

        return interfaceManagements;
    }

    @Override
    public void closeInterface(String key,Integer on) throws Exception {

        InterfaceManagement interfaceManagementRedis = (InterfaceManagement) template.opsForValue().get(key);

        interfaceManagementRedis.setVisit(on);

        template.opsForValue().set(key,interfaceManagementRedis);
    }

    @Override
    public InterfaceManagement getSingle(String key) throws Exception {

        return (InterfaceManagement) template.opsForValue().get(key);
    }

    @Override
    public void update(InterfaceManagement interfaceManagement) throws Exception {

        InterfaceManagement interfaceManagementRedis = (InterfaceManagement) template.opsForValue().get(interfaceManagement.getKey());

        interfaceManagementRedis.setIpVisits(interfaceManagement.getIpVisits());
        interfaceManagementRedis.setIpRedisInterval(interfaceManagement.getIpRedisInterval());

        template.opsForValue().set(interfaceManagement.getKey(),interfaceManagementRedis);
    }

    @Override
    public void ipHandle(String key,Integer on) throws Exception {

        InterfaceManagement interfaceManagementRedis = (InterfaceManagement) template.opsForValue().get(key);

        interfaceManagementRedis.setVisit(on);

        template.opsForValue().set(key,interfaceManagementRedis);
    }

}
