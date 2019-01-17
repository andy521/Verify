package com.orange.verify.api.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.orange.verify.api.bean.AccountLoginLog;
import com.orange.verify.api.vo.AccountLoginLogVo;

import java.util.List;

public interface AccountLoginLogService extends IService<AccountLoginLog> {

    List<String> getBeforeData(String softId);

    Page<AccountLoginLogVo> page(AccountLoginLogVo accountLoginLog, Page page);

}
