package com.orange.verify.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.orange.verify.api.bean.AccountRegisterLog;

import java.util.List;

public interface AccountRegisterLogService extends IService<AccountRegisterLog> {

    List<String> getBeforeData();

}
