package com.orange.verify.api.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.orange.verify.api.bean.AccountRegisterLog;
import com.orange.verify.api.vo.AccountRegisterLogVo;

import java.util.List;

public interface AccountRegisterLogService extends IService<AccountRegisterLog> {

    List<String> getBeforeData(String softId);

    Page<AccountRegisterLogVo> page(AccountRegisterLog accountRegisterLog, Page page);

}
