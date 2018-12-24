package com.orange.verify.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.orange.verify.api.bean.BaiduMapApi;

public interface BaiduMapApiService extends IService<BaiduMapApi> {

    boolean create(BaiduMapApi baiduMapApi);

}
