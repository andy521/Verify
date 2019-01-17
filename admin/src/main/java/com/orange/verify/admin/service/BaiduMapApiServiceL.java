package com.orange.verify.admin.service;

import com.orange.verify.admin.mapper.BaiduMapApiMapper;
import com.orange.verify.api.bean.BaiduMapApi;
import com.orange.verify.common.ip.BaiduIp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaiduMapApiServiceL {

    @Autowired
    private BaiduMapApiMapper apiMapper;

    public String getIpInfo(String ip) throws Exception {

        if ("127.0.0.1".equals(ip)) {
            return "本地测试";
        }

        try {

            BaiduMapApi single = apiMapper.getSingle();

            String ipInfo = BaiduIp.start(single.getAppkey())
                    .getAddressByIp(ip);

            return ipInfo;

        } catch (Exception e) {
            throw new Exception();
        }
    }

}
