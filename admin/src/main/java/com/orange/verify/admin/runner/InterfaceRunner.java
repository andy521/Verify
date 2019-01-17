package com.orange.verify.admin.runner;

import com.orange.verify.api.bean.InterfaceManagement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 全部开放接口全部统一管理，提供参数配置，系统可以后台可配置
 * redis作为存储
 * 系统启动第一时间执行
 * @author Orange
 */
@Component
@Order(value = 1)
public class InterfaceRunner implements CommandLineRunner {
    
    private static final Logger log = LoggerFactory.getLogger(InterfaceRunner.class);

    @Autowired
    private RedisTemplate<String,Object> template;

    private static final String PREFIX = "orange.verify:open.interface:";

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

        Set<String> keys = template.keys(PREFIX + "*");
        for (String key : keys) {
            int l = 0;
            for (Map.Entry<String, String> m : productionInterface.entrySet()) {
                if (m.getKey().equals(key)) {
                    l = 1;
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

        map.put(PREFIX + "com.orange.verify.adminweb.controller.AccountController.getPublicKey",
                "获取RSA公钥");
        map.put(PREFIX + "com.orange.verify.adminweb.controller.AccountController.getVerificationCode",
                "获取注册验证码");
        map.put(PREFIX + "com.orange.verify.adminweb.controller.AccountController.register",
                "用户-注册");
        map.put(PREFIX + "com.orange.verify.adminweb.controller.AccountController.login",
                "用户-登陆");
        map.put(PREFIX + "com.orange.verify.adminweb.controller.AccountController.bindingCard",
                "用户-绑定卡密");
        map.put(PREFIX + "com.orange.verify.adminweb.controller.AccountController.bindingCode",
                "用户-绑定机器");
        map.put(PREFIX + "com.orange.verify.adminweb.controller.AccountController.updatePassword",
                "用户-修改密码");

        map.put(PREFIX + "com.orange.verify.adminweb.controller.SoftController.getSoftDesc",
                "获取软件信息");

        map.put(PREFIX + "com.orange.verify.adminweb.controller.SoftLeaveMessageController.create",
                "用户-提交留言(反馈)");

        map.put(PREFIX + "com.orange.verify.adminweb.controller.CardController.getCardTimeLimit",
                "用户-查看卡密什么时候开始用的，什么时候结束的");

        map.put(PREFIX + "com.orange.verify.adminweb.controller.SoftVersionsController.getVersions",
                "获取软件版本信息");

        map.put(PREFIX + "com.orange.verify.adminweb.controller.UserController.login",
                "系统管理员-登陆");
        map.put(PREFIX + "com.orange.verify.adminweb.controller.UserController.logout",
                "系统管理员-退出");

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
