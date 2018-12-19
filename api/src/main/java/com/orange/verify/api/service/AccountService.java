package com.orange.verify.api.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.orange.verify.api.bean.Account;
import com.orange.verify.api.vo.AccountVo;

public interface AccountService extends IService<Account> {

    Page<AccountVo> page(AccountVo accountVo, Page page);

}
