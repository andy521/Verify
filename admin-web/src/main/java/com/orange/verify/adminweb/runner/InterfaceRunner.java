package com.orange.verify.adminweb.runner;

import cn.hutool.core.util.ClassUtil;
import com.orange.verify.adminweb.annotation.Open;
import com.orange.verify.api.bean.InterfaceManagement;
import com.orange.verify.api.redis.RedisKeyConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 全部开放接口全部统一管理，提供参数配置，系统可以后台可配置
 * redis作为存储
 * 系统启动后第一时间执行
 * @author Orange软件
 * @date 2019.3.15
 */
@Component
@Order(value = 1)
public class InterfaceRunner implements CommandLineRunner {
    
    private static final Logger log = LoggerFactory.getLogger(InterfaceRunner.class);

    @Value("${interface.scan.path}")
    private String interfaceScanPath;

    @Autowired
    private RedisTemplate<String,Object> template;

    @Override
    public void run(String... args) throws Exception {

        log.info("正在执行全部接口入存管理");

        Map<String,String> productionInterface = productionInterface();
        InterfaceManagement setting = getSetting();

        for (Map.Entry<String, String> m : productionInterface.entrySet()) {

            String key = m.getKey();
            String remarks = m.getValue();

            Boolean hasKey = template.hasKey(key);
            if (hasKey == false) {
                setting.setKey(key);
                setting.setRemarks(remarks);
                template.opsForValue().set(key,setting);
            } else {
                InterfaceManagement o = (InterfaceManagement) template.opsForValue().get(key);
                if (!remarks.equals(o.getRemarks())) {
                    setting.setKey(key);
                    setting.setRemarks(remarks);
                    template.opsForValue().set(key,setting);
                }
            }

        }

        Set<String> keys = template.keys(RedisKeyConstant.OPEN_INTERFACE + "*");
        for (String key : keys) {
            int l = 0;
            for (Map.Entry<String, String> m : productionInterface.entrySet()) {
                if (m.getKey().equals(key)) {
                    l = 1;
                    break;
                }
            }
            if (l == 0) {
                Boolean delete = template.delete(key);
                if (delete == true) {
                    log.info("删除无用(过期)开放接口成功");
                } else {
                    log.info("删除无用(过期)开放接口失败");
                }
            }
        }

        log.info("执行结束");

    }

    /**
     * 接口地址管理 作为redis键 接口备注作为值
     */
    private Map<String,String> productionInterface() {

        Map<String,String> map = new HashMap<String,String>();

        Set<Class<?>> classes = ClassUtil.scanPackage(interfaceScanPath);

        for (Class c : classes) {

            Method[] methods = c.getMethods();

            for (Method method : methods) {

                Open annotation = method.getAnnotation(Open.class);
                if (annotation != null) {
                    map.put(RedisKeyConstant.OPEN_INTERFACE + c.getName() + "." + method.getName(),annotation.explain());
                }
            }

        }

        return map;
    }

    /**
     * 接口默认控制配置
     * visit 1=可以访问 0=接口关闭
     * ipHandle 1开启ip限流控制 0关闭
     * ipVisits ip访问次数，限制接口在 xx 分钟内容只能访问 xx 次，默认60次
     * ipRedisInterval ip redis缓存多少分钟 默认1分钟
     */
    private InterfaceManagement getSetting() {

        InterfaceManagement interfaceManagement = new InterfaceManagement();
        interfaceManagement.setVisit(1);
        interfaceManagement.setIpHandle(1);
        interfaceManagement.setIpVisits(60L);
        interfaceManagement.setIpRedisInterval(1L);

        return interfaceManagement;
    }


}
