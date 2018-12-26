package com.orange.verify.admin.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.orange.verify.admin.mapper.AccountLoginLogMapper;
import com.orange.verify.api.bean.AccountLoginLog;
import com.orange.verify.api.service.AccountLoginLogService;

import java.util.List;

@Service
public class AccountLoginLogImpl extends ServiceImpl<AccountLoginLogMapper, AccountLoginLog>
        implements AccountLoginLogService {


    @Override
    public List<String> getBeforeData() {

        return super.baseMapper.getBeforeData();
    }

}
