package com.orange.verify.admin.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.orange.verify.admin.mapper.EmailAccountMapper;
import com.orange.verify.api.bean.EmailAccount;
import com.orange.verify.api.service.EmailAccountService;

@Service
public class EmailAccountImpl extends ServiceImpl<EmailAccountMapper, EmailAccount> implements EmailAccountService {



}
