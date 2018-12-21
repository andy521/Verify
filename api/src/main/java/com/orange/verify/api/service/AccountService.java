package com.orange.verify.api.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.orange.verify.api.bean.Account;
import com.orange.verify.api.model.ServiceResult;
import com.orange.verify.api.vo.AccountVo;
import com.orange.verify.api.vo.open.AccountRegisterVo;

import java.util.HashMap;

public interface AccountService extends IService<Account> {

    Page<AccountVo> page(AccountVo accountVo, Page page);

    ServiceResult<String> getPublicKey();

    ServiceResult<Integer> register(AccountRegisterVo accountRegisterVo);

}
