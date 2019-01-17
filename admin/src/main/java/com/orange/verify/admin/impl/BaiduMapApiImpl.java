package com.orange.verify.admin.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.orange.verify.admin.mapper.BaiduMapApiMapper;
import com.orange.verify.api.bean.BaiduMapApi;
import com.orange.verify.api.service.BaiduMapApiService;
import com.orange.verify.common.ip.BaiduIp;

@Service
public class BaiduMapApiImpl extends ServiceImpl<BaiduMapApiMapper, BaiduMapApi> implements BaiduMapApiService {

    @Override
    public boolean create(BaiduMapApi baiduMapApi) {

        int baiduMapApiCount = super.baseMapper.getBaiduMapApiCount();
        if (baiduMapApiCount == 0) {
            if (super.baseMapper.insert(baiduMapApi) == 1) {
                return true;
            }
        }

        return false;
    }

}
