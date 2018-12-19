package com.orange.verify.admin.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.orange.verify.admin.mapper.EmailAccountMapper;
import com.orange.verify.api.bean.EmailAccount;
import com.orange.verify.api.service.EmailAccountService;

import java.util.List;

@Service
public class EmailAccountImpl extends ServiceImpl<EmailAccountMapper, EmailAccount> implements EmailAccountService {

    @Override
    public List<EmailAccount> getList() {

        QueryWrapper<EmailAccount> createDate = new QueryWrapper<EmailAccount>().orderByDesc(
                "create_date");
        List<EmailAccount> emailAccounts = super.baseMapper.selectList(createDate);

        return emailAccounts;
    }

}
