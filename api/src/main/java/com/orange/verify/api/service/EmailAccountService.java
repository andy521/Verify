package com.orange.verify.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.orange.verify.api.bean.EmailAccount;

import java.util.List;

public interface EmailAccountService extends IService<EmailAccount> {

    List<EmailAccount> getList();

}
