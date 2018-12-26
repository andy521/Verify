package com.orange.verify.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.orange.verify.api.bean.AccountLoginLog;

import java.util.List;

public interface AccountLoginLogService extends IService<AccountLoginLog> {

    List<String> getBeforeData();

}
