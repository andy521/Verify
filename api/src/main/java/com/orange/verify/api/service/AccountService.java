package com.orange.verify.api.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.orange.verify.api.bean.Account;
import com.orange.verify.api.sr.ServiceResult;
import com.orange.verify.api.vo.AccountVo;
import com.orange.verify.api.vo.open.*;

public interface AccountService extends IService<Account> {

    Page<AccountVo> page(AccountVo accountVo, Page page);

    ServiceResult<String> getPublicKey();

    void saveVerificationCode(AccountVerificationCodeVo accountVerificationCodeVo);

    ServiceResult register(AccountRegisterVo accountRegisterVo);

    ServiceResult login(AccountLoginVo accountLoginVo);

    ServiceResult bindingCard(AccountBindingCardVo accountBindingCardVo);

    ServiceResult bindingCode(AccountBindingCodeVo accountBindingCodeVo);

    ServiceResult updatePassword(AccountUpdatePasswordVo accountUpdatePasswordVo);

}
